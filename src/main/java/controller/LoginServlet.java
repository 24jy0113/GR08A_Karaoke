package controller;

import java.io.IOException;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import dao.UserDao;
import model.User;

/**
 * Servlet implementation class LoginServlet
 */
@WebServlet("/LoginServlet")
public class LoginServlet extends HttpServlet {

	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        req.setCharacterEncoding("UTF-8");
        String rawUserId = req.getParameter("userId");
        String password = req.getParameter("password");
        String userId;
        
        try {
            userId = String.format("%06d", Integer.parseInt(rawUserId.trim()));
        } catch (NumberFormatException e) {
            req.setAttribute("error", "アカウントIDは6桁の数字で入力してください");
            req.getRequestDispatcher("index.jsp").forward(req, resp);
            return;
        }
        User user = UserDao.login(userId, password);

        if (user == null) {
        	System.out.println("Login failed: user == null");
            req.setAttribute("error", "アカウントIDまたはパスワードが違います");
            req.getRequestDispatcher("index.jsp").forward(req, resp);
            return;
        }

        HttpSession session = req.getSession();
        session.setAttribute("loginUser", user);
        
        String role = user.getRoleName().trim();
        String context = req.getContextPath();
        
        switch (user.getRoleName()) {
            case "フロント":
                resp.sendRedirect(context + "/front_top.jsp");
                break;
            case "キッチン":
                resp.sendRedirect(context + "/kitchen_order_list.jsp");
                break;
            case "フロア":
                resp.sendRedirect(context + "/floor_order_list.jsp");
                break;
            case "管理者":
                resp.sendRedirect(context + "/manage_top.jsp");
                break;
            default:
                resp.sendRedirect(context + "/index.jsp");
        }

    }
}