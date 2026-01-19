package controller;

import java.io.IOException;

import dao.UserDao;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import model.User;

@WebServlet("/SignUpCompleteServlet")
public class SignUpCompleteServlet extends HttpServlet {

    protected void doPost(HttpServletRequest req, HttpServletResponse res)
            throws ServletException, IOException {

        HttpSession session = req.getSession(false);

        String userId = (String) session.getAttribute("SIGNUP_USER_ID");
        String userName = (String) session.getAttribute("SIGNUP_USER_NAME");
        String password = (String) session.getAttribute("SIGNUP_PASSWORD");
        String roleIdStr = (String) session.getAttribute("SIGNUP_ROLE_ID");

        if (userId == null) {
            res.sendRedirect(req.getContextPath() + "/admin/sign_up.jsp");
            return;
        }
        if (roleIdStr == null) {
            res.sendRedirect(req.getContextPath() + "/admin/sign_up.jsp");
            return;
        }
        
        int roleId = Integer.parseInt(roleIdStr);
        String newUserId = UserDao.generateNextUserId();

        UserDao.insertUser(userId, userName, password);
        UserDao.insertUserRole(newUserId, roleId);

        session.removeAttribute("SIGNUP_PASSWORD");

        res.sendRedirect(req.getContextPath() + "/admin/sign_up_confirmed.jsp");
    }
}

