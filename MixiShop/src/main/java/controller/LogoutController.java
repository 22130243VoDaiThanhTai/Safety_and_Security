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

import dao.CategoryDAO;
import model.Category;

@WebServlet("/logout")
public class LogoutController extends HttpServlet {
	private CategoryDAO categoryDAO = new CategoryDAO();
	
	    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	        // Xóa session "account" để đăng xuất
	        response.setContentType("text/html; charset=UTF-8");
	        response.setCharacterEncoding("UTF-8");

			HttpSession session = request.getSession(false); // Tránh tạo session mới nếu chưa có

			if (session != null) {
				session.invalidate();
//				session.removeAttribute("account"); // Hủy toàn bộ session khi đăng xuất
			}
	        response.sendRedirect(request.getContextPath() + "/home");
	    }
	}
