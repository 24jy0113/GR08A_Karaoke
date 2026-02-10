package controller;
import java.io.IOException;
import dao.OrderDao;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/FrontOrderFinished")
public class FrontOrderFinished extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse res)
            throws ServletException, IOException {

        try {
            int orderId =
                Integer.parseInt(req.getParameter("orderId"));

            OrderDao dao = new OrderDao();

            // 調理済み(2) → 完了(3)
            dao.updateStatus(orderId, 3);

            res.sendRedirect(
                req.getContextPath() + "/FrontOrderReady"
            );

        } catch (Exception e) {
            throw new ServletException(e);
        }
    }
}

