<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"
    import="model.*,java.util.*"
%>
<%
User user = (User) session.getAttribute("loginUser");
if (user == null) {
    response.sendRedirect(request.getContextPath() + "/index.jsp");
    return;
}
%>
<%
List<Order> orderList =
    (List<Order>) request.getAttribute("orderList");
%>

<!DOCTYPE html>
<html lang="ja">
<head>
    <meta charset="UTF-8">
    <title>伝票一覧-フロント</title>
    <link rel="stylesheet" type="text/css" href="<%= request.getContextPath() %>/css/12_03.css">
    <link rel="stylesheet" type="text/css" href="<%= request.getContextPath() %>/css/header.css">
</head>
<body>
    <header>
        <div class="header_inner">
            <img class="title_img" src="<%= request.getContextPath() %>/img/logo.png" alt="logo" width="60" height="60">
            <h1 class="title_name">七福サウンド</h1>
            <nav class="gnav">
                <ul class="gnav_list">
                     <li><img class="user_img" src="<%= request.getContextPath() %>/img/user.png" alt="cart" width="25" height="25"><%= user.getUserName() %></li>
                    <li><a class="gnav_button" href="<%= request.getContextPath() %>/LogoutServlet">ログアウト</a></li>
                </ul>
            </nav>
        </div>
    </header>
    <h1>調理済み一覧</h1>
    <div class="card-container">
        <% if (orderList != null && !orderList.isEmpty()) { %>
	    <% for (Order o : orderList) { %>
    <div class="card">
	        <strong><%= o.getRoomNo() %>室</strong><br>
	
	        <% for (OrderItem oi : o.getItemList()) { %>

			    <%= oi.getItemName() %>

			    <% if (oi.getSelectedOptionDetails() != null
			          && !oi.getSelectedOptionDetails().isEmpty()) { %>
			        （
			        <% for (int i = 0; i < oi.getSelectedOptionDetails().size(); i++) {
			               OrderItem.SelectedOptionDetail d =
			                   oi.getSelectedOptionDetails().get(i);
			        %>
			            <%= d.selectionName() %>
			            <% if (i < oi.getSelectedOptionDetails().size() - 1) { %> / <% } %>
			        <% } %>
			        ）
			    <% } %>
			
			    × <%= oi.getCount() %><br>
			
			<% } %>
			<br>
	
	        受取番号：<%= o.getReceivingNo() %><br>

	        <form action="<%= request.getContextPath() %>/FrontOrderFinished"
	              method="post">
	            <input type="hidden" name="orderId"
	                   value="<%= o.getId() %>">
	            <button type="submit">完了</button>
	        </form>
	    </div>
	
	    <% } %>
	<% } else { %>
	    <p>調理済みの注文はありません。</p>
	<% } %>
    </div>
    <div class="footer-buttons">
        
        <button type="button" onclick="location.href='<%= request.getContextPath() %>/index_select.jsp'">担当選択画面へ戻る</button>

    </div>
</body>
</html>