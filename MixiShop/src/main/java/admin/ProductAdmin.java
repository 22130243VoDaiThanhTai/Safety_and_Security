package admin;

import java.util.Scanner;

import dao.ProductDAO;
import model.Product;

public class ProductAdmin {
	public static void main(String[] args) {
		ProductDAO productDAO = new ProductDAO();
		Scanner scanner = new Scanner(System.in);

		while (true) {
			System.out.println("______________________________");
			System.out.println("CHỌN CHỨC NĂNG:");
			System.out.println("1. Thêm sản phẩm");
			System.out.println("2. Cập nhật sản phẩm");
			System.out.println("3. Xóa sản phẩm");
			System.out.println("4. Hiển thị danh sách sản phẩm");
			System.out.println("0. Thoát");
			System.out.print("Vui lòng nhấn phím để chọn chức năng: ");

			int choice = scanner.nextInt();
			scanner.nextLine();
			System.out.println();

			switch (choice) {
			case 1:
				System.out.print("Nhập tên của sản phẩm: ");
				String newProductName = scanner.nextLine();
				System.out.print("Nhập tên ảnh của sản phẩm: ");
				String newProductImageName = scanner.nextLine();
				System.out.print("Nhập giá của sản phẩm: ");
				int newProductPrice = scanner.nextInt();
				System.out.print("Nhập số lượng sản phẩm: ");
				int newProductQuantity = scanner.nextInt();
				System.out.print("Nhập ID của danh mục sản phẩm: ");
				int newIDByCategory = scanner.nextInt();
				productDAO.insert(new Product(newProductName, newProductImageName, newProductPrice, newProductQuantity, newIDByCategory));
				System.out.println();
				break;

			case 2:
				System.out.print("Nhập ID của sản phẩm cần cập nhật: ");
				int updateId = scanner.nextInt();
				scanner.nextLine();
				System.out.print("Nhập tên của sản phẩm cần cập nhật: ");
				String updateProductName = scanner.nextLine();
				System.out.print("Nhập tên ảnh của sản phẩm cần cập nhật: ");
				String updateProductImageName = scanner.nextLine();
				System.out.print("Nhập giá của sản phẩm cần cập nhật: ");
				int updateProductPrice = scanner.nextInt();
				System.out.print("Nhập số lượng của sản phẩm cần cập nhật: ");
				int updateProductQuantity = scanner.nextInt();
				System.out.print("Nhập ID của danh mục sản phẩm cần cập nhật: ");
				int updateIDByCategory = scanner.nextInt();
				productDAO.update(new Product(updateId, updateProductName, updateProductImageName, updateProductPrice, updateProductQuantity, updateIDByCategory));
				System.out.println();
				break;

			case 3:
				System.out.print("Nhập tên của sản phẩm cần xóa: ");
				String deleteProductName = scanner.nextLine();
				productDAO.delete(new Product(deleteProductName));
				System.out.println();
				break;

			case 4:
				productDAO.getAllProduct();
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
