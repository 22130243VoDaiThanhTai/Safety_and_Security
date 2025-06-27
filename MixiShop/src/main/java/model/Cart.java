package model;

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
        JsonArray itemsArray = new JsonArray();
        for (Item item : cart.getItems()) {
            JsonObject itemJson = new JsonObject();
            itemJson.addProperty("productId", item.getProduct().getId());
            itemJson.addProperty("productName", item.getProduct().getName());
            itemJson.addProperty("quantity", item.getQuantity());
            itemJson.addProperty("price", item.getPrice());
            itemsArray.add(itemJson);
        }

        JsonObject json = new JsonObject();
        json.add("items", itemsArray);
        json.addProperty("total", cart.getTotal());
        json.addProperty("address", address);
        json.addProperty("phone", phone);
        json.addProperty("userId", account.getId());
        json.addProperty("username", account.getUsername());

        return json.toString();
    }


}
