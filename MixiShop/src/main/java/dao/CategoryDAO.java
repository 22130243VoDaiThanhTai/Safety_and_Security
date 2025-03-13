package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import database.DatabaseConnection;
import model.Category;

public class CategoryDAO implements DAOInterface<Category> {
	Connection connect = null;
	PreparedStatement ps = null;
	ResultSet rs = null;

	public List<Category> getAllCategory() {
		List<Category> list = new ArrayList<Category>();

		try {

			connect = DatabaseConnection.getConnection();

			String sql = "select * from category";

			ps = connect.prepareStatement(sql);

			rs = ps.executeQuery();

			while (rs.next()) {
				int id = rs.getInt(1);
				String name = rs.getString(2);

				Category category = new Category(id, name);

				list.add(category);
			}

			if (list.isEmpty()) {
				System.out.println("Không có sản phẩm nào");
			} else {
				System.out.println("Danh sách danh mục:");
				for (Category category : list) {
					System.out.println();
					System.out.println("ID: " + category.getId());
					System.out.println("Name: " + category.getName());
				}
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		} finally {
			try {
				if (rs != null)
					rs.close();
				if (ps != null)
					ps.close();
				if (connect != null)
					connect.close();
			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
			}
		}

		return list;
	}

	@Override
	public int insert(Category t) {
		// TODO Auto-generated method stub
		int result = 0;
		try {
			connect = DatabaseConnection.getConnection();

			String sql = "INSERT INTO category (name) VALUES (?)";

			ps = connect.prepareStatement(sql);
			ps.setString(1, t.getName());

			result = ps.executeUpdate();

			if (result > 0) {
				System.out.println("Thêm danh mục thành công");
			} else {
				System.out.println("Thêm danh mục thất bại");
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		} finally {
			try {
				if (ps != null)
					ps.close();
				if (connect != null)
					ps.close();
			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
			}
		}
		return result;
	}

	@Override
	public int update(Category t) {
		// TODO Auto-generated method stub
		int result = 0;
		try {
			connect = DatabaseConnection.getConnection();
			
			String sql = "UPDATE category SET name = ? WHERE id = ?";
			
			ps = connect.prepareStatement(sql);
			
			ps.setString(1, t.getName());
			ps.setInt(2, t.getId());
			
			result = ps.executeUpdate();
			
			if (result > 0) {
				System.out.println("Cập nhật danh mục thành công");
			} else {
				System.out.println("Cập nhật danh mục thất bại");
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		} finally {
			try {
				if(ps != null) ps.close();
				if(connect != null) connect.close();
			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
			}
		}
		
		return result;
	}

	@Override
	public int delete(Category t) {
		// TODO Auto-generated method stub
		int result = 0;
		try {
			connect = DatabaseConnection.getConnection();
			
			String sql = "DELETE FROM category WHERE id = ?";
			
			ps = connect.prepareStatement(sql);
			
			ps.setInt(1, t.getId());
			
			result = ps.executeUpdate();
			
			if (result > 0) {
				System.out.println("Xóa danh mục thành công");
			} else {
				System.out.println("Xóa danh mục thất bại");
			}
			
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		} finally {
			try {
				if(ps != null) ps.close();
				if(connect != null) connect.close();
			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
			}
		}
		
		return result;
	}
	
	public int deleteCategoryById(int categoryId) {
		// TODO Auto-generated method stub
		int result = 0;
		try {
			connect = DatabaseConnection.getConnection();
			
			String sql = "DELETE FROM category WHERE id = ?";
			
			ps = connect.prepareStatement(sql);
			
			ps.setInt(1, categoryId);
			
			result = ps.executeUpdate();
			
			if (result > 0) {
				System.out.println("Xóa danh mục thành công");
			} else {
				System.out.println("Xóa danh mục thất bại");
			}
			
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		} finally {
			try {
				if(ps != null) ps.close();
				if(connect != null) connect.close();
			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
			}
		}
		
		return result;
	}
}
