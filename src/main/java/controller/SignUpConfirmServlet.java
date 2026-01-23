package controller;
import java.io.IOException;

import dao.UserDao;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebServlet("/SignUpConfirmServlet")
public class SignUpConfirmServlet extends HttpServlet {

    protected void doPost(HttpServletRequest req, HttpServletResponse res)
            throws ServletException, IOException {

        req.setCharacterEncoding("UTF-8");

        String userName = req.getParameter("userName");
        String password = req.getParameter("password");
        String roleName = req.getParameter("roleName");
        int roleId = UserDao.findRoleIdByRoleName(roleName);

        if (userName == null || userName.isEmpty()
            || password == null || password.isEmpty()
            || roleName == null || roleName.isEmpty()) {
            req.setAttribute("error", "入力内容に不備があります");
            req.getRequestDispatcher(req.getContextPath() + "/admin/sign_up.jsp").forward(req, res);
            return;
        }

        String newUserId = UserDao.generateNextUserId();

        //sessionに入れる
        HttpSession session = req.getSession();
        session.setAttribute("SIGNUP_USER_ID", newUserId);
        session.setAttribute("SIGNUP_USER_NAME", userName);
        session.setAttribute("SIGNUP_PASSWORD", password);
        session.setAttribute("SIGNUP_ROLE_ID", roleId);  //DAO insert 用   
        session.setAttribute("SIGNUP_ROLE_NAME", roleName);  //確認ページで表示用

        res.sendRedirect(req.getContextPath() + "/admin/sign_up_confirm.jsp");
    }
}

