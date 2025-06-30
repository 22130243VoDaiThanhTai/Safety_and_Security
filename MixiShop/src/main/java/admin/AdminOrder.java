package admin;

import java.io.IOException;
import java.sql.SQLException;
import java.util.*;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

import dao.OrderDao;
import model.Order;

@WebServlet("/adminOrders")
public class AdminOrder extends HttpServlet {
    private OrderDao orderDao = new OrderDao();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        response.setContentType("text/html; charset=UTF-8");
        response.setCharacterEncoding("UTF-8");

        List<Order> listOrder = new ArrayList<>();
        Map<Integer, Boolean> verifyMap = new HashMap<>();
        StringBuilder alertMessage = new StringBuilder();

        try {
            // Lấy danh sách đơn hàng
            listOrder = orderDao.getAllOrder();

            // Xác minh chữ ký từng đơn hàng
            verifyMap = OrderDao.verifyOrderIntegrity();

            // Tạo thông báo nếu có đơn hàng bị thay đổi
            List<Integer> invalidOrders = new ArrayList<>();
            for (Map.Entry<Integer, Boolean> entry : verifyMap.entrySet()) {
                if (!entry.getValue()) {
                    invalidOrders.add(entry.getKey());
                }
            }

            if (!invalidOrders.isEmpty()) {
                alertMessage.append("⚠️ Các đơn hàng có mã: ");
                for (int id : invalidOrders) {
                    alertMessage.append(id).append(", ");
                }
                alertMessage.setLength(alertMessage.length() - 2);
                alertMessage.append(" đã bị thay đổi hoặc không chính chủ!");
                request.setAttribute("alertMessage", alertMessage.toString());
            }

        } catch (SQLException | RuntimeException e) {
            throw new RuntimeException("Lỗi khi lấy danh sách đơn hàng", e);
        } catch (Exception e) {
            throw new RuntimeException("Lỗi xác minh chữ ký đơn hàng", e);
        }

        // Đẩy dữ liệu về JSP
        request.setAttribute("listOrder", listOrder);
        request.setAttribute("orderVerifyMap", verifyMap);
        request.setAttribute("contentPage", "adminOrder.jsp");

        RequestDispatcher dispatcher = request.getRequestDispatcher("base.jsp");
        dispatcher.forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doGet(request, response);
    }
}
