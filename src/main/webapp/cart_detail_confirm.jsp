<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8" import="java.util.ArrayList,model.*"%>
<%
Room room = (Room) session.getAttribute("room");
%>
<%
Order order = (Order) session.getAttribute("order");
if (order == null) {
	response.sendRedirect(request.getContextPath() + "/cus_top.jsp");
	return;
}
%>

<!DOCTYPE html>
<html lang="ja">
<head>
<meta charset="UTF-8">
<title>カート内容確認画面</title>
<link rel="stylesheet" type="text/css"
	href="<%=request.getContextPath()%>/css/header.css">
<link rel="stylesheet" type="text/css"
	href="<%=request.getContextPath()%>/css/default.css">
<link rel="stylesheet" type="text/css"
	href="<%=request.getContextPath()%>/css/07.css">
</head>
<body>
	<%@ include file="/shered/cus_header.jsp"%>
	<main class="text-center">
		<h1 class="bodytitle">ご注文の確認</h1>
		<p class="bodymsg">受け取り方法を選択し、注文内容に問題なければ「注文を確定する」を押してください</p>
		<div class="container text-left">
			<form method="post" action="CartDetailConfirmServlet">
				<hr>
				<h3 class="bodytitle">受取方法</h3>
				<p>ご自身で受け取りに行くか、お部屋までお届けするか選択してください</p>
				<label> <input type="radio" name="pickupMethod"
					value="カウンター受取" checked> 受け取りに行く
				</label> <label> <input type="radio" name="pickupMethod"
					value="お部屋までお届け"> 部屋で受け取り
				</label>
				<hr>
				<h3 class="bodytitle">注文内容</h3>
				<div class="cart-contents">
					<table>
						<%
						for (OrderItem oi : order.getItemList()) {
						%>

						<tr>
							<th><%=oi.getItem().getName()%></th>
							<th>オプション</th>
							<th>個数</th>
							<th>小計</th>

						</tr>
						<tr>
							<td><%=oi.getItem().getPrice()%>円(税込)</td>
							<td>
							<%
							if (oi.getSelectedOptions() != null && !oi.getSelectedOptions().isEmpty()) {
								for (OrderItem.SelectedOption so : oi.getSelectedOptions()) {
									Option opt = oi.getItem().findOptionById(so.optId());
									if (opt != null) {
										Option.Selection sel = opt.findSelectionById(so.selectionId());
										if (sel != null) {
							%>
							<%=opt.getName()%>：<%=sel.name()%>（<%=sel.price()%>円）<br>
							<%
										}
									}
								}
							} else {
							%>
							なし
							<%
							}
							%>
							</td>
							<td><%=oi.getCount()%></td>
							<td><%=oi.getTotal()%>円(税込)</td>
						</tr>
						<%
						}
						%>
					</table>
				</div>
				<p class="order-summary">
					商品合計金額：<%=order.calculateTotal()%>円(税込)
				</p>
		</div>
		<div class="action-buttons flex-center">
			<button type="button" class="btn-back"
				onclick="location.href='<%=request.getContextPath()%>/cart_detail.jsp'">カート内容へ戻る</button>
			<button type="submit" class="btn-next">注文を確定する</button>
		</div>
		</form>
	</main>
	<%@ include file="/shered/cus_footer.jsp"%>

</body>
</html>