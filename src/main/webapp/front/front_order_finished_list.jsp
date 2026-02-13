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
    <title>完了した注文一覧-フロント</title>
    <link rel="stylesheet" type="text/css" href="<%= request.getContextPath() %>/css/12_04.css">
    <link rel="stylesheet" type="text/css" href="<%= request.getContextPath() %>/css/header.css">
</head>
<body>
    <%@ include file="/shered/biz_header.jsp" %>
    <h1>完了した注文一覧</h1>
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
	
	        <%
			Integer receivingNo = o.getReceivingNo();
			%>
			
			<% if (receivingNo == null || receivingNo == 0) { %>
			    部屋までお届け
			<% } else { %>
			    受取番号：<%= String.format("%04d", receivingNo) %>
			<% } %><br>

	        
	    </div>
	
	    <% } %>
	<% } else { %>
	    <p>完了した注文はありません。</p>
	<% } %>
    </div>
    <div class="action-buttons">
	     <button type="button" class="btn-back" onclick="location.href='<%= request.getContextPath() %>/FrontOrderReady'">調理済み一覧に戻る</button>
	</div>
</body>
</html>