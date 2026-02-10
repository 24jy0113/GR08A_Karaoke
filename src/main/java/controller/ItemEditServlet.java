package controller;

import java.io.IOException;
import java.net.URLEncoder;

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
		processRequest(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		processRequest(request, response);
	}

	protected void processRequest(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		Action action = null;

		if ("GET".equalsIgnoreCase(request.getMethod())) {
			action = new ItemUpdateAction();
		} else {
			String cmd = request.getParameter("cmd");
			switch (cmd) {
			case "edit":
				action = new ItemUpdateAction();
				break;
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
			RequestDispatcher rd = request.getRequestDispatcher("/admin/" + view);
			rd.forward(request, response);
		} else {
			// エラーハンドリング：一覧に戻すなど
			response.sendRedirect("/SearchItemByName");
		}

		// errMsgがある時.
		String errMsg = ((String) (request.getAttribute("errMsg")));
		if (errMsg != null && !errMsg.isEmpty()) {
			// フロントエンド用のメッセージ.
			String message = URLEncoder.encode(errMsg, "UTF-8");

			// 検索フォームに返却.
			response.sendRedirect(request.getContextPath() + "/admin/modify_search.jsp?e=" + message);
			return;
		}
	}

}
