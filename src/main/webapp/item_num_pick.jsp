<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8" import="model.*"%>
<%
Item item = (Item) request.getAttribute("item");
%>
<%
Room room = (Room) session.getAttribute("room");
%>
<%
OrderItem oi = (OrderItem) session.getAttribute("buildingItem");
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
	<main>
		<div class="text-center">
			<h2 class="bodytitle">注文個数をお選びください</h2>
			<div class="container">
				<!-- 左側：画像領域 -->
				<div>
					<div class="left-box">
						<img class="item-img"
							src="<%=request.getContextPath()%>/img/<%=item.getImage()%>"
							alt="product">
					</div>
					<h2 class="bodytitle"><%=item.getName()%></h2>
					<h2 class="bodytitle"><%=item.getPrice()%>円(税込)
					</h2>
				</div>
				<!-- 右側 -->
				<div class="right-box">
					<form action="CartAddServlet" method="post">
						<h3 class="bodymsg">
							個数： <input type="number" name="count" min="1" value="1"
								class="quantity-input" required>
						</h3>
						<div class="action-buttons flex-center">
							<button type="button" class="btn-back"
								onclick="location.href='<%=request.getContextPath()%>/ItemOptionServlet?itemId=<%=item.getId()%>'">
								キャンセル</button>
							<button type="submit" class="btn-next">次へ</button>
						</div>
					</form>
				</div>
			</div>
		</div>
	</main>
	<%@ include file="/shered/cus_footer.jsp"%>

</body>
</html>