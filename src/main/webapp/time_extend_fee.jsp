<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8" import="model.Room"%>

<%
Room room = (Room) session.getAttribute("room");
Integer remainingMinutes = (Integer) session.getAttribute("remainingMinutes");
%>
<!DOCTYPE html>
<html lang="ja">

<head>
<meta charset="UTF-8">
<title>料金設定表</title>
<link rel="stylesheet" type="text/css"
	href="<%=request.getContextPath()%>/css/header.css">
<link rel="stylesheet" type="text/css"
	href="<%=request.getContextPath()%>/css/default.css">
<link rel="stylesheet" type="text/css"
	href="<%=request.getContextPath()%>/css/04.css">
</head>

<body>
	<!-- Header -->
	<header>
		<div class="header_inner">
			<img class="title_img"
				src="<%=request.getContextPath()%>/img/logo.png" alt="logo"
				width="60" height="60">
			<h1 class="title_name">七福サウンド</h1>
			<nav class="gnav">
				<ul class="gnav_list">
					<li><a href="<%=request.getContextPath()%>/cus_top.jsp">トップへ</a></li>
					<li><a href="<%=request.getContextPath()%>/ExtendCanServlet">延長申請</a></li>
					<li><a href="<%=request.getContextPath()%>/item_search.jsp">メニューを番号で探す</a></li>
					<li><a href="<%=request.getContextPath()%>/item_list.jsp">フード・ドリンク</a></li>
					<li><a href="<%=request.getContextPath()%>/cusPurchaseHistory">注文履歴</a></li>
					<li><a class="gnav_botton" href="cart_detail.jsp"> <img
							class="cart_img" src="<%=request.getContextPath()%>/img/cart.png"
							alt="cart" width="20" height="20">カート内容を確認
					</a></li>
				</ul>
			</nav>
		</div>
	</header>
	<main>
		<div class="container text-center">
			<h1 class="bodytitle">料金設定表</h1>
			<table class="table_border">
				<tr>
					<th></th>
					<th>時間帯</th>
					<th>一般料金</th>
					<th>学割・シニア割</th>
				</tr>
				<tr>
					<th>月～金・祝日前</th>
					<td>9時～19時</td>
					<td>240円</td>
					<td>190円</td>
				</tr>
				<tr>
					<th>土・日・祝日</th>
					<td>9時～19時</td>
					<td>230円</td>
					<td>280円</td>
				</tr>
				<tr>
					<th>月～木・日・祝日</th>
					<td>19時～22時</td>
					<td>490円</td>
					<td>400円</td>
				</tr>
				<tr>
					<th>金・土・祝日前</th>
					<td>19時～22時</td>
					<td>540円</td>
					<td>450円</td>
				</tr>
			</table>
			<div class="action-buttons">
				<button type="button" class="btn-back" onclick="history.back()">延長申請へ戻る</button>
			</div>
		</div>
		<div class="footer-wrap">
			<%
			if (room != null) {
			%>
			<h1>
				部屋番号
				<%=room.getRoomNo()%></h1>
			<%
			}
			%>
			<%
			if (remainingMinutes != null) {
			%>
			<h1>
				残り時間
				<%=remainingMinutes%>
				分
			</h1>
			<%
			}
			%>
		</div>
	</main>

</body>

</html>