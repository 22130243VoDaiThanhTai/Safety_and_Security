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

@WebServlet("/search")
public class SearchController extends HttpServlet {
    private ProductDAO productDAO = new ProductDAO();
    private CategoryDAO categoryDAO = new CategoryDAO();

    // Thực hiện tìm kiếm sản phẩm trong phương thức GET
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html; charset=UTF-8");
        response.setCharacterEncoding("UTF-8");

        // Lấy giá trị của tham số 'searched' từ URL
        String textSearched = request.getParameter("searched");

        // Lấy danh sách sản phẩm và danh mục
        List<Product> listProductSearched = productDAO.searchByName(textSearched);
        List<Category> listCategory = categoryDAO.getAllCategory();

        // Đặt các thuộc tính vào request
        request.setAttribute("listProductSearched", listProductSearched);
        request.setAttribute("listCategory", listCategory);
        request.setAttribute("contentPage", "search.jsp");

        // Chuyển tiếp đến trang base.jsp để hiển thị kết quả
        RequestDispatcher dispatcher = request.getRequestDispatcher("base.jsp");
        dispatcher.forward(request, response);
    }

    // Xử lý hành động thêm vào giỏ hàng trong phương thức POST
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html; charset=UTF-8");
        response.setCharacterEncoding("UTF-8");

        // Lấy giá trị của tham số 'searched' từ request (đảm bảo có giá trị từ form hoặc URL)
        String textSearched = request.getParameter("searched");

        String action = request.getParameter("action");

        if ("add".equals(action)) {
            int productId = Integer.parseInt(request.getParameter("productCode"));
            Product product = productDAO.getProductByID(productId);
            
            HttpSession session = request.getSession();
            Cart cart = (Cart) session.getAttribute("cart");
            if (cart == null) {
                cart = new Cart();
            }

            // Kiểm tra đăng nhập và số lượng sản phẩm
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

            // Chuyển hướng lại trang tìm kiếm với tham số 'searched'
            response.sendRedirect("search?searched=" + textSearched);
        }
    }
}
