package controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.CategoryDAO;
import dao.ProductDAO;
import model.Account;
import model.Category;
import model.Product;


@WebServlet("/profile")
public class ProfileController extends HttpServlet {
    private CategoryDAO categoryDAO = new CategoryDAO();
    private ProductDAO productDAO = new ProductDAO();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Lấy thông tin người dùng từ session
        response.setContentType("text/html; charset=UTF-8");
        response.setCharacterEncoding("UTF-8");
    	
        Account account = (Account) request.getSession().getAttribute("account");

        if (account == null) {
            // Nếu chưa đăng nhập, chuyển hướng đến trang đăng nhập
            response.sendRedirect("login");
            return;
        }

        // Truyền thông tin người dùng tới JSP
        request.setAttribute("user", account);
        request.setAttribute("contentPage", "profile.jsp");

        // Truyền danh sách sản phẩm và danh mục vào JSP (nếu cần)
        List<Product> listProduct = productDAO.getAllProduct();
        List<Category> listCategory = categoryDAO.getAllCategory();
        request.setAttribute("listProduct", listProduct);
        request.setAttribute("listCategory", listCategory);

        // Chuyển hướng tới trang base.jsp (giao diện chính)
        RequestDispatcher dispatcher = request.getRequestDispatcher("base.jsp");
        dispatcher.forward(request, response);
    }
}
