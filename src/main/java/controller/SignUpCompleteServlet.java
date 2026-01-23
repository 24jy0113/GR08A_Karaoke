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

        String userId   = (String) session.getAttribute("SIGNUP_USER_ID");
        String userName = (String) session.getAttribute("SIGNUP_USER_NAME");
        String password = (String) session.getAttribute("SIGNUP_PASSWORD");
        Integer roleId  = (Integer) session.getAttribute("SIGNUP_ROLE_ID");

        if (userId == null || roleId == null) {
            res.sendRedirect(req.getContextPath() + "/admin/sign_up.jsp");
            return;
        }


        UserDao.insertUser(userId, userName, password);
        UserDao.insertUserRole(userId, roleId);
        if (session.getAttribute("SIGNUP_USER_ID") == null) {
            res.sendRedirect(req.getContextPath() + "/admin/sign_up.jsp");
            return;
        }


        session.removeAttribute("SIGNUP_PASSWORD");
        session.removeAttribute("SIGNUP_USER_ID");
        session.removeAttribute("SIGNUP_USER_NAME");
        session.removeAttribute("SIGNUP_ROLE_ID");
        session.removeAttribute("SIGNUP_ROLE_NAME");
        
       

        res.sendRedirect(req.getContextPath() + "/admin/sign_up_confirmed.jsp");
    }
}

