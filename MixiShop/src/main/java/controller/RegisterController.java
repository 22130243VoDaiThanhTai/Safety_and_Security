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
import service.OTPService;

@WebServlet("/register")
public class RegisterController extends HttpServlet {
    private AccountDAO accountDAO = new AccountDAO();

    @Override
    public void init() throws ServletException {
        accountDAO = new AccountDAO();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html; charset=UTF-8");
        response.setCharacterEncoding("UTF-8");

        request.setAttribute("contentPage", "register.jsp");
        RequestDispatcher dispatcher = request.getRequestDispatcher("base.jsp");
        dispatcher.forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String username = request.getParameter("username");
        String password = request.getParameter("password");
        String email = request.getParameter("email");
        String address = request.getParameter("address");
        String phone = request.getParameter("phone");


        // Kiểm tra dữ liệu đầu vào (bạn có thể thêm validate sâu hơn nếu cần)
        if (accountDAO.checkEmailExists(email) || accountDAO.checkPhoneExists(phone)) {
            request.setAttribute("error", "Email hoặc số điện thoại đã tồn tại.");
            request.setAttribute("contentPage", "register.jsp");
            RequestDispatcher dispatcher = request.getRequestDispatcher("base.jsp");
            dispatcher.forward(request, response);
            return;
        }

        // Tạo đối tượng Account với status = 0
        Account newAccount = new Account(username, password, email, address, phone);
        boolean inserted = accountDAO.registerUser(newAccount);

        if (!inserted) {
            request.setAttribute("error", "Đăng ký thất bại. Vui lòng thử lại.");
            request.setAttribute("contentPage", "register.jsp");
            RequestDispatcher dispatcher = request.getRequestDispatcher("base.jsp");
            dispatcher.forward(request, response);
            return;
        }

        // Tạo và gửi OTP
        String otp = OTPService.generateOTP(phone);
        OTPService.sendOTP(phone, otp);

        // Lưu số điện thoại để xác thực OTP sau
        request.setAttribute("phone", phone);
        request.setAttribute("contentPage", "verify-otp.jsp");
        RequestDispatcher dispatcher = request.getRequestDispatcher("base.jsp");
        dispatcher.forward(request, response);
    }
}



