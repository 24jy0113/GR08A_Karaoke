package action;

import java.io.IOException;
import java.net.URLEncoder;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import dao.ItemDao;

/**
 * Servlet implementation class SearchItemByName
 */
@WebServlet("/SearchItemByName")
public class ItemNameSearchServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * Default constructor. 
	 */
	public ItemNameSearchServlet() {
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// セッションの取得（なければ新規作成、あれば既存ものを返す）.
		HttpSession session = request.getSession();

		// 不要なセッションを掃除してから一覧を表示
		session.removeAttribute("editItem");
		Object currentMode = session.getAttribute("mode");
		session.removeAttribute("admin");

		request.setCharacterEncoding("UTF-8");

		String rawAdminMode = request.getParameter("admin");
		boolean adminMode = false;

		if (rawAdminMode == null && currentMode != null) {
			adminMode = ((Boolean) currentMode).booleanValue();
		} else if (rawAdminMode != null && rawAdminMode.isEmpty()) {
			adminMode = true;
		}

		String undoPath = (adminMode ? "/admin/" : "/kitchen/kitchen_") + "modify_search.jsp";

		if (request.getParameter("go_top") != null) {
			response.sendRedirect(request.getContextPath() + undoPath);
			return;
		}

		if (adminMode) {
			session.setAttribute("admin", true);
		} else {
			session.setAttribute("admin", false);
		}

		String query = request.getParameter("q");

		if (query == null) {
			// フォーム入力がない時.
			query = (String) session.getAttribute("query");
		} else if (query.isEmpty()) {
			// フォーム入力が空の時.

			// フロントエンド用のメッセージ.
			String message = URLEncoder.encode("商品名を入力してください", "UTF-8");

			// 検索フォームに返却.
			response.sendRedirect(request.getContextPath() + undoPath + "?e=" + message);
			return;
		} else {
			// フォームで検索する時.
			session.setAttribute("query", query);
		}

		try {
			var dao = new ItemDao();
			var list = dao.searchItemByName(query);

			if (list.isEmpty()) {
				// フロントエンド用のメッセージ.
				String message = URLEncoder.encode("該当する商品がありませんでした", "UTF-8");

				// 検索フォームに返却.
				response.sendRedirect(request.getContextPath() + undoPath + "?e=" + message);
				return;
			}

			request.setAttribute("searchResult", list);
			request.setAttribute("query", query);
		} catch (Exception e) {
			// デバッグ用のスタックトレース.
			e.printStackTrace();

			// フロントエンド用のメッセージ.
			String message = URLEncoder.encode(e.getMessage(), "UTF-8");

			// 検索フォームに返却.
			response.sendRedirect(request.getContextPath() + undoPath + "?e=" + message);
			return;
		}
		RequestDispatcher rd = request.getRequestDispatcher("/admin/modify_list.jsp");
		rd.forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		doGet(request, response);
	}

}
