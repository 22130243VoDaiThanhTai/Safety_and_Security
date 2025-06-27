package model;

public class OrderItemDTO {
    private int productId;
    private String productName;
    private int quantity;
    private double price;

    public OrderItemDTO(int productId, String productName, int quantity, double price) {
        this.productId = productId;
        this.productName = productName;
        this.quantity = quantity;
        this.price = price;
    }

    // Getters & Setters
}
