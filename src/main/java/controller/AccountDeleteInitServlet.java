package controller;
import java.io.IOException;

import dao.UserDao;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import model.User;

@WebServlet("/AccountDeleteInitServlet")
public class AccountDeleteInitServlet extends HttpServlet {

    protected void doPost(HttpServletRequest req, HttpServletResponse res)
            throws ServletException, IOException {

        String userId = req.getParameter("userId");
        if (userId == null) {
            res.sendRedirect(req.getContextPath() + "/admin/account_search.jsp");
            return;
        }

        User u = UserDao.searchUserByUserId(userId);
        req.getSession().setAttribute("DELETE_USER", u);

        req.getRequestDispatcher("/admin/account_delete_notice.jsp").forward(req, res);
    }
}
