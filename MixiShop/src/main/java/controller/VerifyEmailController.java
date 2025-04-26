package controller;

import dao.AccountDAO;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/verify")
public class VerifyEmailController extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String email = request.getParameter("email");

        if (email != null && !email.trim().isEmpty()) {
            AccountDAO dao = new AccountDAO();
            dao.updateStatusByEmail(email);  // cập nhật status = 1 cho email này
        }

        request.setAttribute("message", "Xác minh thành công! Bạn có thể đăng nhập.");
        request.getRequestDispatcher("/verify-success.jsp").forward(request, response);
    }
}
