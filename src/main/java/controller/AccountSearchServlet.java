package controller;

import java.io.IOException;
import java.util.ArrayList;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import dao.UserDao;
import model.User;

@WebServlet("/AccountSearchServlet")
public class AccountSearchServlet extends HttpServlet {

    protected void doGet(HttpServletRequest req, HttpServletResponse res)
            throws ServletException, IOException {

        req.setCharacterEncoding("UTF-8");

        String keyword = req.getParameter("keyword");
        if (keyword != null) {
            keyword = keyword.replace("　", " ").trim();
        }
        
        ArrayList<User> list = new ArrayList<>();

        if (keyword != null && !keyword.isBlank()) {
            User u = UserDao.searchUserByUserId(keyword);
            if (u != null) {
                list.add(u);
            } else {
                list = UserDao.searchUserByUserName(keyword);
            }
        }

        req.setAttribute("userList", list);
        req.getRequestDispatcher("/admin/account_search_result.jsp").forward(req, res);
    }
}
