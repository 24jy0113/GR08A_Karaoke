package controller;

import java.io.IOException;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import action.Action;
import action.ItemUpdateAction;
import action.ItemUpdateConfirmAction;
import action.ItemUpdateExecuteAction;

/**
 * Servlet implementation class ItemEditServlet
 */
@WebServlet("/ItemEditServlet")
@MultipartConfig
public class ItemEditServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public ItemEditServlet() {
		super();
		// TODO Auto-generated constructor stub
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		Action action = null;
		if (request.getParameter("q") != null)
			request.setAttribute("query", request.getParameter("q"));

		if (request.getParameter("id") != null) {
			action = new ItemUpdateAction();
		} else {
			action = new ItemUpdateAction();
			/*
			String cmd = request.getParameter("cmd");
			switch (cmd) {
			case "add":
				break;
			
			default:
				break;
			}*/
		}
		if (action != null) {
			String view = action.execute(request, response);
			RequestDispatcher rd = request.getRequestDispatcher(view);
			rd.forward(request, response);
		} else {
			// エラーハンドリング：一覧に戻すなど
			response.sendRedirect("/admin/modify_search.jsp");
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		Action action = null;
		if (request.getParameter("q") != null)
			request.setAttribute("query", request.getParameter("q"));

		if ("execute".equals("")) {
			// SQLの更新処理
		} else {
			String cmd = request.getParameter("cmd");
			switch (cmd) {
			case "confirm":
				action = new ItemUpdateConfirmAction();
				break;
			case "execute":
				action = new ItemUpdateExecuteAction();
				break;
			}
		}
		if (action != null) {
			String view = action.execute(request, response);
			RequestDispatcher rd = request.getRequestDispatcher(view);
			rd.forward(request, response);
		} else {
			// エラーハンドリング：一覧に戻すなど
			response.sendRedirect("/admin/modify_search.jsp");
		}
	}

}
