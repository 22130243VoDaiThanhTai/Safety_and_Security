package controller;

import java.io.IOException;
import java.net.URLEncoder;
import java.util.List;
import java.util.concurrent.TimeUnit;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

import dao.AccountDAO;
import dao.CategoryDAO;
import model.Account;
import model.Category;

@WebServlet("/login")
public class LoginController extends HttpServlet {
	private CategoryDAO categoryDAO = new CategoryDAO();
	private AccountDAO accountDAO = new AccountDAO();

	private static final int MAX_ATTEMPTS = 5;
	private static final long LOCK_TIME_MS = TimeUnit.MINUTES.toMillis(10);

	// Google OAuth
	private static final String CLIENT_ID = "907964223254-9j5im6uvq8ic45o8569o2bt40kkivam3.apps.googleusercontent.com";
	private static final String REDIRECT_URI = "http://localhost:8080/oauth2callback";
	private static final String SCOPE = "https://www.googleapis.com/auth/userinfo.email https://www.googleapis.com/auth/userinfo.profile";

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.setContentType("text/html; charset=UTF-8");
		response.setCharacterEncoding("UTF-8");

		String action = request.getParameter("action");
		if ("google".equals(action)) {
			doGoogleLogin(request, response);
			return;
		}

		List<Category> listCategory = categoryDAO.getAllCategory();
		request.setAttribute("listCategory", listCategory);
		request.setAttribute("contentPage", "login.jsp");
		RequestDispatcher dispatcher = request.getRequestDispatcher("base.jsp");
		dispatcher.forward(request, response);
	}

	private void doGoogleLogin(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		String oauthUrl = "https://accounts.google.com/o/oauth2/auth"
				+ "?client_id=" + CLIENT_ID
				+ "&redirect_uri=" + URLEncoder.encode(REDIRECT_URI, "UTF-8")
				+ "&response_type=code"
				+ "&scope=" + URLEncoder.encode(SCOPE, "UTF-8")
				+ "&access_type=offline";

		response.sendRedirect(oauthUrl);
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.setContentType("text/html; charset=UTF-8");
		response.setCharacterEncoding("UTF-8");

		String username = request.getParameter("username");
		String password = request.getParameter("password");

		HttpSession session = request.getSession();
		Integer failedAttempts = (Integer) session.getAttribute("failedAttempts");
		Long lockTime = (Long) session.getAttribute("lockTime");

		if (lockTime != null) {
			long currentTime = System.currentTimeMillis();
			if (currentTime - lockTime < LOCK_TIME_MS) {
				request.setAttribute("mess", "Tài khoản bị khóa do nhập sai quá nhiều lần. Vui lòng thử lại sau 10 phút.");
				request.setAttribute("contentPage", "login.jsp");
				RequestDispatcher dispatcher = request.getRequestDispatcher("base.jsp");
				dispatcher.forward(request, response);
				return;
			} else {
				session.removeAttribute("failedAttempts");
				session.removeAttribute("lockTime");
			}
		}

		Account account = accountDAO.authenticateUser(username, password);
		if (account != null) {
			session.setAttribute("account", account);
			session.removeAttribute("failedAttempts");
			session.removeAttribute("lockTime");
			response.sendRedirect("home");
		} else {
			if (failedAttempts == null) {
				failedAttempts = 1;
			} else {
				failedAttempts++;
			}
			session.setAttribute("failedAttempts", failedAttempts);

			if (failedAttempts >= MAX_ATTEMPTS) {
				session.setAttribute("lockTime", System.currentTimeMillis());
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
