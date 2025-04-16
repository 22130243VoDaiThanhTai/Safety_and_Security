package controller;

import model.Account;
import dao.AccountDAO;
import org.json.JSONObject;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.stream.Collectors;

@WebServlet("/oauth2callback")
public class GoogleCallbackController extends HttpServlet {

    private static final String CLIENT_ID = "907964223254-9j5im6uvq8ic45o8569o2bt40kkivam3.apps.googleusercontent.com";
    private static final String CLIENT_SECRET = "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";
    private static final String REDIRECT_URI = "http://localhost:8080/oauth2callback";

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String code = request.getParameter("code");
        if (code == null || code.isEmpty()) {
            response.getWriter().println("Missing code parameter");
            return;
        }

        // Gửi yêu cầu để lấy access token
        String tokenUrl = "https://oauth2.googleapis.com/token";
        String urlParameters = "code=" + URLEncoder.encode(code, "UTF-8") +
                "&client_id=" + URLEncoder.encode(CLIENT_ID, "UTF-8") +
                "&client_secret=" + URLEncoder.encode(CLIENT_SECRET, "UTF-8") +
                "&redirect_uri=" + URLEncoder.encode(REDIRECT_URI, "UTF-8") +
                "&grant_type=authorization_code";

        HttpURLConnection tokenConn = (HttpURLConnection) new URL(tokenUrl).openConnection();
        tokenConn.setRequestMethod("POST");
        tokenConn.setDoOutput(true);
        tokenConn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");

        try (OutputStream os = tokenConn.getOutputStream()) {
            os.write(urlParameters.getBytes());
            os.flush();
        }

        String tokenResponse;
        try (BufferedReader in = new BufferedReader(new InputStreamReader(tokenConn.getInputStream()))) {
            tokenResponse = in.lines().collect(Collectors.joining());
        } catch (IOException e) {
            String errorMsg;
            try (BufferedReader errorReader = new BufferedReader(new InputStreamReader(tokenConn.getErrorStream()))) {
                errorMsg = errorReader.lines().collect(Collectors.joining());
            }
            throw new RuntimeException("Failed to get access token: " + errorMsg);
        }

        JSONObject jsonObject = new JSONObject(tokenResponse);
        String accessToken = jsonObject.getString("access_token");

        // Lấy thông tin người dùng từ access token
        String userInfoUrl = "https://www.googleapis.com/oauth2/v2/userinfo?access_token=" + accessToken;
        HttpURLConnection userConn = (HttpURLConnection) new URL(userInfoUrl).openConnection();

        String userInfo;
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(userConn.getInputStream()))) {
            userInfo = reader.lines().collect(Collectors.joining());
        }

        JSONObject userJson = new JSONObject(userInfo);
        String email = userJson.getString("email");
        String name = userJson.getString("name");

        // Kiểm tra xem người dùng đã có tài khoản trong cơ sở dữ liệu chưa
        AccountDAO accountDAO = new AccountDAO();
        Account existingAccount = accountDAO.findUserByEmail(email);

        if (existingAccount == null) {
            // Tạo tài khoản mới nếu chưa có
            Account newAccount = new Account();
            newAccount.setEmail(email);
            newAccount.setUsername(name); // Hoặc set username nếu cần
            newAccount.setRole(1); // Role mặc định hoặc theo yêu cầu (0: Admin, 1: User)

            // Lưu tài khoản mới vào database
            accountDAO.registerGoogleUser(newAccount);
            existingAccount = newAccount; // Cập nhật tài khoản mới tạo vào session
        }

        // Lưu thông tin vào session
        HttpSession session = request.getSession();
        session.setAttribute("account", existingAccount);

        // Chuyển về trang chủ
        response.sendRedirect(request.getContextPath() + "/home");
    }
}
