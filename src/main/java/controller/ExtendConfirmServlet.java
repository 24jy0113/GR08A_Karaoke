package controller;

import java.io.IOException;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import action.ExtendAction;

@WebServlet("/ExtendConfirmServlet")
public class ExtendConfirmServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public ExtendConfirmServlet() {
		super();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		HttpSession session = request.getSession();

		Integer extendMinutes = (Integer) session.getAttribute("extendMinutes");

		try {
			new ExtendAction().execute(extendMinutes, session);
			session.removeAttribute("extendMinutes"); // sessionの後始末.
			// 完了画面へ行く.
			response.sendRedirect(request.getContextPath() + "/time_extend_confirmed.jsp");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
