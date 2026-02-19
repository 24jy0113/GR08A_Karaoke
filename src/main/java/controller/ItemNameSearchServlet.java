package controller;

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

		// 入力がない場合管理者フラグを使いまわすので一時保存.
		Object currentMode = session.getAttribute("isAdmin");
		
		// 不要なセッションを掃除してから一覧を表示
		session.removeAttribute("editItem");
		session.removeAttribute("isAdmin");

		request.setCharacterEncoding("UTF-8");

		// 管理者か確認.
		String rawAdminMode = request.getParameter("isAdmin");
		// 管理者フラグを無効で初期化.
		boolean adminMode = false;

		// 管理者のフラグを取得.
		if (rawAdminMode == null && currentMode != null) {
			// パラメータがない場合、セッションから一時保存してたフラグを取得.
			adminMode = ((Boolean) currentMode).booleanValue();
		} else if (rawAdminMode != null && rawAdminMode.equals("true")) {
			// パラメータに管理者フラグが有効なら有効.
			adminMode = true;
		}

		// 検索画面へ遷移する先を設定.
		String undoPath = (adminMode ? "/admin/" : "/kitchen/kitchen_") + "modify_search.jsp";

		// 検索画面に遷移.
		if (request.getParameter("go_top") != null) {
			// 検索条件をセッションから削除.
			session.removeAttribute("query");

			response.sendRedirect(request.getContextPath() + undoPath);
			return;
		}

		// セッションに管理者のフラグを保存.
		if (adminMode) {
			session.setAttribute("isAdmin", true);
		} else {
			session.setAttribute("isAdmin", false);
		}

		// 商品追加時は商品追加モードでフォーム遷移.
		String insertItem = request.getParameter("insertItem");
		if (insertItem != null && insertItem.equals("true")) {
			response.sendRedirect(request.getContextPath() + "/ItemEditServlet");
			return;
		}

		// フォームからパラメータを取得.
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

				// 検索条件をセッションから削除.
				session.removeAttribute("query");

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

			// 検索条件をセッションから削除.
			session.removeAttribute("query");

			// 検索フォームに返却.
			response.sendRedirect(request.getContextPath() + undoPath + "?e=" + message);
			return;
		}
		// 検索結果画面へ遷移.
		RequestDispatcher rd = request.getRequestDispatcher("/admin/modify_list.jsp");
		rd.forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		doGet(request, response);
	}

}
