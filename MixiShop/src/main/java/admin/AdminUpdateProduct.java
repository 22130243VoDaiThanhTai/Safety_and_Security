package admin;

import java.io.File;
import java.io.IOException;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.http.Part;

import dao.CategoryDAO;
import dao.ProductDAO;
import model.Account;
import model.Category;
import model.Product;

/**
 * Servlet implementation class AdminUpdateProduct
 */
@WebServlet("/adminUpdateProduct")
@MultipartConfig
public class AdminUpdateProduct extends HttpServlet {
    private ProductDAO productDAO = new ProductDAO();
    private CategoryDAO categoryDAO = new CategoryDAO();
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html; charset=UTF-8");
        response.setCharacterEncoding("UTF-8");
		
		HttpSession session = request.getSession(false);

        // Kiểm tra nếu session chưa được khởi tạo hoặc không có tài khoản
        if (session == null || session.getAttribute("account") == null) {
        	response.setContentType("text/html;charset=UTF-8");
            response.getWriter().println("<script type=\"text/javascript\">");
            response.getWriter().println("alert('Truy cập không hợp lệ! Vui lòng đăng nhập bằng tài khoản ADMIN!');");
            response.getWriter().println("window.location.href = 'login';");
            response.getWriter().println("</script>");	
            return;
        }

        // Lấy thông tin tài khoản từ session
        Account account = (Account) session.getAttribute("account");

        // Kiểm tra nếu tài khoản có role khác 0
        if (account.getRole() != 0) {
        	 response.setContentType("text/html;charset=UTF-8");
             response.getWriter().println("<script type=\"text/javascript\">");
             response.getWriter().println("alert('Truy cập không hợp lệ! Vui lòng đăng nhập bằng tài khoản ADMIN!');");
             response.getWriter().println("window.location.href = 'login';");
             response.getWriter().println("</script>");	
            return;
        }
		List<Product> listProduct = productDAO.getAllProduct();
        List<Category> listCategory = categoryDAO.getAllCategory();
        request.setAttribute("listProduct", listProduct);
        request.setAttribute("listCategory", listCategory);
        request.setAttribute("contentPage", "adminUpdateProduct.jsp");
        RequestDispatcher dispatcher = request.getRequestDispatcher("base.jsp");
        dispatcher.forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html; charset=UTF-8");
        response.setCharacterEncoding("UTF-8");
		
        HttpSession session = request.getSession();
		int productID = Integer.parseInt(request.getParameter("productCode"));
		String productName = request.getParameter("productName");
        int productPrice = Integer.parseInt(request.getParameter("productPrice"));
        int productQuantity = Integer.parseInt(request.getParameter("productQuantity"));
        int categoryID = Integer.parseInt(request.getParameter("categoryCode"));
        
        Part part = request.getPart("productImage");
        String productImage = extractFileName(part);

        if (productImage != null && !productImage.isEmpty()) {
            productImage = productImage.replaceAll(" ", "_");
        }

        String imagePath = productImage;

        Product product = new Product(productID, productName, imagePath, productPrice, productQuantity, categoryID);
        int result = productDAO.update(product);

        if (result > 0) {
            File directory = new File(getServletContext().getRealPath("/") + "images/");
            if (!directory.exists()) {
                directory.mkdirs();
            }
            part.write(getServletContext().getRealPath("/") + "images/" + productImage);

            session.setAttribute("notification", "<i class=\"fa-solid fa-circle-check text-success\"></i> Cập nhật sản phẩm \"" + product.getName() + "\" thành công");
            session.setAttribute("notificationType", "success");
            doGet(request, response);
        } else {
            session.setAttribute("notification", "<i class=\"fa-solid fa-circle-xmark text-danger\"></i> Cập nhật sản phẩm \"" + product.getName() + "\" thất bại");
            session.setAttribute("notificationType", "error");
            doGet(request, response);
        }
	}
	
    private String extractFileName(Part part) {
        String contentDisposition = part.getHeader("content-disposition");
        String[] elements = contentDisposition.split(";");
        for (String element : elements) {
            if (element.trim().startsWith("filename")) {
                return element.substring(element.indexOf("=") + 2, element.length() - 1);
            }
        }
        return null;
    }

}
