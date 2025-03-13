package dao;

import model.Account;
import model.Product;
import database.DatabaseConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class AccountDAO {
    Connection connect = null;
    PreparedStatement ps = null;
    ResultSet rs = null;

    public synchronized Account authenticateUser(String username, String password) {
        try {
            // Mở kết nối với cơ sở dữ liệu
            connect = DatabaseConnection.getConnection();
            
            // Truy vấn cơ sở dữ liệu để kiểm tra tài khoản và mật khẩu
            String sql = "SELECT * FROM account WHERE username = ? AND password = ?";
            ps = connect.prepareStatement(sql);
            ps.setString(1, username);
            ps.setString(2, password);

            // Thực hiện truy vấn
            rs = ps.executeQuery();
            
            // Nếu tìm thấy tài khoản
            if (rs.next()) {
                int id = rs.getInt("id");
                String email = rs.getString("email");
                String address = rs.getString("address");
                int role = rs.getInt("userID");  // Lấy giá trị role từ cơ sở dữ liệu
                String phone = rs.getString("phone");
                
                // Trả về đối tượng Account với tất cả các thuộc tính
                return new Account(id, username, password, email, address, role, phone);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            // Đảm bảo đóng kết nối khi xong
            closeConnections();
        }
        
        // Trả về null nếu không tìm thấy tài khoản
        return null;
    }
 

    public boolean registerUser(Account account) {
        String sql = "INSERT INTO account(username, email, address, password, userID, phone) VALUES (?, ?, ?, ?, ?,	?)";
        try {
            connect = DatabaseConnection.getConnection();
            ps = connect.prepareStatement(sql);

            ps.setString(1, account.getUsername());
            ps.setString(2, account.getEmail());
            ps.setString(3, account.getAddress());
            ps.setString(4, account.getPassword());
            ps.setInt(5, account.getRole());
            ps.setString(6, account.getPhone());
            

            return ps.executeUpdate() > 0;
            
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        } finally {
            closeConnections();
        }
    }
    
    public List<Account> getAllAccount(){
    	List<Account> listAccount = new ArrayList<Account>();
    	try {

			connect = DatabaseConnection.getConnection();

			String sql = "select * from account";

			ps = connect.prepareStatement(sql);

			rs = ps.executeQuery();

			while (rs.next()) {
				int id = rs.getInt(1);
				String username = rs.getString(2);
				String password = rs.getString(3);
				String email = rs.getString(4);
				String address = rs.getString(5);
				int role = rs.getInt(6);
				String phone = rs.getString(7);

				Account account = new Account(id, username, password, email, address, role, phone);

				listAccount.add(account);
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
    	return listAccount;
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
