package model;

import java.sql.Timestamp;

public class RSAKey {
    private int id;
    private int userId;
    private String publicKey;
    private boolean isActive;
    private Timestamp createdAt;

    public RSAKey() {}

    public RSAKey(int userId, String publicKey, boolean isActive) {
        this.userId = userId;
        this.publicKey = publicKey;
        this.isActive = isActive;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getPublicKey() {
        return publicKey;
    }

    public void setPublicKey(String publicKey) {
        this.publicKey = publicKey;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }

    public Timestamp getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Timestamp createdAt) {
        this.createdAt = createdAt;
    }
}

