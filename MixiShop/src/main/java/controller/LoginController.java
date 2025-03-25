package controller;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.TimeUnit;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import dao.AccountDAO;
import dao.CategoryDAO;
import model.Category;
import model.Account;

@WebServlet("/login")
public class LoginController extends HttpServlet {
	private CategoryDAO categoryDAO = new CategoryDAO();
	private AccountDAO accountDAO = new AccountDAO();
	private static final int MAX_ATTEMPTS = 5;  // Số lần nhập sai tối đa
	private static final long LOCK_TIME_MS = TimeUnit.MINUTES.toMillis(1); // Khóa 10 phút

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html; charset=UTF-8");
		response.setCharacterEncoding("UTF-8");

		List<Category> listCategory = categoryDAO.getAllCategory();
		request.setAttribute("listCategory", listCategory);
		request.setAttribute("contentPage", "login.jsp");
		RequestDispatcher dispatcher = request.getRequestDispatcher("base.jsp");
		dispatcher.forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html; charset=UTF-8");
		response.setCharacterEncoding("UTF-8");

		String username = request.getParameter("username");
		String password = request.getParameter("password");

		HttpSession session = request.getSession();
		Integer failedAttempts = (Integer) session.getAttribute("failedAttempts");
		Long lockTime = (Long) session.getAttribute("lockTime");

		// Kiểm tra nếu tài khoản bị khóa
		if (lockTime != null) {
			long currentTime = System.currentTimeMillis();
			if (currentTime - lockTime < LOCK_TIME_MS) {
				request.setAttribute("mess", "Tài khoản bị khóa do nhập sai quá nhiều lần. Vui lòng thử lại sau 10 phút.");
				request.setAttribute("contentPage", "login.jsp");
				RequestDispatcher dispatcher = request.getRequestDispatcher("base.jsp");
				dispatcher.forward(request, response);
				return;
			} else {
				// Hết thời gian khóa, đặt lại số lần nhập sai
				session.removeAttribute("failedAttempts");
				session.removeAttribute("lockTime");
			}
		}

		Account account = accountDAO.authenticateUser(username, password);
		if (account != null) {
			session.setAttribute("account", account);
			session.removeAttribute("failedAttempts"); // Reset số lần nhập sai
			session.removeAttribute("lockTime");
			response.sendRedirect("home");
		} else {
			// Nếu nhập sai, tăng số lần nhập sai lên
			if (failedAttempts == null) {
				failedAttempts = 1;
			} else {
				failedAttempts++;
			}

			session.setAttribute("failedAttempts", failedAttempts);

			if (failedAttempts >= MAX_ATTEMPTS) {
				session.setAttribute("lockTime", System.currentTimeMillis()); // Lưu thời gian khóa
				request.setAttribute("mess", "Bạn đã nhập sai mật khẩu 5 lần. Tài khoản bị khóa trong 10 phút.");
			} else {
				request.setAttribute("mess", "Tên đăng nhập hoặc mật khẩu không chính xác. Lần thử thứ " + failedAttempts + "/5.");
			}

			request.setAttribute("contentPage", "login.jsp");
			RequestDispatcher dispatcher = request.getRequestDispatcher("base.jsp");
			dispatcher.forward(request, response);
		}
	}
}
