package dao;

import database.DatabaseConnection;
import model.Item;
import model.Order;
import model.OrderDetail;
import model.Product;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class OrderDao {
    Connection connect = null;
    PreparedStatement ps = null;
    ResultSet rs = null;
    private ProductDAO productDAO = new ProductDAO();
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
    public List<Order> getAllOrder() throws SQLException {
        List<Order> orders = new ArrayList<>();
        String sql = "SELECT * FROM orders";
        try {
            connect = DatabaseConnection.getConnection();
            ps = connect.prepareStatement(sql);
            rs = ps.executeQuery();
            while (rs.next()) {
                Order order = new Order();
                order.setOrderId(rs.getInt("id"));
                order.setUserId(rs.getInt("user_id"));
                order.setEmail(rs.getString("email"));
                order.setPhoneNumber(rs.getString("phone"));
                order.setAddress(rs.getString("address"));
                order.setTotal(rs.getDouble("total_price"));
                order.setOrderDate(rs.getTimestamp("created_at")); // nếu có cột này
                orders.add(order);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (connect != null) connect.close();
        }
        return orders;
    }

    public List<Order> getOrderByUser(int userId) throws SQLException {
        List<Order> orders = new ArrayList<>();
        String sql = "SELECT * FROM orders WHERE user_id = ?";
        try {
            connect = DatabaseConnection.getConnection();
            ps = connect.prepareStatement(sql);
            ps.setInt(1, userId);
            rs = ps.executeQuery();
            while (rs.next()) {
                Order order = new Order();
                int orderId = rs.getInt("id");
                order.setOrderId(rs.getInt("id"));
                order.setUserId(rs.getInt("user_id"));
                order.setEmail(rs.getString("email"));
                order.setPhoneNumber(rs.getString("phone"));
                order.setAddress(rs.getString("address"));
                order.setTotal(rs.getDouble("total_price"));
                order.setOrderDate(rs.getTimestamp("created_at")); // nếu có cột này

                List<Item> items = getItemsByOrderId(orderId);
                order.setItems(items);
                orders.add(order);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (connect != null) connect.close();
        }
        return orders;
    }
    public List<Item> getItemsByOrderId(int orderId) throws SQLException {
        List<Item> items = new ArrayList<>();
        String sql = "SELECT * FROM order_details WHERE order_id = ?";

        try {
            connect = DatabaseConnection.getConnection(); // ⚠️ THIẾU DÒNG NÀY
            ps = connect.prepareStatement(sql);
            ps.setInt(1, orderId);
            rs = ps.executeQuery();

            while (rs.next()) {
                int productId = rs.getInt("product_id");
                int quantity = rs.getInt("quantity");

                Product product = productDAO.getProductByID(productId);

                Item item = new Item(product, quantity, product.getPrice());
                items.add(item);
                System.out.println("Số lượng item tìm thấy: " + items.size());
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (connect != null) connect.close(); // đóng kết nối lại
        }

        return items;
    }
    public List<OrderDetail> getOrderDetails(int orderId) throws SQLException {
        List<OrderDetail> details = new ArrayList<>();
        String sql = "SELECT od.product_id, p.name AS product_name,p.image, od.quantity, od.price " +
                "FROM order_details od " +
                "JOIN product p ON od.product_id = p.id " +
                "WHERE od.order_id = ?";
        try {
            connect = DatabaseConnection.getConnection();
            ps = connect.prepareStatement(sql);
            ps.setInt(1, orderId);
            rs = ps.executeQuery();

            while (rs.next()) {
                OrderDetail detail = new OrderDetail();
                detail.setProductId(rs.getInt("product_id"));
                detail.setProductName(rs.getString("product_name"));
                detail.setImage(rs.getString("image"));
                detail.setQuantity(rs.getInt("quantity"));
                detail.setPrice(rs.getInt("price"));
                details.add(detail);
            }
        } catch (Exception e) {
            throw new SQLException("Lỗi truy xuất chi tiết đơn hàng", e);
        } finally {
            if (connect != null) connect.close();
        }
        return details;
    }

}
