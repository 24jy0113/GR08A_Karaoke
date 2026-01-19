package controller;

import java.io.IOException;

import dao.UserDao;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import model.User;
@WebServlet("/AccountDeleteServlet")
public class AccountDeleteServlet extends HttpServlet {

    protected void doPost(HttpServletRequest req, HttpServletResponse res)
            throws ServletException, IOException {

        req.setCharacterEncoding("UTF-8");

        String userId = req.getParameter("userId");

        if (userId == null) {
            res.sendRedirect("account_search.jsp");
            return;
        }

        UserDao.deleteUser(userId);

        res.sendRedirect("account_deleted_msg.jsp");
    }
}

