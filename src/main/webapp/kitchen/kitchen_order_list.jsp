<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8" import="model.*,java.util.*"%>

<%
User user = (User) session.getAttribute("loginUser");
if (user == null) {
	response.sendRedirect(request.getContextPath() + "/index.jsp");
	return;
}
%>
<%
List<Order> orderList = (List<Order>) request.getAttribute("orderList");
%>
<!DOCTYPE html>
<html lang="ja">
<head>
<meta charset="UTF-8">
<title>注文情報確認ーキッチン</title>

<link rel="stylesheet" type="text/css"
	href="<%=request.getContextPath()%>/css/header.css">
<link rel="stylesheet" type="text/css"
	href="<%=request.getContextPath()%>/css/default.css">
<link rel="stylesheet" type="text/css"
	href="<%=request.getContextPath()%>/css/12_01.css">
</head>
<body>
	<%@ include file="/shared/biz_header.jsp"%>
	<main>
		<h1 class="bodytitle">注文一覧</h1>
		<div class="card-container">
			<%
			if (orderList != null && !orderList.isEmpty()) {
			%>
			<%
			for (Order o : orderList) {
			%>

			<div class="card">
				<strong><%=o.getRoomNo()%>室</strong><br>
				<%
				for (OrderItem oi : o.getItemList()) {
				%>
				<%=oi.getItemName()%>
				<%
				if (oi.getSelectedOptionDetails() != null
						&& !oi.getSelectedOptionDetails().isEmpty()) {
				%>
				（
				<%
				for (int i = 0; i < oi.getSelectedOptionDetails().size(); i++) {
					OrderItem.SelectedOptionDetail d = oi.getSelectedOptionDetails().get(i);
				%>
				<%=d.selectionName()%>
				<%
				if (i < oi.getSelectedOptionDetails().size() - 1) {
				%>
				/
				<%
				}
				%>
				<%
				}
				%>
				）
				<%
				}
				%>
				×
				<%=oi.getCount()%><br>
				<%
				}
				%>
				<br>
				<%
				Integer receivingNo = o.getReceivingNo();
				if (receivingNo == null || receivingNo == 0) {
				%>
				部屋までお届け
				<%
				} else {
				%>
				受取番号：<%=String.format("%04d", receivingNo)%>
				<%
				}
				%>
				<form action="<%=request.getContextPath()%>/KitchenOrderDone"
					method="post">
					<input type="hidden" name="orderId" value="<%=o.getId()%>">
					<button type="submit"
						onclick="this.disabled=true; this.form.submit();">調理済み</button>
				</form>
			</div>

			<%
			}
			%>
			<%
			} else {
			%>
			<p class="bodymsg">現在、注文はありません。</p>
			<%
			}
			%>

		</div>

		<div class="action-buttons flex-center">
			<button type="button" class="btn-next"
				onclick="location.href='<%=request.getContextPath()%>/kitchen/kitchen_modify_search.jsp'">在庫状況の更新</button>
			<button type="button" class="btn-next"
				onclick="location.href='<%=request.getContextPath()%>/KitchenOrderFinished'">調理済み一覧</button>
		</div>
		<button type="button" class="btn-back"
			onclick="location.href='<%=request.getContextPath()%>/index_select.jsp'">担当選択画面へ戻る</button>
	</main>
</body>
</html>