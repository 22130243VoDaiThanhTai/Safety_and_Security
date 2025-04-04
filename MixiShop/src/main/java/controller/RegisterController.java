package controller;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.AccountDAO;
import model.Account;

@WebServlet("/register")
public class RegisterController extends HttpServlet {
    private AccountDAO accountDAO = new AccountDAO();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html; charset=UTF-8");
        response.setCharacterEncoding("UTF-8");

        request.setAttribute("contentPage", "register.jsp");
        RequestDispatcher dispatcher = request.getRequestDispatcher("base.jsp");
        dispatcher.forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html; charset=UTF-8");
        response.setCharacterEncoding("UTF-8");
        String username = request.getParameter("username");
        String email = request.getParameter("email");
        String address = request.getParameter("address");
        String password = request.getParameter("password");
        String confirmPassword = request.getParameter("confirm-password");
        String phone = request.getParameter("phone");

        // Lấy giá trị role và xử lý null
        String roleParam = request.getParameter("user-id");
        int role = (roleParam != null && !roleParam.isEmpty()) ? Integer.parseInt(roleParam) : 1;

        if (!password.equals(confirmPassword)) {
            request.setAttribute("errorMessage", "Mật khẩu không khớp!");
            doGet(request, response);
            return;
        }
        if (accountDAO.checkEmailExists(email)) {
            request.setAttribute("errorMessage", "Email đã tồn tại");
            doGet(request, response);
            return;
        }

        if (accountDAO.checkPhoneExists(phone)) {
            request.setAttribute("errorMessage", "Số điện thoại đã được sử dụng");
            doGet(request, response);
            return;
        }

        // Tạo đối tượng Account mới và thiết lập giá trị cho các thuộc tính
        Account account = new Account();
        account.setUsername(username);
        account.setEmail(email);
        account.setAddress(address);
        account.setPassword(password);
        account.setRole(role);
        account.setPhone(phone);

        // Gọi phương thức registerUser từ AccountDAO để lưu thông tin người dùng
        boolean isRegistered = accountDAO.registerUser(account);

        if (isRegistered) {
            response.sendRedirect("login");
        } else {
            request.setAttribute("errorMessage", "Đăng ký thất bại. Vui lòng thử lại!");
            doGet(request, response);
        }
    }
}


