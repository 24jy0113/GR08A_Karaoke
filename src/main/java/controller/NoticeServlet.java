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

	    // 既存Sessionのみ取得
	    HttpSession session = request.getSession(false);
	    
	    // Sessionチェック
	    if (session == null || session.getAttribute("room") == null) {
	        response.setContentType("application/json;charset=UTF-8");
	        response.getWriter().write("{\"sessionExpired\":true}");// セッション切れ情報(true)をJSON形式で文字列として送る
	        return;
	    }

	    // Room情報取得
	    Room room = (Room) session.getAttribute("room");

	    // Action呼び出し
	    NoticeAction action = new NoticeAction();
	    NoticeResult result = action.execute(room, session);

	    // JSONで返却
	    response.setContentType("application/json;charset=UTF-8");
	    response.getWriter().write(result.toJson());
	}

    
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
