package dao;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import database.DatabaseConnection;
import model.*;

import java.security.KeyFactory;
import java.security.PublicKey;
import java.security.spec.X509EncodedKeySpec;
import java.sql.*;
import java.util.*;

public class OrderDao {
    Connection connect = null;
    PreparedStatement ps = null;
    ResultSet rs = null;
    private ProductDAO productDAO = new ProductDAO();
    static DS ds = new DS();
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
    public int createOrder1(int userId, String email, String phone, String address, double totalPrice,String data,String privateKeyBase64) throws SQLException {
        int orderId = -1;

        String sql = "INSERT INTO orders (user_id, email, phone, address, total_price,signature) VALUES (?, ?, ?, ?, ?,?)";
        try {
            String sign = ds.sign(data,privateKeyBase64);
            connect = DatabaseConnection.getConnection();
            ps = connect.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setInt(1, userId);
            ps.setString(2, email);
            ps.setString(3, phone);
            ps.setString(4, address);
            ps.setDouble(5, totalPrice);
            ps.setString(6, sign);
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

//    xacs mionh chữ kí
    public static String getAllOrderSignatureJson() throws SQLException {
        List<OrderSignatureDTO> orderJsonList = new ArrayList<>();
        Connection conn = DatabaseConnection.getConnection();

        String sql = "SELECT o.id AS order_id, o.total_price, o.address, o.phone, o.user_id, a.username " +
                "FROM orders o JOIN account a ON o.user_id = a.id";
        PreparedStatement ps = conn.prepareStatement(sql);
        ResultSet rs = ps.executeQuery();

        while (rs.next()) {
            int orderId = rs.getInt("order_id");
            double total = rs.getDouble("total_price");
            String address = rs.getString("address");
            String phone = rs.getString("phone");
            int userId = rs.getInt("user_id");
            String username = rs.getString("username");

            // Lấy danh sách sản phẩm cho đơn này
            List<OrderItemDTO> items = new ArrayList<>();
            String detailSql = "SELECT p.id AS product_id, p.name AS product_name, od.quantity, od.price " +
                    "FROM order_details od JOIN product p ON od.product_id = p.id WHERE od.order_id = ?";
            PreparedStatement psDetail = conn.prepareStatement(detailSql);
            psDetail.setInt(1, orderId);
            ResultSet rsDetail = psDetail.executeQuery();

            while (rsDetail.next()) {
                items.add(new OrderItemDTO(
                        rsDetail.getInt("product_id"),
                        rsDetail.getString("product_name"),
                        rsDetail.getInt("quantity"),
                        rsDetail.getDouble("price")
                ));
            }

            OrderSignatureDTO orderDTO = new OrderSignatureDTO(items, total, address, phone, userId, username);
            orderJsonList.add(orderDTO);
            System.out.println(orderJsonList.size());
        }

        conn.close();

        // Convert list sang JSON
        Gson gson = new GsonBuilder().disableHtmlEscaping().setPrettyPrinting().create();
        return gson.toJson(orderJsonList);
    }
    public static PublicKey getPublicKeyForUser(int userId, Connection conn) {
        try {
            String sql = "SELECT public_key FROM rsa_keys WHERE user_id = ? AND is_active = 1 LIMIT 1";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, userId);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                String publicKeyBase64 = rs.getString("public_key");
                byte[] keyBytes = Base64.getDecoder().decode(publicKeyBase64);
                X509EncodedKeySpec keySpec = new X509EncodedKeySpec(keyBytes);
                KeyFactory keyFactory = KeyFactory.getInstance("RSA");
                return keyFactory.generatePublic(keySpec);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }


    public static Map<Integer, Boolean> verifyOrderIntegrity() throws Exception {
        Map<Integer, Boolean> resultMap = new HashMap<>();
        Connection conn = DatabaseConnection.getConnection();

        String sql = "SELECT o.id AS order_id, o.total_price, o.address, o.phone, o.user_id, a.username, o.signature " +
                "FROM orders o JOIN account a ON o.user_id = a.id";
        PreparedStatement ps = conn.prepareStatement(sql);
        ResultSet rs = ps.executeQuery();

        Gson gson = new GsonBuilder().disableHtmlEscaping().create(); // giống hệt bên ký

        while (rs.next()) {
            int orderId = rs.getInt("order_id");
            double total = rs.getDouble("total_price");
            String address = rs.getString("address");
            String phone = rs.getString("phone");
            int userId = rs.getInt("user_id");
            String username = rs.getString("username");
            String signature = rs.getString("signature");

            // ✅ Lấy đúng public key người dùng
            PublicKey publicKey = getPublicKeyForUser(userId, conn);
            if (publicKey == null) {
                resultMap.put(orderId, false);
                continue;
            }

            // ✅ Tạo lại danh sách sản phẩm
            List<OrderItemDTO> items = new ArrayList<>();
            String detailSql = "SELECT p.id AS product_id, p.name AS product_name, od.quantity, od.price " +
                    "FROM order_details od JOIN product p ON od.product_id = p.id WHERE od.order_id = ?";
            PreparedStatement psDetail = conn.prepareStatement(detailSql);
            psDetail.setInt(1, orderId);
            ResultSet rsDetail = psDetail.executeQuery();

            while (rsDetail.next()) {
                items.add(new OrderItemDTO(
                        rsDetail.getInt("product_id"),
                        rsDetail.getString("product_name"),
                        rsDetail.getInt("quantity"),
                        rsDetail.getDouble("price")
                ));
            }

            // ✅ Build lại JSON giống lúc ký
            OrderSignatureDTO orderDTO = new OrderSignatureDTO(items, total, address, phone, userId, username);
            String recreatedJson = gson.toJson(orderDTO);
            System.out.println("🔍 JSON  xác minh:\n" + recreatedJson);


            // ✅ Xác minh chữ ký
            boolean isValid = ds.verifySignature(recreatedJson, signature, publicKey);
            resultMap.put(orderId, isValid);
        }

        conn.close();
        return resultMap;
    }




    public static void main(String[] args) throws Exception {
        System.out.println(getAllOrderSignatureJson());
        System.out.println(verifyOrderIntegrity());
    }


}
