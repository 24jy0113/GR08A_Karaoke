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

@WebServlet("/AccountUpdateConfirmServlet")
public class AccountUpdateConfirmServlet extends HttpServlet {

    protected void doPost(HttpServletRequest req, HttpServletResponse res)
            throws ServletException, IOException {
    	
    	req.setCharacterEncoding("UTF-8");
    	
    	User u = (User) req.getSession().getAttribute("UPDATE_USER");
        if (u == null) {
            res.sendRedirect(req.getContextPath() + "/admin/account_search.jsp");
            return;
        }

        String userName = req.getParameter("userName");
        if (userName != null && !userName.isEmpty()) {
            u.setUserName(userName);
        }

        String password = req.getParameter("password");
        if (password != null && !password.isEmpty()) {
            u.setPasswordHash(PasswordUtil.hash(password));
        }
        
        String roleName = req.getParameter("roleName");
        if (roleName != null && !roleName.isEmpty()) {
            u.setRoleName(roleName);
            u.setRoleId(UserDao.findRoleIdByRoleName(roleName));
        }

        boolean success = UserDao.updateUser(u);

        if (!success) {
            req.setAttribute("errorMessage", "アカウント更新に失敗しました");
            req.getRequestDispatcher("/admin/account_update_confirm.jsp")
               .forward(req, res);
            return;
        }

        req.getSession().setAttribute("UPDATE_USER", u);
        res.sendRedirect(req.getContextPath() + "/admin/account_update_confirmed.jsp");
    }
}

