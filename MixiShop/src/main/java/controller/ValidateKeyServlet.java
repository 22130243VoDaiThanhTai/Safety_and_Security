package controller;

import com.google.gson.Gson;
import model.Account;
import model.Cart;
import model.RSA;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@WebServlet("/validateKey")
public class ValidateKeyServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Thiết lập response trả về JSON
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        HttpSession session = request.getSession();
        Cart cart = (Cart) session.getAttribute("cart");
        Account account = (Account) session.getAttribute("account");
        System.out.println("id người dùng " + account.getId() + account.getUsername());
        String address = request.getParameter("address");
        String phone = request.getParameter("phone");

// Hàm để build JSON order
        String orderJson = cart.cartToJson(cart, account, address, phone);

// In ra chuỗi JSON đơn hàng
        System.out.println("✅ Chuỗi JSON đơn hàng để ký:");
        System.out.println(orderJson);

        BufferedReader reader = request.getReader();
        StringBuilder sb = new StringBuilder();
        String line;

        while ((line = reader.readLine()) != null) sb.append(line);
        System.out.println(line + " line nè");
        // Dùng Gson để parse JSON input
        Gson gson = new Gson();
        Map<String, String> json = gson.fromJson(sb.toString(), Map.class);
        String privateKeyBase64 = json.get("privateKey");
        System.out.println(privateKeyBase64 + " privatekey nè");

        boolean isValid = false;
        try {
            RSA rsa = new RSA();
            rsa.setPrivateKeyFromBase64(privateKeyBase64);
            isValid = true;  // Nếu không lỗi thì hợp lệ
        } catch (Exception e) {
            isValid = false; // Nếu lỗi => khóa không hợp lệ
        }

        // Trả về kết quả JSON
        Map<String, Boolean> result = new HashMap<>();
        result.put("valid", isValid);
        System.out.println(isValid);

        String jsonResponse = gson.toJson(result);
        response.getWriter().write(jsonResponse);
    }
}

