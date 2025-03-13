package controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.mysql.cj.Session;

import dao.AccountDAO;
import dao.CategoryDAO;
import dao.ProductDAO;
import model.Category;
import model.Account;

@WebServlet("/login")
public class LoginController extends HttpServlet {
	private CategoryDAO categoryDAO = new CategoryDAO();
	private AccountDAO accountDAO = new AccountDAO();

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html; charset=UTF-8");
        response.setCharacterEncoding("UTF-8");
		
		List<Category> listCategory = categoryDAO.getAllCategory();

		request.setAttribute("listCategory", listCategory);
		request.setAttribute("contentPage", "login.jsp");
		RequestDispatcher dispatcher = request.getRequestDispatcher("base.jsp");
		dispatcher.forward(request, response);
	}
	protected void doPost(HttpServletRequest request, HttpServletResponse response)throws ServletException, IOException {		
        response.setContentType("text/html; charset=UTF-8");
        response.setCharacterEncoding("UTF-8");
		
		String username = request.getParameter("username");
		String password = request.getParameter("password");
		
		Account account = accountDAO.authenticateUser(username, password);
		if ( account != null) {
			
			HttpSession session = request.getSession();
			session.setAttribute("account", account);	
			response.sendRedirect("home");
		}
		else {
			 request.setAttribute("mess", "Tên đăng nhập hoặc mật khẩu không chính xác");
			 request.setAttribute("contentPage", "login.jsp");
				RequestDispatcher dispatcher = request.getRequestDispatcher("base.jsp");
				dispatcher.forward(request, response);
		}
	}
	
}
