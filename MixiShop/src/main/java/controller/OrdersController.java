package controller;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import dao.OrderDao;
import dao.ProductDAO;
import model.Account;
import model.Cart;
import model.Item;
import model.Order;
import model.Product;

@WebServlet("/orders")
public class OrdersController extends HttpServlet {
    String receivedOrder = "Đã nhận hàng";
    String cancelOrder = "Hủy đơn hàng";
	private ProductDAO productDAO = new ProductDAO();
    private OrderDao orderDao = new OrderDao();
	
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html; charset=UTF-8");
        response.setCharacterEncoding("UTF-8");

        HttpSession session = request.getSession();
        Account account = (Account) session.getAttribute("account");

        // Kiểm tra xem người dùng đã đăng nhập chưa
        if (account == null) {
            response.sendRedirect("login");
            return;
        }
        // Lấy danh sách đơn hàng từ session
        List<Order> orders = (List<Order>) session.getAttribute("orders");
//        List<Order> orders = null;
//        try {
//            orders = (List<Order>) orderDao.getOrderByUser(account.getId());
//        } catch (SQLException e) {
//            throw new RuntimeException(e);
//        }
        // Nếu danh sách đơn hàng không có, thông báo không có đơn hàng
        if (orders == null || orders.isEmpty()) {
            session.setAttribute("notification", "<strong><i class=\"fa-solid fa-face-frown text-danger\"></i> Hiện tại chưa có đơn hàng nào :(</strong>");
            session.setAttribute("notificationType", "error");
            request.setAttribute("message", "Không có đơn hàng nào.");
        } else {
            // Sắp xếp danh sách orders theo orderId giảm dần
            Collections.sort(orders, new Comparator<Order>() {
                @Override
                public int compare(Order o1, Order o2) {
                    return Integer.compare(o2.getOrderId(), o1.getOrderId()); // Giảm dần
                }
            });
        }
        request.setAttribute("orders", orders);
        request.setAttribute("contentPage", "orders.jsp");
        RequestDispatcher dispatcher = request.getRequestDispatcher("base.jsp");
        dispatcher.forward(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html; charset=UTF-8");
        response.setCharacterEncoding("UTF-8");

        String action = request.getParameter("action");
        HttpSession session = request.getSession();

        try {
            if ("refund".equals(action)) {
                int orderCodeDelete = Integer.parseInt(request.getParameter("orderCode"));
                List<Order> orders = (List<Order>) session.getAttribute("orders");
                if (orders != null) {
                    // Tìm đơn hàng cần xóa
                    Order orderToDelete = null;
                    for (Order order : orders) {
                        if (order.getOrderId() == orderCodeDelete) {
                            orderToDelete = order;
                            break;
                        }
                    }

                    // Nếu tìm thấy đơn hàng, tiến hành hoàn lại số lượng sản phẩm
                    if (orderToDelete != null) {
                        // Duyệt qua các item trong đơn hàng và cập nhật lại số lượng sản phẩm
                        for (Item item : orderToDelete.getItems()) {
                        	int productId = item.getProduct().getId();
                            Product product = productDAO.getProductByID(productId);
                            int quantityToRestore = item.getQuantity(); // Số lượng cần phục hồi

                                // Cập nhật số lượng của sản phẩm trong kho
                                product.setQuantity(product.getQuantity() + quantityToRestore);
                                productDAO.update(product); // Cập nhật sản phẩm vào cơ sở dữ liệu

                        }

                        // Xóa đơn hàng khỏi danh sách đơn hàng
                        orders.remove(orderToDelete);
                        
                        // Thông báo thành công
                        session.setAttribute("notification", "<i class=\"fa-solid fa-circle-check text-success\"></i> Hủy đơn hàng thành công!");
                        session.setAttribute("notificationType", "success");
                    }
                }
            }
            
            else if ("confirm".equals(action)) {
                int orderCodeConfirm = Integer.parseInt(request.getParameter("orderCode"));
                List<Order> orders = (List<Order>) session.getAttribute("orders");
                for (Order order : orders) {
					if (order.getOrderId() == orderCodeConfirm) {
						order.setStatus(receivedOrder);
						
						// Thông báo thành công
                        session.setAttribute("notification", "<i class=\"fa-solid fa-circle-check text-success\"></i> Cảm ơn quý khách vì đã mua hàng bên chúng tôi :)");
                        session.setAttribute("notificationType", "success");
					}
				}
            }
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }

        // Sau khi thực hiện hoàn tất, quay lại trang đơn hàng
        doGet(request, response);
    }

}
