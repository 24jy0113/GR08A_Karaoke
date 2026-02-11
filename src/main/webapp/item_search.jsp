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
<html lang=ja>
<head>
<meta charset="UTF-8">
<title>メニュー番号入力</title>

<link rel="stylesheet" type="text/css"
	href="<%=request.getContextPath()%>/css/header.css">
<link rel="stylesheet" type="text/css"
	href="<%=request.getContextPath()%>/css/default.css">
<link rel="stylesheet" type="text/css"
	href="<%=request.getContextPath()%>/css/06_01.css">
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
		<div class="page">
			<%
			String error = (String) request.getAttribute("error");
			%>
			<%
			if (error != null) {
			%>
			<p class="errermsg"><%=error%></p>
			<%
			}
			%>
			<aside class="ad-area">
				<img class="campaign"
					src="<%=request.getContextPath()%>/img/campaign.png" alt="campaign"
					width="260" height="320">
				<button class="btn-back side-back"
					onclick="location.href='<%=request.getContextPath()%>/cus_top.jsp'">
					トップページへ</button>
			</aside>
			<section class="main-area">
				<h1 class="bodytitle">メニュー番号で商品を探す</h1>
				<p class="bodymsg">メニュー番号を入力してください</p>

				<div class="input-row">
					<form action="<%=request.getContextPath()%>/ItemSearchServlet"
						method="get">
						<input id="menuInput" name="orderNumber" type="text" maxlength="4"
							readonly />
						<button id="searchBtn" type="submit">検索</button>
					</form>
				</div>
				<div class="pad">
					<button>1</button>
					<button>2</button>
					<button>3</button>
					<button>4</button>
					<button>5</button>
					<button>6</button>
					<button>7</button>
					<button>8</button>
					<button>9</button>
					<button id="clear">←</button>
					<button>0</button>
				</div>
			</section>
		</div>
		<script>
                const input = document.getElementById('menuInput');
                const padButtons = document.querySelectorAll('.pad button');
                
                padButtons.forEach(btn => {
                    btn.addEventListener('click', () => {
                        const val = btn.textContent;
                        if (val === '←') { input.value = input.value.slice(0, -1); return; }
                        if (input.value.length < 4) input.value += val;
                    });
                });
            </script>

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