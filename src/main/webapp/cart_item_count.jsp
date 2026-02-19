<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8" import="java.util.*,model.*"%>
<%
int index = Integer.parseInt(request.getParameter("index"));
ArrayList<OrderItem> cart = (ArrayList<OrderItem>) session.getAttribute("cart");
OrderItem oi = cart.get(index);
Item item = oi.getItem();

// ステップ1から渡されたオプションパラメータを取得・保持
Map<Integer, Integer> selectedOptions = new LinkedHashMap<>();
for (Option opt : item.getOptionList()) {
	String param = request.getParameter("opt_" + opt.getId());
	if (param != null) {
		selectedOptions.put(opt.getId(), Integer.parseInt(param));
	}
}
%>
<%
Room room = (Room) session.getAttribute("room");
%>
<!DOCTYPE html>
<html lang="ja">
<head>
<meta charset="UTF-8">
<title>個数選択</title>
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
		<h2 class="bodytitle">個数を選択してください</h2>
		<div class="container">
			<!-- 左側：画像領域 -->
			<div>
				<div class="left-box">
					<img class="item-img"
						src="<%=request.getContextPath()%>/img/<%=item.getImage()%>"
						alt="product">
				</div>
				<h2 class="bodytitle"><%=item.getName()%></h2>
				<h2 class="bodytitle"><%=item.getPrice()%> 円(税込)</h2>
			</div>
			<!-- 右側：選択済みオプション表示＋個数入力 -->
			<div class="right-box">
				<div class="pad">
					<!-- 選択済みオプションの確認表示 -->
					<h3 class="bodymsg">選択済みオプション：</h3>
					<%
					for (Option opt : item.getOptionList()) {
						Integer selId = selectedOptions.get(opt.getId());
						if (selId != null) {
							for (Option.Selection sel : opt.getSelectionList()) {
								if (sel.id() == selId) {
					%>
					<p><%=opt.getName()%>：<%=sel.name()%>（<%=sel.price()%>円）</p>
					<%
								}
							}
						}
					}
					%>

					<form action="CartItemUpdateServlet" method="post">
						<input type="hidden" name="index" value="<%=index%>">

						<!-- ステップ1で選択したオプションをhiddenで引き継ぐ -->
						<%
						for (Map.Entry<Integer, Integer> entry : selectedOptions.entrySet()) {
						%>
						<input type="hidden" name="opt_<%=entry.getKey()%>"
							value="<%=entry.getValue()%>">
						<%
						}
						%>

						<h3 class="bodymsg">
							個数：
							<input type="number" name="count" min="1"
								value="<%=oi.getCount()%>" required>
						</h3>
						<div class="action-buttons flex-center">
							<button type="button" class="btn-back"
								onclick="location.href='<%=request.getContextPath()%>/cart_item_option.jsp'">オプション選択に戻る</button>
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
