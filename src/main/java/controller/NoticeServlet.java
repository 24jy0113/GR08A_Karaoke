package controller;

import java.io.IOException;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import action.NoticeAction;
import model.NoticeResult;
import model.Room;

@WebServlet("/NoticeServlet")
public class NoticeServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
    public NoticeServlet() {
        super();
    }
    
    @Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

	    // 1. Session取得（なければ終了）
	    HttpSession session = request.getSession(false);
	    if (session == null || session.getAttribute("room") == null) {
	        response.setContentType("application/json;charset=UTF-8");
	        response.getWriter().write("{\"sessionExpired\":true}");
	        return;
	    }

	    // 2. Room情報取得
	    Room room = (Room) session.getAttribute("room");

	    // 3. Action呼び出し
	    NoticeAction action = new NoticeAction();
	    NoticeResult result = action.execute(room, session);

	    // 4. JSONで返却
	    response.setContentType("application/json;charset=UTF-8");
	    response.getWriter().write(result.toJson());
	}

    
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
