package model;

import java.util.List;

public class OrderSignatureDTO {
    private List<OrderItemDTO> items;
    private double total;
    private String address;
    private String phone;
    private int userId;
    private String username;
    private String signature;

    public OrderSignatureDTO(List<OrderItemDTO> items, double total, String address, String phone, int userId, String username) {
        this.items = items;
        this.total = total;
        this.address = address;
        this.phone = phone;
        this.userId = userId;
        this.username = username;
    }
    public void setSignature(String signature) {
        this.signature = signature;
    }

    // Getters & Setters
}

