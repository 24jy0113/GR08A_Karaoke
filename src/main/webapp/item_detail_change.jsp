<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" import="java.util.*,model.*"%>
<%
int index = Integer.parseInt(request.getParameter("index"));
ArrayList<OrderItem> cart =
	(ArrayList<OrderItem>) session.getAttribute("cart");
OrderItem oi = cart.get(index);
Item item = oi.getItem();
%>
<%
Room room = (Room) session.getAttribute("room");
Integer remainingMinutes = (Integer) session.getAttribute("remainingMinutes");
%>
<!DOCTYPE html>
<html lang=ja>
<head>
    <meta charset="UTF-8">
    <title>商品内容変更</title>
    <link rel="stylesheet" type="text/css" href="<%= request.getContextPath() %>/css/06_03.css">
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
                    <li><a class="gnav_botton" href="cart_detail.jsp">
                            <img class="cart_img" src="<%= request.getContextPath() %>/img/cart.png" alt="cart" width="20" height="20">カート内容を確認
                        </a>
                    </li>
                </ul>
            </nav>
        </div>
    </header>
    <main>
        <div class="bodymsg">
            <div class="msg">
                <h1><%= item.getName() %></h1>
               <h2><%= item.getPrice() %>　円(税込)</h2>
            </div>
            <div class="container">
                <!-- 左側：画像領域 -->
                <div>
                    <div class="left-box">画像</div>
                   
                </div>
                <!-- 右側：検索欄＋テンキー -->
                <div class="right-box">  
                    <div class="pad">
                    <form action="CartItemUpdateServlet" method="post">
						<input type="hidden" name="index" value="<%= index %>">
					
						<h3><%= item.getName() %></h3>
					
						<% for (Option opt : item.getOptionList()) {
							OrderItem.SelectedOption so =
								oi.findSelectedOptionById(opt.getId());
						%>
						<p><strong><%= opt.getName() %></strong></p>
					
						<% for (Option.Selection sel : opt.getSelectionList()) { %>
						<label>
							<input type="radio"
							       name="opt_<%= opt.getId() %>"
							       value="<%= sel.id() %>"
							       <%= (so != null && so.selectionId() == sel.id()) ? "checked" : "" %>
							       required>
							<%= sel.name() %>（+<%= sel.price() %>円）
						</label><br>
						<% } %>
					
						<% } %>
					
						<h3>数量</h3>
						<input type="number" name="count" min="1"
						       value="<%= oi.getCount() %>" required>
					
						<br><br>
                        <button style="color: white; background-color: black;" type="submit">変更する</button>
                        <button onclick="history.back()">カートに戻る</button>
                      </form>  
                    </div>
                </div>
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