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
<title>オプション選択</title>
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
			<h2 class="bodytitle">オプションをお選びください（オプションがなければ「次へ」）</h2>
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
				<!-- 右側 -->
				<div class="right-box">
					<div class="pad">
						<form action="ItemNumServlet" method="post">
							<%
							for (Option opt : item.getOptionList()) {
							%>
							<h3 class="bodymsg"><%=opt.getName()%>（追加料金）
							</h3>

							<%
							for (Option.Selection sel : opt.getSelectionList()) {
							%>
							<label> <input type="radio" name="opt_<%=opt.getId()%>"
								value="<%=sel.id()%>" required> <%=sel.name()%>（<%=sel.price()%>円）
							</label><br>
							<%
							}
							%>
							<%
							}
							%>
							<div class="action-buttons flex-center">
								<button type="button" class="btn-back"
									onclick="location.href='<%=request.getContextPath()%>/item_detail?id=<%=item.getId()%>'">戻る</button>
								<button type="submit" class="btn-next">次へ</button>
							</div>
						</form>
					</div>
				</div>
			</div>

		</div>


	</main>
	<%@ include file="/shered/cus_footer.jsp"%>
</body>
</html>