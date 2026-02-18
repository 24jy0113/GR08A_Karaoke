package controller;

import java.io.IOException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import jakarta.servlet.AsyncContext;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class SseNotificationServlet
 */
@WebServlet(urlPatterns ="/SseNotificationServlet", asyncSupported = true)
public class SseNotificationServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	public static final Map<String, AsyncContext> roomConnections =new ConcurrentHashMap<>();
       
   

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// 部屋idを取得する.
		String roomId=request.getParameter("roomId");
		if(roomId==null||roomId.isEmpty())return;
	}

}
