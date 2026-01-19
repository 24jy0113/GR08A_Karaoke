package controller;
import java.io.IOException;


import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import model.User;
@WebServlet("/AccountUpdateServlet")
public class AccountUpdateServlet extends HttpServlet {

    protected void doPost(HttpServletRequest req, HttpServletResponse res)
            throws ServletException, IOException {

        req.setCharacterEncoding("UTF-8");

        User u = new User();
        u.setUserId(req.getParameter("userId"));
        u.setUserName(req.getParameter("userName"));

        req.getSession().setAttribute("UPDATE_USER", u);
        res.sendRedirect("account_update_confirm.jsp");
    }
}

