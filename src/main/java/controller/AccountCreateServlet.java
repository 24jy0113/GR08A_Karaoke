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

@WebServlet("/AccountCreateServlet")
public class AccountCreateServlet extends HttpServlet {

    protected void doPost(HttpServletRequest req, HttpServletResponse res)
            throws ServletException, IOException {

        req.setCharacterEncoding("UTF-8");

        HttpSession session = req.getSession(false);
        User loginUser = (User) session.getAttribute("loginUser");

        String userName = req.getParameter("userName");
        String password = req.getParameter("password");
        String[] roleIds = req.getParameterValues("roleIds");

        if (userName == null || password == null || roleIds == null) {
            req.setAttribute("error", "入力内容が不足しています");
            req.getRequestDispatcher("/admin/account_add.jsp").forward(req, res);
            return;
        }

        res.sendRedirect(req.getContextPath() + "/admin/sign_up_confirm.jsp");
    }
}

