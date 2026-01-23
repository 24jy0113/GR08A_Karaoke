package controller;

import java.io.IOException;

import dao.UserDao;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import model.User;

@WebServlet("/AccountUpdateInitServlet")
public class AccountUpdateInitServlet extends HttpServlet {

    protected void doPost(HttpServletRequest req, HttpServletResponse res)
            throws ServletException, IOException {

        req.setCharacterEncoding("UTF-8");
        String userId = req.getParameter("userId");

        if (userId == null || userId.isEmpty()) {
            res.sendRedirect(req.getContextPath() + "/admin/account_search.jsp");
            return;
        }

        User u = UserDao.searchUserByUserId(userId);

        if (u == null) {
            res.sendRedirect(req.getContextPath() + "/admin/account_search.jsp");
            return;
        }

        if (u.getRoleName() != null) {
            u.setRoleId(UserDao.findRoleIdByRoleName(u.getRoleName()));
        }

        req.getSession().setAttribute("UPDATE_USER", u);

        req.getRequestDispatcher("/admin/account_update.jsp")
           .forward(req, res);
   
    }
}

