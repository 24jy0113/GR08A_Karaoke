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
    <header>
        <div class="header_inner">
            <img class="title_img" src="<%= request.getContextPath() %>/img/logo.png" alt="logo" width="60" height="60">
            <h1 class="title_name">七福サウンド</h1>
            <nav class="gnav">
                <ul class="gnav_list">
                    <li><a href="<%= request.getContextPath() %>/cus_top.jsp">トップへ</a></li>
                    <li><a href="<%= request.getContextPath() %>/time_extend.jsp">延長申請</a></li>
                    <li><a href="<%= request.getContextPath() %>/item_search.jsp">メニューを番号で探す</a></li>
                    <li><a href="<%= request.getContextPath() %>/item_list.jsp">フード・ドリンク</a></li>
                    <li><a href="<%= request.getContextPath() %>/cus_purchase_history.jsp">注文履歴</a></li>
                    <li><a class="gnav_botton" href="<%= request.getContextPath() %>/cart_detail.jsp">
                            <img class="cart_img" src="<%= request.getContextPath() %>/img/cart.png" alt="cart" width="20" height="20">カート内容を確認
                        </a>
                    </li>
                </ul>
            </nav>
        </div>
    </header>
    <main>
        <div class="bodymsg">
            <div>
                <div class="msg">
                    <h2>ご注文の確認</h2>
                    <p>受け取り方法を選択し、注文内容に問題なければ「注文を確定する」を押してください</p>
                </div>
                
                <form method="post" action="CartDetailConfirmServlet">
                <h3>受取方法</h3>
                <p>ご自身で受け取りに行くか、お部屋までお届けするか選択してください</p>
                    <label>
					  <input type="radio" name="pickupMethod" value="SELF" checked>
					  受取に行く
					</label>
					<label>
					  <input type="radio" name="pickupMethod" value="ROOM">
					  部屋まで届ける
					</label>
                <h3>注文内容</h3>
                <div class="cart-contents">
                   <table>
				<% for (OrderItem oi : order.getItemList()) {
				%>
				
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
				
				<p>商品合計金額：<%= order.calculateTotal() %>円(税込)</p>
				
                <div class="action-buttons">
                    <button type="button" class="btn-back" onclick="history.back()">カート内容へ戻る</button>
                    <button type="submit" class="btn-next">注文を確定する</button>
                </div>
                </form>
            </div>
        </div>

        <div class="footer-wrap">
            <% if (room != null) { %>
  				<h1>部屋番号　<%= room.getRoomNo() %></h1>
      　	　　<% } %>
      	　　<% if (remainingMinutes != null) { %>
        	 	<h1>残り時間　<%= remainingMinutes %>　分</h1>
         　<% } %>
        </div>
    </main>
    
</body>
</html>