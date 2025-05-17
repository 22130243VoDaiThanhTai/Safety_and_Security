package controller;

import dao.AccountDAO;
import service.OTPService;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;

@WebServlet("/verify-otp")
public class OTPController extends HttpServlet {
    private AccountDAO accountDAO;

    @Override
    public void init() throws ServletException {
        accountDAO = new AccountDAO();
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String phone = request.getParameter("phone");
        String otp = request.getParameter("otp");

        if (OTPService.verifyOTP(phone, otp)) {
            OTPService.clearOTP(phone); // Xóa OTP sau khi xác minh thành công
            accountDAO.updateStatus(phone, 1); // Cập nhật status = 1 (đã xác thực)

            // Chuyển hướng đến trang đăng nhập hoặc trang chào mừng
            request.setAttribute("contentPage", "login.jsp");
            RequestDispatcher dispatcher = request.getRequestDispatcher("base.jsp");
            dispatcher.forward(request, response);
        } else {
            request.setAttribute("error", "Mã OTP không đúng hoặc đã hết hạn.");
            request.setAttribute("phone", phone); // Giữ lại để nhập lại OTP
            request.getRequestDispatcher("verify-otp.jsp").forward(request, response);
        }
    }
}

