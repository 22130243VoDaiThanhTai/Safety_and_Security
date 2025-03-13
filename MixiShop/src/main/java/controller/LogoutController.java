package controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.CategoryDAO;
import model.Category;

@WebServlet("/logout")
public class LogoutController extends HttpServlet {
	private CategoryDAO categoryDAO = new CategoryDAO();
	
	    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	        // Xóa session "account" để đăng xuất
	        response.setContentType("text/html; charset=UTF-8");
	        response.setCharacterEncoding("UTF-8");
	    	
	        request.getSession().removeAttribute("account");

	        // Chuyển hướng người dùng về trang home
	        response.sendRedirect(request.getContextPath() + "/home");
	    }
	}
