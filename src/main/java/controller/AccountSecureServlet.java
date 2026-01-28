package controller;

import java.io.IOException;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import dao.UserDao;
import model.User;

@WebServlet("/AccountSecureServlet")
public class AccountSecureServlet extends HttpServlet {
		protected void doGet(HttpServletRequest req, HttpServletResponse res)
	            throws ServletException, IOException {
	        doPost(req, res);
	    }
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse res)
            throws ServletException, IOException {

        req.setCharacterEncoding("UTF-8");

        HttpSession session = req.getSession(false);
        User loginUser = (User) session.getAttribute("loginUser");

        if (loginUser == null) {
            res.sendRedirect("login.jsp");
            return;
        }

        String rawUserId = req.getParameter("userId");
        String password  = req.getParameter("password");

        if (rawUserId == null || password == null) {
            req.setAttribute("error", "");
            req.getRequestDispatcher("/admin/account_secure.jsp").forward(req, res);
            return;
        }

        rawUserId = rawUserId.trim();

        if (!rawUserId.equals(loginUser.getUserId())) {
            req.setAttribute("error", "ログイン中のアカウントと一致しません");
            req.getRequestDispatcher("/admin/account_secure.jsp").forward(req, res);
            return;
        }

        boolean ok;
		try {
			ok = UserDao.checkPassword(rawUserId, password);
			if (!ok) {
	            req.setAttribute("error", "パスワードが違います");
	            req.getRequestDispatcher("/admin/account_secure.jsp").forward(req, res);
	            return;
	        }
		} catch (Exception e) {
			// TODO 自動生成された catch ブロック
			e.printStackTrace();
		}
        

        session.setAttribute("ACCOUNT_REAUTH_OK", true);

        res.sendRedirect(req.getContextPath() + "/admin/account_search.jsp");
    }
}
