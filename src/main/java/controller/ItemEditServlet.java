package controller;

import java.io.IOException;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import com.fasterxml.jackson.databind.ObjectMapper;

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
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");

		if (request.getParameter("edit") != null) {
			int id = Integer.parseInt(request.getParameter("edit"));
			var mapper = new ObjectMapper();

			try {
				var dao = new ItemDao();
				var item = dao.searchItemById(id);
				var category = dao.getCategoryList();
				var option = dao.getAllOptionsGroupedByCategory();
				String optionJson = mapper.writeValueAsString(option);

				request.setAttribute("item", item);
				request.setAttribute("categoryList", category);
				request.setAttribute("optionList", optionJson);

			} catch (Exception e) {
				// デバッグ用のスタックトレース.
				e.printStackTrace();

				// フロントエンド用のメッセージ.
				request.setAttribute("errMsg", e.getMessage());
			}
			RequestDispatcher rd = request.getRequestDispatcher("/admin/modify_update.jsp");
			rd.forward(request, response);
		} else if (request.getParameter("confirm") != null) {

		} /*else if ("execute".equals(action)) {
			
			}*/
	}

}
