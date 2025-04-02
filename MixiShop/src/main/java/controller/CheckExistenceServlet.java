package controller;

import java.io.IOException;


import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.AccountDAO;

@WebServlet("/check-existence") // URL pattern thống nhất
public class CheckExistenceServlet extends HttpServlet {
    private AccountDAO accountDAO;

    @Override
    public void init() throws ServletException {
        this.accountDAO = new AccountDAO(); // Khởi tạo DAO
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        String type = request.getParameter("type"); // "email" hoặc "phone"
        String value = request.getParameter("value"); // Giá trị cần kiểm tra

        try {
            boolean exists = false;

            if ("email".equalsIgnoreCase(type)) {
                exists = accountDAO.checkEmailExists(value);
            }
            else if ("phone".equalsIgnoreCase(type)) {
                exists = accountDAO.checkPhoneExists(value);
            }

            response.getWriter().write(String.format("{\"exists\": %b}", exists));

        } catch (Exception e) {
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            response.getWriter().write("{\"error\": \"Lỗi server\"}");
        }
    }
}
