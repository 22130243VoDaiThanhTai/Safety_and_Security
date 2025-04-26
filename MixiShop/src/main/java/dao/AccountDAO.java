package dao;

import model.Account;
import database.DatabaseConnection;
import org.mindrot.jbcrypt.BCrypt;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AccountDAO {
    private Connection connect;
    private PreparedStatement ps;
    private ResultSet rs;

    public synchronized Account authenticateUser(String username, String password) {
        try {
            connect = DatabaseConnection.getConnection();
            String sql = "SELECT * FROM account WHERE username = ?";
            ps = connect.prepareStatement(sql);
            ps.setString(1, username);
            rs = ps.executeQuery();

            if (rs.next()) {
                String hashedPassword = rs.getString("password");
                if (BCrypt.checkpw(password, hashedPassword)) {
                    return new Account(
                            rs.getInt("id"),
                            username,
                            hashedPassword,
                            rs.getString("email"),
                            rs.getString("address"),
                            rs.getInt("role"),
                            rs.getString("phone"),
                            rs.getInt("status"),
                            rs.getInt("phone_verìied")
                    );
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            closeConnections();
        }
        return null;
    }

    public boolean registerUser(Account account) {
        String sql = "INSERT INTO account(username, email, address, password, role, phone, status,phoneVerified) VALUES (?, ?, ?, ?, ?, ?, ?,?)";
        try {
            connect = DatabaseConnection.getConnection();
            ps = connect.prepareStatement(sql);

            String hashedPassword = hashPassword(account.getPassword());
            ps.setString(1, account.getUsername());
            ps.setString(2, account.getEmail());
            ps.setString(3, account.getAddress());
            ps.setString(4, hashedPassword);
            ps.setInt(5, account.getRole());
            ps.setString(6, account.getPhone());
            ps.setInt(7, 0); // Mặc định status = 0 (chưa xác minh)
            ps.setInt(8, 0); // Mặc định phoneVerified = 0 (chưa xác minh)

            return ps.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        } finally {
            closeConnections();
        }
    }

    public boolean deleteAccount(int id) {
        String sql = "DELETE FROM account WHERE id = ?";
        try {
            connect = DatabaseConnection.getConnection();
            ps = connect.prepareStatement(sql);
            ps.setInt(1, id);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        } finally {
            closeConnections();
        }
    }

    private String hashPassword(String password) {
        return BCrypt.hashpw(password, BCrypt.gensalt());
    }

    public boolean validateUser(String username, String password) {
        Account user = findUserByUsername(username);
        return user != null && BCrypt.checkpw(password, user.getPassword());
    }

    private Account findUserByUsername(String username) {
        String sql = "SELECT * FROM account WHERE username = ?";
        try {
            connect = DatabaseConnection.getConnection();
            ps = connect.prepareStatement(sql);
            ps.setString(1, username);
            rs = ps.executeQuery();

            if (rs.next()) {
                return new Account(
                        rs.getInt("id"),
                        username,
                        rs.getString("password"),
                        rs.getString("email"),
                        rs.getString("address"),
                        rs.getInt("role"),
                        rs.getString("phone"),
                        rs.getInt("status"),
                        rs.getInt("phone_verified")
                );
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            closeConnections();
        }
        return null;
    }

    public Account findUserByEmail(String email) {
        String sql = "SELECT * FROM account WHERE email = ?";
        try {
            connect = DatabaseConnection.getConnection();
            ps = connect.prepareStatement(sql);
            ps.setString(1, email);
            rs = ps.executeQuery();

            if (rs.next()) {
                return new Account(
                        rs.getInt("id"),
                        rs.getString("username"),
                        rs.getString("password"),
                        email,
                        rs.getString("address"),
                        rs.getInt("role"),
                        rs.getString("phone"),
                        rs.getInt("status"),
                        rs.getInt("phone_verified")
                );
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            closeConnections();
        }
        return null;
    }

    public boolean checkPhoneExists(String phone) {
        if (!phone.matches("^0\\d{9}$")) {
            return false;
        }
        String sql = "SELECT COUNT(*) FROM account WHERE phone = ?";
        try {
            connect = DatabaseConnection.getConnection();
            ps = connect.prepareStatement(sql);
            ps.setString(1, phone);
            rs = ps.executeQuery();
            return rs.next() && rs.getInt(1) > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        } finally {
            closeConnections();
        }
    }

    public boolean checkEmailExists(String email) {
        String sql = "SELECT COUNT(*) FROM account WHERE email = ?";
        try {
            connect = DatabaseConnection.getConnection();
            ps = connect.prepareStatement(sql);
            ps.setString(1, email);
            rs = ps.executeQuery();
            return rs.next() && rs.getInt(1) > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        } finally {
            closeConnections();
        }
    }

    public boolean isAccountExist(String email) {
        String sql = "SELECT COUNT(*) FROM account WHERE email = ?";
        try (Connection connect = DatabaseConnection.getConnection();
             PreparedStatement ps = connect.prepareStatement(sql)) {
            ps.setString(1, email);
            try (ResultSet rs = ps.executeQuery()) {
                return rs.next() && rs.getInt(1) > 0;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public void registerGoogleUser(Account acc) {
        String sql = "INSERT INTO account (email, username, role, status) VALUES (?, ?, ?, ?)";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, acc.getEmail());
            ps.setString(2, acc.getUsername());
            ps.setInt(3, acc.getRole());
            ps.setInt(4, 0); // status mặc định là 0
            ps.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<Account> getAllAccount() {
        List<Account> listAccount = new ArrayList<>();
        String sql = "SELECT * FROM account";
        try {
            connect = DatabaseConnection.getConnection();
            ps = connect.prepareStatement(sql);
            rs = ps.executeQuery();

            while (rs.next()) {
                listAccount.add(new Account(
                        rs.getInt("id"),
                        rs.getString("username"),
                        rs.getString("password"),
                        rs.getString("email"),
                        rs.getString("address"),
                        rs.getInt("role"),
                        rs.getString("phone"),
                        rs.getInt("status"),
                        rs.getInt("phone_verified")
                ));
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            closeConnections();
        }
        return listAccount;
    }

    public void updateUserRole(int userId, int roleId) {
        String sql = "UPDATE account SET role = ? WHERE id = ?";
        try {
            connect = DatabaseConnection.getConnection();
            ps = connect.prepareStatement(sql);
            ps.setInt(1, roleId);
            ps.setInt(2, userId);
            ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            closeConnections();
        }
    }

    public void updateStatusByEmail(String email) {
        String sql = "UPDATE account SET status = 1 WHERE email = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, email);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }
    public boolean isPhoneVerified(String phone) {
        String sql = "SELECT phone_verified FROM account WHERE phone = ?";
        try {
            connect = DatabaseConnection.getConnection();
            ps = connect.prepareStatement(sql);
            ps.setString(1, phone);
            rs = ps.executeQuery();
            return rs.next() && rs.getInt("phone_verified") == 1;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        } finally {
            closeConnections();
        }
    }
    public boolean updatePhoneVerification(String phone, int status) {
        String sql = "UPDATE account SET phone_verified = ? WHERE phone = ?";
        try {
            connect = DatabaseConnection.getConnection();
            ps = connect.prepareStatement(sql);
            ps.setInt(1, status);
            ps.setString(2, phone);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        } finally {
            closeConnections();
        }
    }

    private void closeConnections() {
        try {
            if (rs != null) rs.close();
            if (ps != null) ps.close();
            if (connect != null) connect.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
