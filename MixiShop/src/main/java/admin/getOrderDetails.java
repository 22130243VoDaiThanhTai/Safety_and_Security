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

import com.google.gson.Gson;
import dao.AccountDAO;
import dao.CategoryDAO;
import dao.OrderDao;
import dao.ProductDAO;
import model.*;

/**
 * Servlet implementation class AdminProducts
 */
@WebServlet("/getOrderDetail")
public class getOrderDetails extends HttpServlet {
    private OrderDao orderDao = new OrderDao();
    /**
     * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html; charset=UTF-8");
        response.setCharacterEncoding("UTF-8");
        int orderId = Integer.parseInt(request.getParameter("orderId"));
        System.out.println("CON CU" + orderId);
        List<OrderDetail> details = null;
        try {
            details = orderDao.getOrderDetails(orderId);
            System.out.println("CON CU");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        Gson gson = new Gson();
        String json = gson.toJson(details);

        response.setContentType("application/json");
        response.getWriter().write(json);
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


