package controller;

import java.io.IOException;

import dao.UserDao;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import model.User;
import util.PasswordUtil;

@WebServlet("/AccountUpdateServlet")
public class AccountUpdateServlet extends HttpServlet {

    protected void doPost(HttpServletRequest req, HttpServletResponse res)
            throws ServletException, IOException {

        req.setCharacterEncoding("UTF-8");

        User u = (User) req.getSession().getAttribute("UPDATE_USER");
        if (u == null) {
            res.sendRedirect(req.getContextPath() + "/admin/account_search.jsp");
            return;
        }

        String userId   = req.getParameter("userId");
        String userName = req.getParameter("userName");
        String password = req.getParameter("password"); 
        String roleName = req.getParameter("roleName");

        if (userName == null || userName.isEmpty()
                || roleName == null || roleName.isEmpty()) {

            req.getSession().setAttribute("error", "入力内容に不備があります");
            res.sendRedirect(req.getContextPath() + "/admin/account_update.jsp");
            return;
        }

        u.setUserId(userId);
        u.setUserName(userName);
        u.setRoleName(roleName);
        u.setRoleId(UserDao.findRoleIdByRoleName(roleName));

        if (password != null && !password.isEmpty()) {
            u.setPasswordHash(PasswordUtil.hash(password));
        }

        req.getSession().setAttribute("UPDATE_USER", u);
        res.sendRedirect(req.getContextPath() + "/admin/account_update_confirm.jsp");
    }
}