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

/**
 * Servlet implementation class AccountSearchServlet
 */
@WebServlet("/AccountSearchServlet")
public class AccountSearchServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		request.setCharacterEncoding("UTF-8");

		String keyword = request.getParameter("keyword");

		UserDao userDao = new UserDao();

		ArrayList<User> userList = new ArrayList<>();

		if (keyword != null && !keyword.isEmpty()) {

			User user = userDao.findById(keyword);

			if (user != null) {
				userList.add(user);
			} else {
				userList = userDao.findByUserName(keyword);
			}
		}

		request.setAttribute("userList", userList);
		request.getRequestDispatcher("account_search_result.jsp")
				.forward(request, response);
	}

}
