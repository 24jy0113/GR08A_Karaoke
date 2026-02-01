<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" import="model.Item,model.Room, model.Option,model.OrderItem"%>
<%@ page import="java.util.ArrayList"%>

<%
Room room = (Room) session.getAttribute("room");
Integer remainingMinutes = (Integer) session.getAttribute("remainingMinutes");
%>
<%
OrderItem oi = (OrderItem) session.getAttribute("buildingItem");
ArrayList<OrderItem> cart = (ArrayList<OrderItem>) session.getAttribute("cart");
%>
<!DOCTYPE html>
<html lang=ja>
<head>
    <meta charset="UTF-8">
    <title>カート追加確認画面</title>
    <link rel="stylesheet" type="text/css" href="<%= request.getContextPath() %>/css/06_06.css">
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
            
            <div class="container">
                <!-- 左側：画像領域 -->
                <div>
                    <div class="left-box"><img class="item-img" src="<%=request.getContextPath()%>/img/<%=oi.getItem().getImage()%>" alt="product"></div>
                    <br>
                    <h1><%= oi.getItem().getName() %>　　<%= oi.getItem().getPrice() %>　円(税込)</h1>

                </div>
                <!-- 右側 -->
                <div class="right-box">
                    <ul>
					<% for (OrderItem.SelectedOptionDetail d : oi.getSelectedOptionDetailList()) { %>
					  <li><%=oi.getCount()%></li>
					  <li><%= d.optName() %>：<%= d.selectionName() %></li>
					  
					<% } %>
					</ul>

					<p>小計：<%= oi.getTotal() %>円</p>
                    
                </div>
            </div>
        </div>
        <form action="<%= request.getContextPath() %>/CartConfirmServlet" method="post">
		  <div class="action-buttons">
		    <button type="button" class="btn-back" onclick="history.back()">戻る</button>
		    <button type="submit" class="btn-next">カートに入れる</button>
		  </div>
		</form>

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