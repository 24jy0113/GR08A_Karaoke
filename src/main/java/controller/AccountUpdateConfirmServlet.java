package controller;

import java.io.IOException;

import dao.UserDao;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import model.User;

@WebServlet("/AccountUpdateConfirmServlet")
public class AccountUpdateConfirmServlet extends HttpServlet {

    protected void doPost(HttpServletRequest req, HttpServletResponse res)
            throws ServletException, IOException {

        User u = (User) req.getSession().getAttribute("UPDATE_USER");

        if (u == null) {
            res.sendRedirect("account_search.jsp");
            return;
        }

        UserDao.updateUser(u);

        req.getSession().removeAttribute("UPDATE_USER");
        res.sendRedirect("account_update_confirmed.jsp");
    }
}

