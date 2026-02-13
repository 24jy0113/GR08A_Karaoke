<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"
	import="model.Item,model.Room, model.Option,model.OrderItem"%>
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
<link rel="stylesheet" type="text/css"
	href="<%=request.getContextPath()%>/css/header.css">
<link rel="stylesheet" type="text/css"
	href="<%=request.getContextPath()%>/css/default.css">
<link rel="stylesheet" type="text/css"
	href="<%=request.getContextPath()%>/css/06_06.css">

</head>
<body>
	<%@ include file="/shered/cus_header.jsp" %>
	<main>
		<div class="text-center">
			<h1 class="bodytitle">注文を確認してください</h1>
			<div class="container">
				<!-- 左側：画像領域 -->
				<div>
					<div class="left-box">
						<img class="item-img"
							src="<%=request.getContextPath()%>/img/<%=oi.getItem().getImage()%>"
							alt="product">
					</div>
					<h2 class="bodytitle"><%=oi.getItem().getName()%></h2>
					<h2 class="bodytitle"><%=oi.getItem().getPrice()%>円(税込)
					</h2>
				</div>
				<!-- 右側 -->
				<div class="right-box">
					<ul>
						<%
						for (OrderItem.SelectedOptionDetail d : oi.getSelectedOptionDetailList()) {
						%>
						<li class="bodymsg">個数 <%=oi.getCount()%></li>
						<li class="bodymsg">オプション <%=d.optName()%>：<%=d.selectionName()%></li>
						<%
						}
						%>
					</ul>
					<h3 class="bodytitle">小計：<%=oi.getTotal()%>円</h3>
				</div>
			</div>
		</div>
		<form action="<%=request.getContextPath()%>/CartConfirmServlet"
			method="post">
			<div class="action-buttons flex-center">
				<button type="button" class="btn-back" onclick="history.back()">戻る</button>
				<button type="submit" class="btn-next">カートに入れる</button>
			</div>
		</form>
	</main>
	<%@ include file="/shered/cus_footer.jsp" %>

</body>
</html>