package controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import dao.CategoryDAO;
import dao.ProductDAO;
import model.Cart;
import model.Category;
import model.Product;

@WebServlet("/detail")
public class DetailController extends HttpServlet {
	
	private ProductDAO productDAO = new ProductDAO();
	private CategoryDAO categoryDAO = new CategoryDAO();

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html; charset=UTF-8");
        response.setCharacterEncoding("UTF-8");
		
		String id = request.getParameter("id");
		int idProduct = Integer.parseInt(id);
		
		Product productDetail = productDAO.getProductByID(idProduct);
		List<Category> listCategory = categoryDAO.getAllCategory();

		request.setAttribute("productDetail", productDetail);
		request.setAttribute("listCategory", listCategory);
		request.setAttribute("contentPage", "detail.jsp");
		RequestDispatcher dispatcher = request.getRequestDispatcher("base.jsp");
		dispatcher.forward(request, response);
	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html; charset=UTF-8");
        response.setCharacterEncoding("UTF-8");
    	
    	String action = request.getParameter("action");

        if ("add".equals(action)) {
            int productId = Integer.parseInt(request.getParameter("productCode"));
            Product product = productDAO.getProductByID(productId);
            
            HttpSession session = request.getSession();
            Cart cart = (Cart) session.getAttribute("cart");
            if (cart == null) {
                cart = new Cart();
            }

            if (session.getAttribute("account") == null) { 
                response.getWriter().write("<script>alert('Bạn chưa đăng nhập tài khoản! Vui lòng đăng nhập trước khi mua sắm!');</script>");
            } else {
                if (product.getQuantity() == 0) {
                    session.setAttribute("notification", "<i class=\"fa-solid fa-circle-xmark text-danger\"></i> Sản phẩm \"" + product.getName() + "\" đã hết hàng");
                    session.setAttribute("notificationType", "error");
                } else {
                    cart.addItem(product);
                    
                    //Cập nhật số lượng sản phẩm trong csdl
                    product.setQuantity(product.getQuantity() - 1);
                    productDAO.update(product);
                    session.setAttribute("notification", "<i class=\"fa-solid fa-circle-check text-success\"></i> Sản phẩm \"" + product.getName() + "\" đã được thêm vào giỏ hàng!");
                    session.setAttribute("notificationType", "success");
                }
                session.setAttribute("cart", cart);
            }

            response.sendRedirect("detail?id=" + productId);
        }
	}

}