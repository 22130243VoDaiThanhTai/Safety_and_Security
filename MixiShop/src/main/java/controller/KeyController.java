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
import dao.KeyDao;
import dao.ProductDAO;
import model.*;

@WebServlet("/generateKey")
public class KeyController extends HttpServlet {
    KeyDao keyDao;

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html; charset=UTF-8");
        response.setCharacterEncoding("UTF-8");



        RequestDispatcher dispatcher = request.getRequestDispatcher("tool.jsp");
        dispatcher.forward(request, response);

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html; charset=UTF-8");
        response.setCharacterEncoding("UTF-8");
        HttpSession session = request.getSession();
        Account account = (Account) session.getAttribute("account");
        System.out.println(account.getUsername());
        keyDao = new KeyDao();
        try {
            String keySizeStr = request.getParameter("keySize");
            int keySize = (keySizeStr != null) ? Integer.parseInt(keySizeStr) : 2048;

            RSA rsa = new RSA();
            rsa.genKey(keySize);

            String publicKey = rsa.getPublicKeyBase64();
            String privateKey = rsa.getPrivateKeyBase64();
            keyDao.deactivateOldKeys(account.getId());

            RSAKey key = new RSAKey(account.getId(),publicKey,true);
            keyDao.saveKey(key);
            // Set lên request để hiển thị
            request.setAttribute("publicKey", publicKey);
            request.setAttribute("privateKey", privateKey);

            // Set lên session để download sử dụng sau
            session.setAttribute("publicKey", publicKey);
            session.setAttribute("privateKey", privateKey);

        } catch (Exception e) {
            request.setAttribute("error", "Lỗi tạo khóa: " + e.getMessage());
        }

        RequestDispatcher dispatcher = request.getRequestDispatcher("tool.jsp");
        dispatcher.forward(request, response);
    }


}

