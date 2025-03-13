package controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import dao.CategoryDAO;
import dao.ProductDAO;
import model.Account;
import model.Cart;
import model.Category;
import model.Item;
import model.Order;
import model.Product;

@WebServlet("/checkout")
public class CheckoutController extends HttpServlet {
    private ProductDAO productDAO = new ProductDAO();
    private CategoryDAO categoryDAO = new CategoryDAO();
    String cancelOrder = "Hủy đơn hàng";
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Đặt mã hóa UTF-8 cho HTTP response
        response.setContentType("text/html; charset=UTF-8");
        response.setCharacterEncoding("UTF-8");

        Account account = (Account) request.getSession().getAttribute("account");

        if (account == null) {
            response.sendRedirect("login");
            return;
        }

        request.setAttribute("user", account);
        List<Product> listProduct = productDAO.getAllProduct();
        List<Category> listCategory = categoryDAO.getAllCategory();

        request.setAttribute("listProduct", listProduct);
        request.setAttribute("listCategory", listCategory);
        request.setAttribute("contentPage", "checkout.jsp");
        RequestDispatcher dispatcher = request.getRequestDispatcher("base.jsp");
        dispatcher.forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Đặt mã hóa UTF-8 cho HTTP response
        response.setContentType("text/html; charset=UTF-8");
        response.setCharacterEncoding("UTF-8");

        String name = request.getParameter("name");
        String address = request.getParameter("address");
        String phone = request.getParameter("phone");
        HttpSession session = request.getSession();
        Cart cart = (Cart) session.getAttribute("cart");

        if (cart == null || cart.getCount() == 0) {
            response.sendRedirect("cart");
            return;
        }

        try {
            Order order = new Order(cart, name, address, phone);
            order.setStatus("Pending"); // Trạng thái ban đầu

            order.updateStatus();
            
            List<Order> orders = (List<Order>) session.getAttribute("orders");

            if (orders == null) {
                orders = new ArrayList<>();
            }

            orders.add(order);
            session.setAttribute("orders", orders);
            
            if (order.getStatus().equals(cancelOrder)) {
                // Lặp qua các item trong đơn hàng và phục hồi số lượng sản phẩm
                for (Item item : order.getItems()) {
                    int productId = item.getProduct().getId();
                    Product product = productDAO.getProductByID(productId);
                    int quantityToRestore = item.getQuantity(); // Số lượng cần phục hồi

                    // Kiểm tra và phục hồi số lượng sản phẩm trong kho
                    if (product != null) {
                        int currentQuantity = product.getQuantity(); // Số lượng hiện tại của sản phẩm trong kho
                        product.setQuantity(currentQuantity + quantityToRestore); // Cập nhật lại số lượng

                        // Cập nhật lại thông tin sản phẩm trong cơ sở dữ liệu
                        productDAO.update(product);
                    }
                }
            }
            

            cart.clear();

            session.setAttribute("cart", cart);

            String successScript = "<script>"
                    + "alert('Đặt hàng thành công');"
                    + "window.location.href = 'orders';"
                    + "</script>";
            response.getWriter().write(successScript);

        } catch (Exception e) {
            Order order = new Order(cart, name, address, phone);
            order.setStatus("Lỗi đặt hàng");
            session.setAttribute("order", order);

            String errorScript = "<script>"
                    + "alert('Đặt hàng thất bại');"
                    + "window.location.href = 'checkout';"
                    + "</script>";
            response.getWriter().write(errorScript);
        }
    }
}
