package controller;

import java.io.IOException;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import action.Action;
import action.ItemUpdateAction;

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
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		Action action = null;

		if (request.getParameter("edit") != null) {
			action = new ItemUpdateAction();
		} else if (request.getParameter("confirm") != null) {
			
		} /*else if ("execute".equals(action)) {
			postに書く
			}*/

		String view = action.execute(request, response);
		RequestDispatcher rd = request.getRequestDispatcher(view);
		rd.forward(request, response);
	}
	
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO 自動生成されたメソッド・スタブ
		super.doPost(request, response);
	}
	

}
