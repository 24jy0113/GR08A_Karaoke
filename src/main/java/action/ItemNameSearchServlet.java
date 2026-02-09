package action;

import java.io.IOException;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

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
		// 不要なセッションを掃除してから一覧を表示
		request.getSession().removeAttribute("editItem");

		request.setCharacterEncoding("UTF-8");
		String query = request.getParameter("q");

		try {
			var dao = new ItemDao();
			var list = dao.searchItemByName(query);

			request.setAttribute("searchResult", list);
			request.setAttribute("query", query);
		} catch (Exception e) {
			// デバッグ用のスタックトレース.
			e.printStackTrace();

			// フロントエンド用のメッセージ.
			request.setAttribute("errMsg", e.getMessage());
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
