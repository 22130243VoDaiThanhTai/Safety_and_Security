package dao;

import database.DatabaseConnection;
import model.Account;
import model.RSAKey;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class KeyDao {
    private Connection connect;
    private PreparedStatement ps;
    private ResultSet rs;
    public void deactivateOldKeys(int userId) {
        String sql = "UPDATE rsa_keys SET is_active = FALSE WHERE user_id = ?";
        try {
            connect = DatabaseConnection.getConnection();
            ps = connect.prepareStatement(sql);
            ps.setInt(1, userId);
            ps.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public void saveKey(RSAKey key) {
        String sql = "INSERT INTO rsa_keys (user_id, public_key, is_active) VALUES (?, ?, ?)";
        try {
            connect = DatabaseConnection.getConnection();
            ps = connect.prepareStatement(sql);
            ps.setInt(1, key.getUserId());
            ps.setString(2, key.getPublicKey());
            ps.setBoolean(3, key.isActive());
            ps.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
