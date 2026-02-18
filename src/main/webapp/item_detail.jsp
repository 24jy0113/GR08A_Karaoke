<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8" import="model.Item,model.Room, model.Option"%>
<%
Item item = (Item) request.getAttribute("item");
%>
<%
Room room = (Room) session.getAttribute("room");
Integer remainingMinutes = (Integer) session.getAttribute("remainingMinutes");
%>
<!DOCTYPE html>
<html lang="ja">
<head>
<meta charset="UTF-8">
<title>商品詳細情報画面</title>

<link rel="stylesheet" type="text/css"
	href="<%=request.getContextPath()%>/css/header.css">
<link rel="stylesheet" type="text/css"
	href="<%=request.getContextPath()%>/css/default.css">
<link rel="stylesheet" type="text/css"
	href="<%=request.getContextPath()%>/css/06_03.css">
</head>
<body>
	<%@ include file="/shered/cus_header.jsp"%>
	<main>
		<div class="text-center">
			<div class="container">
				<!-- 左側：画像領域 -->
				<div class="left-box">
					<img class="item-img"
						src="<%=request.getContextPath()%>/img/<%=item.getImage()%>"
						alt="product">
				</div>
				<!-- 右側：検索欄＋テンキー -->
				<div class="right-box">
					<h1 class="item-name"><%=item.getName()%></h1>
					<h2 class="item-price"><%=item.getPrice()%>
						円(税込)
					</h2>
					<div class="pad">
						<%
						if (!item.isStock()) {
						%>
						<h3 class="errormsg">この商品は現在売切れです</h3>
						<!-- 押せないボタン -->
						<button type="button" class="btn-next is-disabled" disabled>カートに入れる</button>
						<%
						} else {
						%>
						<!-- 在庫あり：通常ボタン -->
						<form action="ItemOptionServlet" method="get">
							<input type="hidden" name="itemId" value="<%=item.getId()%>">
							<button type="submit" class="btn-next">カートに入れる</button>
						</form>
						<%
						}
						%>
						<button type="button" class="btn-back"
							onclick="location.href='<%=request.getContextPath()%>/item_list'">メニュー一覧に戻る</button>
						<button type="button" class="btn-back"
							onclick="location.href='<%=request.getContextPath()%>/item_search.jsp'">メニューを番号で探す</button>
					</div>
				</div>
			</div>

		</div>
	</main>
	<%@ include file="/shered/cus_footer.jsp"%>

</body>
</html>