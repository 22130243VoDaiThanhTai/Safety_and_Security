package admin;

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
import model.Account;
import model.Category;

@WebServlet("/admin")
public class AdminController extends HttpServlet {
	private CategoryDAO categoryDAO = new CategoryDAO();
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html; charset=UTF-8");
        response.setCharacterEncoding("UTF-8");
    	
    	HttpSession session = request.getSession(false);

        // Kiểm tra nếu session chưa được khởi tạo hoặc không có tài khoản
        if (session == null || session.getAttribute("account") == null) {
        	response.setContentType("text/html;charset=UTF-8");
            response.getWriter().println("<script type=\"text/javascript\">");
            response.getWriter().println("alert('Truy cập không hợp lệ! Vui lòng đăng nhập bằng tài khoản ADMIN!');");
            response.getWriter().println("window.location.href = 'login';");
            response.getWriter().println("</script>");	
            return;
        }

        // Lấy thông tin tài khoản từ session
        Account account = (Account) session.getAttribute("account");

        // Kiểm tra nếu tài khoản có role khác 0
        if (account.getRole() != 0) {
        	 response.setContentType("text/html;charset=UTF-8");
             response.getWriter().println("<script type=\"text/javascript\">");
             response.getWriter().println("alert('Truy cập không hợp lệ! Vui lòng đăng nhập bằng tài khoản ADMIN!');");
             response.getWriter().println("window.location.href = 'login';");
             response.getWriter().println("</script>");	
            return;
        }
        
        List<Category> listCategory = categoryDAO.getAllCategory();
        request.setAttribute("listCategory", listCategory);
        // Nếu tài khoản có role = 0, cho phép truy cập trang admin
        request.setAttribute("contentPage", "admin.jsp");
        RequestDispatcher dispatcher = request.getRequestDispatcher("base.jsp");
        dispatcher.forward(request, response);
    }
}


