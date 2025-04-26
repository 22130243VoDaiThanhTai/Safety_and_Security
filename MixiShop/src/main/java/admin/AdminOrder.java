package admin;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import dao.AccountDAO;
import dao.CategoryDAO;
import dao.OrderDao;
import dao.ProductDAO;
import model.Account;
import model.Category;
import model.Order;
import model.Product;

/**
 * Servlet implementation class AdminProducts
 */
@WebServlet("/adminOrders")
public class AdminOrder extends HttpServlet {
    private OrderDao orderDao = new OrderDao();
    /**
     * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html; charset=UTF-8");
        response.setCharacterEncoding("UTF-8");
        List<Order> listOrder = new ArrayList<Order>();
        try {
            listOrder = orderDao.getAllOrder();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        request.setAttribute("listOrder", listOrder);
        request.setAttribute("contentPage", "adminOrder.jsp");

        RequestDispatcher dispatcher = request.getRequestDispatcher("base.jsp");
        dispatcher.forward(request, response);
    }

    /**
     * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
     */
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html; charset=UTF-8");
        response.setCharacterEncoding("UTF-8");
        doGet(request, response);
    }
}

