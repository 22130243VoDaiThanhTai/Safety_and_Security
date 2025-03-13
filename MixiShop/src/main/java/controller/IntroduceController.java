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

@WebServlet("/introduce")
public class IntroduceController extends HttpServlet {
	private CategoryDAO categoryDAO = new CategoryDAO();

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html; charset=UTF-8");
        response.setCharacterEncoding("UTF-8");
		
		List<Category> listCategory = categoryDAO.getAllCategory();

		request.setAttribute("listCategory", listCategory);
		request.setAttribute("contentPage", "introduce.jsp");
		RequestDispatcher dispatcher = request.getRequestDispatcher("base.jsp");
		dispatcher.forward(request, response);
	}
}
