<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" import="java.util.ArrayList,model.*"%>
<%
Room room = (Room) session.getAttribute("room");
Integer remainingMinutes = (Integer) session.getAttribute("remainingMinutes");
%>
<%
Order order = (Order) session.getAttribute("order");
if (order == null) {
    response.sendRedirect(request.getContextPath() + "/cus_top.jsp");
    return;
}
%>

<!DOCTYPE html>
<html lang="ja">
<head>
    <meta charset="UTF-8">
    <title>カート内容確認画面</title>
    <link rel="stylesheet" type="text/css" href="<%= request.getContextPath() %>/css/07_02.css">
    <link rel="stylesheet" type="text/css" href="<%= request.getContextPath() %>/css/header.css">
</head>
<body>
    <!-- Header -->
    <%@ include file="/shered/cus_header.jsp" %>
    <main class="bodymsg">
		<div class="msg">
			<h2>ご注文の確認</h2>
			<p>受け取り方法を選択し、注文内容に問題なければ「注文を確定する」を押してください</p>
		</div>
             
		<form method="post" action="CartDetailConfirmServlet">
			<h3>受取方法</h3>
			<p>ご自身で受け取りに行くか、お部屋までお届けするか選択してください</p>
			<label>
				<input type="radio" name="pickupMethod" value="カウンター受取" checked>
				受取に行く
			</label>
			<label>
				<input type="radio" name="pickupMethod" value="お部屋までお届け">
				部屋まで届ける
			</label>
			<h3>注文内容</h3>
			<div class="cart-contents">
				<table>
					<% for (OrderItem oi : order.getItemList()) {%>
			
						<tr>
							  <th><%= oi.getItem().getName() %></th>
							  <th>個数</th>
							  <th>小計</th>
						  
						</tr>
						<tr>
							  <td><%= oi.getItem().getPrice() %>円(税込)</td>
							  <td><%= oi.getCount() %></td>
							  <td><%= oi.getTotal() %>円(税込)</td>
						</tr>
					<% } %>
				</table>
		 	</div>
			<p class="order-summary">商品合計金額：<%= order.calculateTotal() %>円(税込)</p>
				
	         <div class="action-buttons">
	             <button type="button" class="btn-back" onclick="history.back()">カート内容へ戻る</button>
	             <button type="submit" class="btn-next">注文を確定する</button>
	         </div>
		</form>
    </main>
    <%@ include file="/shered/cus_footer.jsp" %>
    
</body>
</html>