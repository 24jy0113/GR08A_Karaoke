package controller;

import java.io.IOException;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebServlet("/ExtendServlet")
public class ExtendServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    @Override
    protected void doPost(HttpServletRequest request,
                          HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("room") == null) {
            response.sendRedirect("room_search.jsp");
            return;
        }
        
        // 延長分（分）
        int extendMinutes =
                Integer.parseInt(request.getParameter("extendMinutes"));
        
        // セッションに一時保存
        session.setAttribute("extendMinutes", extendMinutes);
        
        // 確認画面へ
        request.getRequestDispatcher("/time_extend_confirm.jsp").forward(request, response);
        
    }
}
