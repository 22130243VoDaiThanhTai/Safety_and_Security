package dao;

import database.DatabaseConnection;

import java.sql.*;

public class OrderDao {
    Connection connect = null;
    PreparedStatement ps = null;
    ResultSet rs = null;
    public int createOrder(int userId, String email, String phone, String address, double totalPrice) throws SQLException {
        int orderId = -1;
        String sql = "INSERT INTO orders (user_id, email, phone, address, total_price) VALUES (?, ?, ?, ?, ?)";
        try {
            connect = DatabaseConnection.getConnection();
            ps = connect.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setInt(1, userId);
            ps.setString(2, email);
            ps.setString(3, phone);
            ps.setString(4, address);
            ps.setDouble(5, totalPrice);
            ps.executeUpdate();

            rs = ps.getGeneratedKeys();
            if (rs.next()) {
                orderId = rs.getInt(1); // Lấy order_id được tạo ra
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (connect != null) connect.close();
        }
        return orderId;
    }

    // Thêm từng sản phẩm vào chi tiết đơn hàng
    public boolean addOrderDetail(int orderId, int productId, int quantity, double price) throws SQLException {
        String sql = "INSERT INTO order_details (order_id, product_id, quantity, price) VALUES (?, ?, ?, ?)";
        try {
            connect = DatabaseConnection.getConnection();
            ps = connect.prepareStatement(sql);
            ps.setInt(1, orderId);
            ps.setInt(2, productId);
            ps.setInt(3, quantity);
            ps.setDouble(4, price);
            ps.executeUpdate();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (connect != null) connect.close();
        }
        return false;
    }
}
