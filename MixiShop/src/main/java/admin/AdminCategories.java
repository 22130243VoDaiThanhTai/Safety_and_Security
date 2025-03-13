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
import model.Product;

/**
 * Servlet implementation class AdminCategory
 */
@WebServlet("/adminCategories")
public class AdminCategories extends HttpServlet {
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
		// TODO Auto-generated method stub
		List<Category> listCategory = categoryDAO.getAllCategory();
		
		request.setAttribute("listCategory", listCategory);
        request.setAttribute("contentPage", "adminCategories.jsp");

        RequestDispatcher dispatcher = request.getRequestDispatcher("base.jsp");
        dispatcher.forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html; charset=UTF-8");
        response.setCharacterEncoding("UTF-8");
		
        HttpSession session = request.getSession();
		String action = request.getParameter("action");

	    if ("deleted".equals(action)) {
	        int categoryId = Integer.parseInt(request.getParameter("categoryCode"));
	        
	        int deleted = categoryDAO.deleteCategoryById(categoryId);
	        
	        // Xử lý thông báo dựa trên kết quả của việc xóa sản phẩm
	        if (deleted > 0) {
	            session.setAttribute("notification", "<i class=\"fa-solid fa-circle-check text-success\"></i> Xóa danh mục thành công");
	            session.setAttribute("notificationType", "success");
	        } else {
	            session.setAttribute("notification", "<i class=\"fa-solid fa-circle-xmark text-danger\"></i> Thêm danh mục thất bại");
	            session.setAttribute("notificationType", "error");
	        }
	        
	        // Lấy lại danh sách sản phẩm và chuyển tiếp đến trang adminProducts.jsp
			doGet(request, response);
	    }
	}

}
