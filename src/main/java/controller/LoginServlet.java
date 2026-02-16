package controller;

import java.io.IOException;
import java.util.Set;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import dao.PermissionDAO;
import dao.UserDao;
import model.User;

/**
 * Servlet implementation class LoginServlet
 */
@WebServlet("/LoginServlet")
public class LoginServlet extends HttpServlet {

	protected void doPost(HttpServletRequest req, HttpServletResponse res)
            throws ServletException, IOException {

        req.setCharacterEncoding("UTF-8");
        String rawUserId = req.getParameter("userId");
        String password = req.getParameter("password");
        String userId;
        
        if (rawUserId == null || password == null) {
            res.sendRedirect(req.getContextPath() + "/login.jsp");
            return;
        }
        try {
            userId = String.format("%06d", Integer.parseInt(rawUserId.trim()));
            if (userId.isEmpty() || password.isEmpty()) {
                req.setAttribute("errorMsg", "ユーザーIDとパスワードを入力してください");
                req.getRequestDispatcher("/login.jsp").forward(req, res);
                return;
            }
        } catch (NumberFormatException e) {
            req.setAttribute("error", "アカウントIDは6桁の数字で入力してください");
            req.getRequestDispatcher("index.jsp").forward(req, res);
            return;
        }
        User user = UserDao.login(userId, password);

        if (user == null) {
            req.setAttribute("error", "アカウントIDまたはパスワードが違います");
            req.getRequestDispatcher("index.jsp").forward(req, res);
            return;
        }
        
        UserDao.updateLastLoginTime(user.getUserId());
        
        HttpSession session = req.getSession(true);
        session.setAttribute("loginUser", user);
        session.setAttribute("user_id", user.getUserId());

        Set<String> permissions = PermissionDAO.getPermissionsByUserId(user.getUserId());

        session.setAttribute("permissions", permissions);
        
        String role = user.getRoleName().trim();
        String context = req.getContextPath();
        
        switch (user.getRoleName()) {
           
            case "キッチン":
                res.sendRedirect(context + "/KitchenOrderList");
                break;
            case "フロント":
                res.sendRedirect(context + "/front/front_top.jsp");
                break;
            case "フロア":
                res.sendRedirect(context + "/index_select.jsp");
                break;
            case "管理者":
                res.sendRedirect(context + "/index_select.jsp");
                break;
            default:
                res.sendRedirect(context + "/index.jsp");
        }

    }
}