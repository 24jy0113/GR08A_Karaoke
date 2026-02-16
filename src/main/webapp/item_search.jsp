<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8" import="model.Item,model.Room, model.Option"%>
<%
Item item = (Item) request.getAttribute("item");
%>
<%
Room room = (Room) session.getAttribute("room");
Integer remainingMinutes = (Integer) session.getAttribute("remainingMinutes");
%>
<%
String error = (String) request.getAttribute("error");
%>
<!DOCTYPE html>
<html lang="ja">
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
	<%@ include file="/shered/cus_header.jsp"%>
	<main>
		<div class="page">
			<%
			if (error != null) {
			%>
			<p class="errormsg"><%=error%></p>
			<%
			}
			%>
			<aside class="ad-area">
				<img class="campaign"
					src="<%=request.getContextPath()%>/img/menu.png" alt="menu"
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
	</main>
	<%@ include file="/shered/cus_footer.jsp"%>
</body>
</html>