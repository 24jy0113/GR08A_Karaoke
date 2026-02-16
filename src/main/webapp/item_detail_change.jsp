<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8" import="java.util.*,model.*"%>
<%
int index = Integer.parseInt(request.getParameter("index"));
ArrayList<OrderItem> cart = (ArrayList<OrderItem>) session.getAttribute("cart");
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
<link rel="stylesheet" type="text/css"
	href="<%=request.getContextPath()%>/css/header.css">
<link rel="stylesheet" type="text/css"
	href="<%=request.getContextPath()%>/css/default.css">
<link rel="stylesheet" type="text/css"
	href="<%=request.getContextPath()%>/css/06_04.css">
</head>
<body>
	<%@ include file="/shered/cus_header.jsp"%>
	<main class="text-center">
		<h2 class="bodytitle">個数・オプションを変更できます！</h2>
		<div class="container">
			<!-- 左側：画像領域 -->
			<div>
				<div class="left-box">
					<img class="item-img"
						src="<%=request.getContextPath()%>/img/<%=item.getImage()%>"
						alt="product">
				</div>
				<h2 class="bodytitle"><%=item.getName()%></h2>
				<h2 class="bodytitle"><%=item.getPrice()%>
					円(税込)
				</h2>
			</div>
			<!-- 右側：検索欄＋テンキー -->
			<div class="right-box">
				<div class="pad">
					<form action="CartItemUpdateServlet" method="post">
						<input type="hidden" name="index" value="<%=index%>">
						<%
						for (Option opt : item.getOptionList()) {
							OrderItem.SelectedOption so = oi.findSelectedOptionById(opt.getId());
						%>
						<h3 class="bodymsg"><%=opt.getName()%></h3>
						<%
						for (Option.Selection sel : opt.getSelectionList()) {
						%>
						<label> <input type="radio" name="opt_<%=opt.getId()%>"
							value="<%=sel.id()%>"
							<%=(so != null && so.selectionId() == sel.id()) ? "checked" : ""%>
							required> <%=sel.name()%>（<%=sel.price()%>円）
						</label><br>
						<%
						}
						%>

						<%
						}
						%>

						<h3 class="bodymsg">
							個数： <input type="number" name="count" min="1"
								value="<%=oi.getCount()%>" required>
						</h3>
						<div class="action-buttons flex-center">
							<button type="button" class="btn-back"
								onclick="location.href='<%=request.getContextPath()%>/cart_detail.jsp'">カートに戻る</button>
							<button type="submit" class="btn-next">変更する</button>
						</div>
					</form>
				</div>
			</div>
		</div>

	</main>
	<%@ include file="/shered/cus_footer.jsp"%>

</body>
</html>