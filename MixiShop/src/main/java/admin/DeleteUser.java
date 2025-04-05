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
@WebServlet("/deleteuser")
public class DeleteUser extends HttpServlet {
    private CategoryDAO categoryDAO = new CategoryDAO();
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    /**
     * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
     */
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String idRaw = request.getParameter("id");
        try {
            int id = Integer.parseInt(idRaw);
            AccountDAO accountDAO = new AccountDAO();
            boolean deleted = accountDAO.deleteAccount(id);
            if (deleted) {
                System.out.println("Xóa tài khoản thành công.");
            } else {
                System.out.println("Xóa tài khoản thất bại.");
            }
            response.sendRedirect("adminAccounts");
        } catch (NumberFormatException e) {
            e.printStackTrace();
            response.sendRedirect("error.jsp");
        }
    }

}
