package model;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Cart {
    private Map<Integer, Item> items = new HashMap<>();

    public Cart() {
        items = new HashMap<>();
    }

    public int getCount() {
        return items.size();
    }

    public void addItem(Product product) {
        int id = product.getId();
        if (items.containsKey(id)) {
            Item scitem = items.get(id);
            scitem.incrementQuantity();
        } else {
            Item newItem = new Item(product, 1,product.getPrice());
            items.put(id, newItem);
        }
    }

    public void remove(int productId) {
        items.remove(productId);
    }

    public List<Item> getItems() {
        return new ArrayList<>(items.values());
    }

    public int getNumberOfItems() {
        int numberOfItems = 0;
        for (Item item : items.values()) {
            numberOfItems += item.getQuantity();
        }
        return numberOfItems;
    }

    public double getTotal() {
        double total = 0;
        for (Item item : items.values()) {
            total += item.getTotal();
        }
        return roundOff(total);
    }

    private double roundOff(double x) {
        return Math.round(x * 100) / 100.0;
    }

    public void updateQuantity(int productId, int quantity) {
        if (quantity == 0) {
            remove(productId);
        } else {
            for (Item item : items.values()) {
                if (item.getProduct().getId() == productId) {
                    item.setQuantity(quantity);
                    break;
                }
            }
        }
    }
    
    public void clear() {
        items.clear();
    }
    
    public int getItemQuantity(int productId) {
        Item item = items.get(productId);
        if (item != null) {
            return item.getQuantity();
        }
        return 0;
    }
    public String cartToJson(Cart cart, Account account, String address, String phone) {
        List<OrderItemDTO> items = new ArrayList<>();
        for (Item item : cart.getItems()) {
            items.add(new OrderItemDTO(
                    item.getProduct().getId(),
                    item.getProduct().getName(),
                    item.getQuantity(),
                    (double) item.getPrice()  // đảm bảo là double
            ));
        }

        OrderSignatureDTO orderDTO = new OrderSignatureDTO(
                items,
                (double) cart.getTotal(),  // đảm bảo là double
                address,
                phone,
                account.getId(),
                account.getUsername()
        );

        Gson gson = new GsonBuilder().disableHtmlEscaping().create(); // dùng chung config
        return gson.toJson(orderDTO);

    }


}
