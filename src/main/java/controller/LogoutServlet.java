package controller;

import java.io.IOException;
import java.net.URLEncoder;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

/**
 * Servlet implementation class LogoutServlet
 */
@WebServlet("/LogoutServlet")
public class LogoutServlet extends HttpServlet {

	protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		HttpSession session = req.getSession(false);

		if (session != null) {
			session.invalidate();
		}

		HttpSession newSession = req.getSession(true);
		// フロントエンド用のメッセージ.
		String message = URLEncoder.encode("ログアウトしました。", "UTF-8");

		res.sendRedirect(req.getContextPath() + "/index.jsp?logoutMsg=" + message);
	}

}
