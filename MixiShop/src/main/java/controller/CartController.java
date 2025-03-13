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

@WebServlet("/cart")
public class CartController extends HttpServlet {
    private ProductDAO productDAO = new ProductDAO();
    private CategoryDAO categoryDAO = new CategoryDAO();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Lấy danh sách sản phẩm và danh mục
    	
        response.setContentType("text/html; charset=UTF-8");
        response.setCharacterEncoding("UTF-8");
        
        List<Product> listProduct = productDAO.getAllProduct();
        List<Category> listCategory = categoryDAO.getAllCategory();

        // Lấy giỏ hàng từ session
        HttpSession session = request.getSession();
        Cart cart = (Cart) session.getAttribute("cart");
        if (cart == null) {
            cart = new Cart();
            session.setAttribute("cart", cart);
        }

        // Truyền dữ liệu vào request
        request.setAttribute("listProduct", listProduct);
        request.setAttribute("listCategory", listCategory);
        request.setAttribute("cartItems", cart.getItems());
        request.setAttribute("cartTotal", cart.getTotal());
        request.setAttribute("totalItems", cart.getNumberOfItems());
        request.setAttribute("contentPage", "cart.jsp");

        RequestDispatcher dispatcher = request.getRequestDispatcher("base.jsp");
        dispatcher.forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html; charset=UTF-8");
        response.setCharacterEncoding("UTF-8");
    	
    	String action = request.getParameter("action");
        HttpSession session = request.getSession();
        Cart cart = (Cart) session.getAttribute("cart");
        if (cart == null) {
            cart = new Cart();
            session.setAttribute("cart", cart);
        }

        try {
        	if ("deleted".equals(action)) {
                int productCode = Integer.parseInt(request.getParameter("productCode"));
                Product product = productDAO.getProductByID(productCode);
                int quantityToDelete = cart.getItemQuantity(productCode);
                product.setQuantity(product.getQuantity() + quantityToDelete);
                productDAO.update(product);
                cart.remove(productCode);
                session.setAttribute("notification", "<i class=\"fa-solid fa-circle-check text-success\"></i> Sản phẩm \"" + product.getName() + "\" đã được xóa khỏi giỏ hàng!");
                session.setAttribute("notificationType", "success");
            }
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }

        // Quay lại trang giỏ hàng
        response.sendRedirect("cart");
    }
}
