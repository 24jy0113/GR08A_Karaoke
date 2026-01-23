package controller;

import java.io.IOException;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import action.ExtendAction;

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

        try {
            // 業務処理は Action に丸投げ
            new ExtendAction().execute(extendMinutes, session);

            // 延長完了画面に行く
            response.sendRedirect("time_extend_confirmed.jsp");

        } catch (Exception e) {
            e.printStackTrace();
            /* エラー画面はとりあえず保留
             * response.sendRedirect("error.jsp");
            */
        }
    }
}
