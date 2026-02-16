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
<title>伝票一覧-フロント</title>
<link rel="stylesheet" type="text/css"
	href="<%=request.getContextPath()%>/css/header.css">
<link rel="stylesheet" type="text/css"
	href="<%=request.getContextPath()%>/css/default.css">
<link rel="stylesheet" type="text/css"
	href="<%=request.getContextPath()%>/css/12_02.css">
</head>
<body>
	<%@ include file="/shered/biz_header.jsp"%>
	<main>
		<h1 class="bodytitle">調理済み一覧</h1>
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
				%>

				<%
				if (receivingNo == null || receivingNo == 0) {
				%>
				部屋までお届け
				<%
				} else {
				%>
				受取番号：<%=String.format("%04d", receivingNo)%>
				<%
				}
				%><br>

				<form action="<%=request.getContextPath()%>/FrontOrderFinished"
					method="post">
					<input type="hidden" name="orderId" value="<%=o.getId()%>">
					<button type="submit">完了</button>
				</form>
			</div>

			<%
			}
			%>
			<%
			} else {
			%>
			<p class="bodymsg">調理済みの注文はありません。</p>
			<%
			}
			%>
		</div>
		<div class="action-buttons flex-center">
			<button type="button" class="btn-back"
				onclick="location.href='<%=request.getContextPath()%>/front/front_top.jsp'">表示選択画面へ戻る</button>
			<button type="button" class="btn-next"
				onclick="location.href='<%=request.getContextPath()%>/FrontOrderFinishedList'">完了一覧</button>
		</div>
	</main>
</body>
</html>