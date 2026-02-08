package controller;

import java.io.IOException;
import java.util.List;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import dao.OrderDao;
import model.Order;
import model.OrderItem;
import model.Room;


@WebServlet("/cusPurchaseHistory")

public class CusPurchaseHistory extends HttpServlet{
	protected void doGet(HttpServletRequest req, HttpServletResponse res)
	        throws ServletException, IOException {

	    HttpSession session = req.getSession();
	    Room room = (Room) session.getAttribute("room");

	    if (room == null) {
	        res.sendRedirect("cus_top.jsp");
	        return;
	    }

	    try {
	        OrderDao dao = new OrderDao();
	        int roomId = room.getId();
	        List<Order> orderList = dao.findActiveOrdersByRoom(roomId);
	        int totalSum = dao.getActiveOrderTotalByRoom(roomId);
	        
	        for (Order o : orderList) {

	            List<OrderItem> itemList =
	                dao.findOrderItemsByOrderId(o.getId());

	            for (OrderItem oi : itemList) {
	            	List<OrderItem.SelectedOptionDetail> optionDetails =
	            		    dao.findOptionsByOrderDetailId(oi.getId());
	            	oi.setSelectedOptionDetails(optionDetails);
	            }
	            o.setItemList(itemList);
	            
	        }


	        req.setAttribute("orderList", orderList);
	        req.setAttribute("totalSum", totalSum);
	        req.getRequestDispatcher("/cus_purchase_history.jsp")
	               .forward(req, res);

	    } catch (Exception e) {
	        throw new ServletException(e);
	    }
	}


}
