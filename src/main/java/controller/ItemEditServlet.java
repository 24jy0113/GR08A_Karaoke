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
 * Servlet implementation class ItemEditServlet
 */
@WebServlet("/ItemEditServlet")
public class ItemEditServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public ItemEditServlet() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		String action = request.getParameter("action");

		if ("edit".equals(action)) {
			int id = Integer.parseInt(request.getParameter("id"));

			try {
				var dao = new ItemDao();
				var item = dao.searchItemById(id);
				var category = dao.getCategoryList();
				var option = dao.searchOptionByCategoryId(item.getCategoryId());

				request.setAttribute("item", item);
				request.setAttribute("categoryList", category);
				request.setAttribute("optionList", option);

			} catch (Exception e) {
				// デバッグ用のスタックトレース.
				e.printStackTrace();

				// フロントエンド用のメッセージ.
				request.setAttribute("errMsg", e.getMessage());
			}
			RequestDispatcher rd = request.getRequestDispatcher("/admin/modify_update.jsp");
			rd.forward(request, response);
		} else if ("confirm".equals(action)) {
		} else if ("execute".equals(action)) {

		}
	}

}
