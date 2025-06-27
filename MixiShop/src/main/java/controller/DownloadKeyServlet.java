package controller;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URLEncoder;

@WebServlet("/download")
public class DownloadKeyServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String type = request.getParameter("type");

        if (type == null || (!type.equals("public") && !type.equals("private"))) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid key type");
            return;
        }

        HttpSession session = request.getSession();
        String key = (String) session.getAttribute(type + "Key");

        if (key == null) {
            response.sendError(HttpServletResponse.SC_NOT_FOUND, "Key not found in session");
            return;
        }

        // Gói key với định dạng yêu cầu
        String decoratedKey = "------------------" + type + " key------------------\n"
                + key + "\n"
                + "------------------" + type + " key------------------";

        String filename = type + "_key.txt";

        // Cấu hình tải file
        response.setContentType("text/plain");
        response.setHeader("Content-Disposition", "attachment;filename=\"" + URLEncoder.encode(filename, "UTF-8") + "\"");

        try (OutputStream out = response.getOutputStream()) {
            out.write(decoratedKey.getBytes());
            out.flush();
        }
    }
}
