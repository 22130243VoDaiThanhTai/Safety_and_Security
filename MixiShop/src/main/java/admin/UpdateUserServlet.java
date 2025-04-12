package admin;

import java.io.File;
import java.io.IOException;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.http.Part;

import dao.AccountDAO;
import dao.CategoryDAO;
import model.Account;
import model.Category;
import model.Product;

/**
 * Servlet implementation class AdminAddProduct
 */
@WebServlet("/update_user")
public class UpdateUserServlet extends HttpServlet {
    private AccountDAO accountDAO = new AccountDAO();
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    /**
     * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
     */
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int userId = Integer.parseInt(request.getParameter("user_id"));
        int roleId = Integer.parseInt(request.getParameter("role_id"));
        System.out.println(roleId);
        System.out.println(userId);
        accountDAO.updateUserRole(userId, roleId);
        response.sendRedirect("adminAccounts");
    }

}

