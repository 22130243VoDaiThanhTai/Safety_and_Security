package admin;

import java.util.Scanner;

import dao.CategoryDAO;
import model.Category;

public class CategoryAdmin {
	public static void main(String[] args) {
		CategoryDAO categoryDAO = new CategoryDAO();
		Scanner scanner = new Scanner(System.in);

		while (true) {
			System.out.println("______________________________");
			System.out.println("CHỌN CHỨC NĂNG:");
			System.out.println("1. Thêm danh mục");
			System.out.println("2. Cập nhật danh mục");
			System.out.println("3. Xóa danh mục");
			System.out.println("4. Hiển thị danh sách danh mục");
			System.out.println("0. Thoát");
			System.out.print("Vui lòng nhấn phím để chọn chức năng: ");

			int choice = scanner.nextInt();
			scanner.nextLine();
			System.out.println();

			switch (choice) {
			case 1:
				System.out.print("Nhập tên danh mục: ");
				String newCategoryName = scanner.nextLine();
				categoryDAO.insert(new Category(newCategoryName));
				System.out.println();
				break;

			case 2:
				System.out.print("Nhập ID của danh mục cần cập nhật: ");
				int updateId = scanner.nextInt();
				scanner.nextLine();
				System.out.print("Nhập tên danh mục mới: ");
				String updatedCategoryName = scanner.nextLine();
				categoryDAO.update(new Category(updateId, updatedCategoryName));
				System.out.println();
				break;

			case 3:
				System.out.print("Nhập ID của danh mục cần xóa: ");
				int deleteId = scanner.nextInt();
				categoryDAO.delete(new Category(deleteId));
				System.out.println();
				break;

			case 4:
				categoryDAO.getAllCategory();
				System.out.println();
				break;

			case 0:
				System.out.println("Thoát chương trình.");
				System.out.println();
				scanner.close();
				return;

			default:
				System.out.println("Lựa chọn không hợp lệ. Vui lòng thử lại.");
				System.out.println();
			}
		}
	}
}
