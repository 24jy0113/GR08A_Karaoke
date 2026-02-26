<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8" import="model.Item,model.Room, model.Option"%>
<%
Item item = (Item) request.getAttribute("item");
%>
<%
Room room = (Room) session.getAttribute("room");
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
	<%@ include file="/shared/cus_header.jsp"%>
	<main>
		<div class="page">
			<aside class="ad-area">
				<img class="campaign"
					src="<%=request.getContextPath()%>/img/menu.png" alt="menu"
					width="260" height="320">
				<button class="btn-back side-back"
					onclick="location.href='<%=request.getContextPath()%>/cus_top.jsp'">
					トップページへ</button>
			</aside>
			<section class="main-area">
				<h1 class="bodytitle">メニュー番号から商品を探す</h1>
				<c:if test="${ error != null}">
					<p class="errormsg">${error}</p>
				</c:if>
				<div class="input-row">
					<form action="<%=request.getContextPath()%>/ItemSearchServlet"
						method="get">
						<input id="menuInput" name="orderNumber" type="text" maxlength="4"
							readonly />
						<button id="searchBtn" type="submit">検索</button>
						<div class="pad">
							<button type="button">1</button>
							<button type="button">2</button>
							<button type="button">3</button>
							<button type="button">4</button>
							<button type="button">5</button>
							<button type="button">6</button>
							<button type="button">7</button>
							<button type="button">8</button>
							<button type="button">9</button>
							<button type="button">←</button>
							<button type="button">0</button>
						</div>
					</form>
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
                        if (/^\d$/.test(val) && input.value.length < 4) {
                            input.value += val;
                        }
                    });
                });
            </script>
	</main>
	<%@ include file="/shared/cus_footer.jsp"%>
</body>
</html>