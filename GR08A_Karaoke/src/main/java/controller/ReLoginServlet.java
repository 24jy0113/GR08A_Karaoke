package controller;

import java.io.IOException;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import dao.UserDao;

/**
 * Servlet implementation class ReLoginServlet
 */
@WebServlet("/ReLoginServlet")
public class ReLoginServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private UserDao userDao = new UserDao();
	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		request.setCharacterEncoding("UTF-8");

		String userId = request.getParameter("userId");
		String password = request.getParameter("password");
		
		boolean isValidUser = userDao.validate(userId,password);
		

		// 仮の認証処理（本来はDAOでDB確認）
		if ("SF0112".equals(userId) && "1234".equals(password)) {
			// ログイン成功
			request.getRequestDispatcher("account_search.jsp")
					.forward(request, response);
		} else {
			// ログイン失敗
			request.setAttribute("error", "アカウントIDまたはパスワードが違います。");
			request.getRequestDispatcher("re_login.jsp")
					.forward(request, response);
		}
	}

}
