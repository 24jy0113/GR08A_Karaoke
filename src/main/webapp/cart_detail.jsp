<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8" import="java.util.ArrayList,model.*"%>
<%
Room room = (Room) session.getAttribute("room");
Integer remainingMinutes = (Integer) session.getAttribute("remainingMinutes");
%>
<%
ArrayList<OrderItem> cart = (ArrayList<OrderItem>) session.getAttribute("cart");
if (cart == null || cart.isEmpty()) {
	cart = new ArrayList<>();
}
int totalSum = 0;
%>

<!DOCTYPE html>
<html lang="ja">
<head>
<meta charset="UTF-8">
<title>カート内容</title>
<link rel="stylesheet" type="text/css"
	href="<%=request.getContextPath()%>/css/header.css">
<link rel="stylesheet" type="text/css"
	href="<%=request.getContextPath()%>/css/default.css">
<link rel="stylesheet" type="text/css"
	href="<%=request.getContextPath()%>/css/07.css">

</head>
<body>
	<%@ include file="/shered/cus_header.jsp"%>
	<main class="container">
		<div class="text-center">
			<%
			if (cart.isEmpty()) {
			%>
			<h2 class="bodytitle">カート内容</h2>
			<p class="errormsg">カートの中身は空です。</p>
			<button type="button" class="btn-back"
				onclick="location.href='<%=request.getContextPath()%>/cus_top.jsp'">
				トップページへ戻る</button>
			<%
			} else {
			%>
			<h2 class="bodytitle">カート内容</h2>
			<p class="bodymsg">カートの内容を確認し、注文へ進む場合は「注文へ進む」を押してください</p>
			<form action="CartDetailServlet" method="post">
				<table>
					<%
					for (int i = 0; i < cart.size(); i++) {
						OrderItem oi = cart.get(i);
						totalSum += oi.getTotal();
					%>
					<tr>
						<th><%=oi.getItem().getName()%></th>
						<th>個数</th>
						<th>小計</th>
						<th rowspan="2"><a href="item_detail_change.jsp?index=<%=i%>">
								個数・オプションを変更する </a></th>
					</tr>
					<tr>
						<td><%=oi.getItem().getPrice()%>円(税込)</td>
						<td><%=oi.getCount()%></td>
						<td><%=oi.getTotal()%>円(税込)</td>
					</tr>
					<tr>
						<td><a href="CartRemoveServlet?index=<%=i%>">削除する</a></td>
					</tr>
					<%
					}
					%>
				</table>
				<h3>
					商品合計金額：<%=totalSum%>円(税込)
				</h3>
				<div class="action-buttons flex-center">
					<button type="button" class="btn-back" onclick="location.href='<%=request.getContextPath()%>/cus_top.jsp'">トップページへ戻る</button>
					<button type="submit" class="btn-next">注文へ進む</button>
				</div>
			</form>
			<%
			}
			%>
		</div>
	</main>
	<%@ include file="/shered/cus_footer.jsp"%>
</body>
</html>