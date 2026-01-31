<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" import="model.Item,model.Room, model.Option"%>
<%
Item item = (Item) request.getAttribute("item");
%>
<%
Room room = (Room) session.getAttribute("room");
Integer remainingMinutes = (Integer) session.getAttribute("remainingMinutes");
%>
<!DOCTYPE html>
<html lang=ja>
<head>
    <meta charset="UTF-8">
    <title>オプション選択-変更時</title>
    <link rel="stylesheet" type="text/css" href="<%= request.getContextPath() %>/css/06_04.css">
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
                
                <h2>ドリンクのサイズをお選びください</h2>
            </div>
            <div class="container">
                <!-- 左側：画像領域 -->
                <div>
                    <div class="left-box">画像</div>
                    <h1>生ビール</h1>
                    <h2>600円(税込)</h2>
                </div>
                <!-- 右側 -->
                <div class="right-box">  
                    <div class="pad">
                        <h3>Sサイズ</h3>
                        <h3>Mサイズ+¥30</h3>
                        <h3>Lサイズ+¥50</h3> 
                    </div>
                </div>
            </div>
            
        </div>
        <div class="action-buttons">
            <button type="button" class="btn-back" onclick="location.href='item_detail_change.jsp'">戻る</button>
            <button type="submit" class="btn-next" onclick="location.href='item_num_change.jsp'">次へ</button>
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