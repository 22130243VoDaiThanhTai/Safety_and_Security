package controller;

import dao.AccountDAO;
import model.Account;
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
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession();
        Account unverifiedUser = (Account) session.getAttribute("unverifiedUser");

        if (unverifiedUser != null) {
            String phone = unverifiedUser.getPhone();

            // Gửi OTP mới
            String otp = OTPService.generateOTP(phone);
            OTPService.sendOTP(phone, otp);

            // Chuyển sang trang verify nhập OTP
            request.setAttribute("phone", phone);
            request.setAttribute("contentPage", "verify-otp.jsp");
            RequestDispatcher dispatcher = request.getRequestDispatcher("base.jsp");
            dispatcher.forward(request, response);
        } else {
            // Nếu không có user chưa kích hoạt trong session thì quay lại login
            response.sendRedirect("login");
        }
    }
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String phone = request.getParameter("phone");
        String otp = request.getParameter("otp");

        if (OTPService.verifyOTP(phone, otp)) {
            OTPService.clearOTP(phone); // Xóa OTP sau khi xác minh thành công
            accountDAO.updateStatus(phone, 1); // Cập nhật status = 1 (đã xác thực)

            // Chuyển hướng đến trang đăng nhập
            request.setAttribute("contentPage", "login.jsp");
            RequestDispatcher dispatcher = request.getRequestDispatcher("base.jsp");
            dispatcher.forward(request, response);
        } else {
            request.setAttribute("error", "Mã OTP không đúng hoặc đã hết hạn.");
            request.setAttribute("phone", phone); // Giữ lại để nhập lại OTP
            request.setAttribute("contentPage", "verify-otp.jsp");
            RequestDispatcher dispatcher = request.getRequestDispatcher("base.jsp");
            dispatcher.forward(request, response);
        }
    }
}

