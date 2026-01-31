<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" import="model.Room"%>
<%
Room room = (Room) session.getAttribute("room");
Integer remainingMinutes = (Integer) session.getAttribute("remainingMinutes");
%>
<!DOCTYPE html>
<html lang="ja">
<head>
    <meta charset="UTF-8">
    <title>延長内容のご確認</title>
    <link rel="stylesheet" type="text/css" href="<%= request.getContextPath() %>/css/04_02.css">
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
                <h2>下記の延長内容を確認してください</h2>
            
                <table>
                    <tr>
                        <th>部屋番号</th>
                        <th>受付時間</th>
                        <th>退室時間</th>
                        <th>合計延長時間（分）</th>
                    </tr>
                    <!-- 1行目 -->
                    <tr>
                        <td>101</td>
                        <td>10:30 </td>
                        <td>11:30</td>
                        <td>30</td>                
                    </tr>
                </table>
                <div class="action-buttons">
                    <button type="button" class="btn-back" onclick="history.back()">戻る</button>
                    <button type="submit" class="btn-next" onclick="location.href='time_extend_confirmed.jsp'">延長申請を完了する</button>
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