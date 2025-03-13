package dao;

import model.Product;
import model.Account;
import database.DatabaseConnection;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import java.util.ArrayList;
import java.util.List;

public class ProductDAO implements DAOInterface<Product> {
	Connection connect = null;
	PreparedStatement ps = null;
	ResultSet rs = null;

	public List<Product> getAllProduct() {
		List<Product> list = new ArrayList<Product>();

		try {

			connect = DatabaseConnection.getConnection();

			String sql = "select * from product";

			ps = connect.prepareStatement(sql);

			rs = ps.executeQuery();

			while (rs.next()) {
				int id = rs.getInt(1);
				String name = rs.getString(2);
				String image = rs.getString(3);
				int price = rs.getInt(4);
				int quantity = rs.getInt(5);
				int categoryID = rs.getInt(6);

				Product product = new Product(id, name, image, price, quantity, categoryID);

				list.add(product);
			}

			if (list.isEmpty()) {
				System.out.println("Không có sản phẩm nào");
			} else {
				System.out.println("Danh sách sản phẩm:");
				for (Product product : list) {
					System.out.println();
					System.out.println("ID: " + product.getId());
					System.out.println("Name: " + product.getName());
					System.out.println("Image: " + product.getImage());
					System.out.println("Price: " + product.getPrice() + " VNĐ");
					System.out.println("CategoryID: " + product.getCategoryID());
				}
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		} finally {
			try {
				if (rs != null) rs.close();
				if (ps != null) ps.close();
				if (connect != null) connect.close();
			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
			}
		}

		return list;
	}

	public List<Product> getProductByCategory(int categoryID) {
		List<Product> list = new ArrayList<Product>();

		try {

			connect = DatabaseConnection.getConnection();

			String sql = "select * from product where categoryID = ?";

			ps = connect.prepareStatement(sql);

			ps.setInt(1, categoryID);

			rs = ps.executeQuery();

			while (rs.next()) {
				int id = rs.getInt(1);
				String name = rs.getString(2);
				String image = rs.getString(3);
				int price = rs.getInt(4);
				int quantity = rs.getInt(5);

				Product product = new Product(id, name, image, price, quantity, categoryID);

				list.add(product);
			}

			if (list.isEmpty()) {
				System.out.println("Không có sản phẩm nào");
			} else {
				System.out.println("Danh sách sản phẩm:");
				for (Product product : list) {
					System.out.println();
					System.out.println("ID: " + product.getId());
					System.out.println("Name: " + product.getName());
					System.out.println("Image: " + product.getImage());
					System.out.println("Price: " + product.getPrice() + " VNĐ");
				}
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		} finally {
			try {
				if (rs != null) rs.close();
				if (ps != null) ps.close();
				if (connect != null) connect.close();
			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
			}
		}

		return list;
	}

	public List<Product> searchByName(String textSearched) {
		List<Product> list = new ArrayList<Product>();

		try {

			connect = DatabaseConnection.getConnection();

			String sql = "select * from product where name like ?";

			ps = connect.prepareStatement(sql);

			ps.setString(1, "%" + textSearched + "%");

			rs = ps.executeQuery();

			while (rs.next()) {
				int id = rs.getInt(1);
				String name = rs.getString(2);
				String image = rs.getString(3);
				int price = rs.getInt(4);
				int quantity = rs.getInt(5);
				int categoryID = rs.getInt(6);

				Product product = new Product(id, name, image, price, quantity, categoryID);

				list.add(product);
			}

			if (list.isEmpty()) {
				System.out.println("Không có sản phẩm nào");
			} else {
				System.out.println("Danh sách sản phẩm:");
				for (Product product : list) {
					System.out.println();
					System.out.println("ID: " + product.getId());
					System.out.println("Name: " + product.getName());
					System.out.println("Image: " + product.getImage());
					System.out.println("Price: " + product.getPrice() + " VNĐ");
				}
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		} finally {
			try {
				if (rs != null) rs.close();
				if (ps != null) ps.close();
				if (connect != null) connect.close();
			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
			}
		}

		return list;
	}

	public Product getProductByID(int id) {
		Product product = null;

		try {
			connect = DatabaseConnection.getConnection();
			String sql = "select * from product where id = ?";
			ps = connect.prepareStatement(sql);
			ps.setInt(1, id);
			rs = ps.executeQuery();

			if (rs.next()) {
				String name = rs.getString(2);
				String image = rs.getString(3);
				int price = rs.getInt(4);
				int quantity = rs.getInt(5);
				int categoryID = rs.getInt(6);

				product = new Product(id, name, image, price, quantity, categoryID);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (rs != null) rs.close();
				if (ps != null) ps.close();
				if (connect != null) connect.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		return product;
	}

	@Override
	public int insert(Product t) {
		// TODO Auto-generated method stub
		int result = 0;
		try {
			connect = DatabaseConnection.getConnection();

			String sql = "INSERT INTO product (name, image, price, quantity, categoryId) VALUES (?, ?, ?, ?, ?)";

			ps = connect.prepareStatement(sql);

			ps.setString(1, t.getName());
			ps.setString(2, t.getImage());
			ps.setInt(3, t.getPrice());
			ps.setInt(4, t.getQuantity());
			ps.setInt(5, t.getCategoryID());

			result = ps.executeUpdate();

			if (result > 0) {
				System.out.println("Thêm sản phẩm thành công");
			} else {
				System.out.println("Thêm sản phẩm thất bại");
			}

		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		} finally {
			try {
				if (rs != null) rs.close();
				if (ps != null) ps.close();
				if (connect != null) connect.close();
			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
			}
		}
		return result;
	}

	@Override
	public int update(Product t) {
		// TODO Auto-generated method stub
		int result = 0;
		try {
			connect = DatabaseConnection.getConnection();

			String sql = "UPDATE product SET name = ?, image = ?, price = ?, quantity = ?, categoryID = ? WHERE id = ?";

			ps = connect.prepareStatement(sql);

			ps.setString(1, t.getName());
			ps.setString(2, t.getImage());
			ps.setInt(3, t.getPrice());
			ps.setInt(4, t.getQuantity());
			ps.setInt(5, t.getCategoryID());
			ps.setInt(6, t.getId());

			result = ps.executeUpdate();

			if (result > 0) {
				System.out.println("Cập nhật sản phẩm thành công");
			} else {
				System.out.println("Cập nhật sản phẩm thất bại");
			}

		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		} finally {
			try {
				if (rs != null) rs.close();
				if (ps != null) ps.close();
				if (connect != null) connect.close();
			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
			}
		}
		return result;
	}

	@Override
	public int delete(Product t) {
		// TODO Auto-generated method stub
		int result = 0;
		try {
			connect = DatabaseConnection.getConnection();

			String sql = "DELETE FROM product WHERE name = ?";

			ps = connect.prepareStatement(sql);

			ps.setString(1, t.getName());

			result = ps.executeUpdate();

			if (result > 0) {
				System.out.println("Xóa sản phẩm thành công");
			} else {
				System.out.println("Xóa sản phẩm thất bại");
			}

		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		} finally {
			try {
				if (rs != null) rs.close();
				if (ps != null) ps.close();
				if (connect != null) connect.close();
			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
			}
		}

		return 0;
	}
	
	public int deleteProductById(int idProduct) {
		// TODO Auto-generated method stub
		int result = 0;
		try {
			connect = DatabaseConnection.getConnection();

			String sql = "DELETE FROM product WHERE id = ?";

			ps = connect.prepareStatement(sql);

			ps.setInt(1, idProduct);

			result = ps.executeUpdate();

			if (result > 0) {
				System.out.println("Xóa sản phẩm thành công");
			} else {
				System.out.println("Xóa sản phẩm thất bại");
			}

		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		} finally {
			try {
				if (rs != null) rs.close();
				if (ps != null) ps.close();
				if (connect != null) connect.close();
			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
			}
		}

		return result;
	}



	public static void main(String[] args) {
		ProductDAO productDAO = new ProductDAO();
		productDAO.getProductByCategory(1);
	}
}