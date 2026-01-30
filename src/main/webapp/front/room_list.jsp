<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8" import="model.User" import="java.util.List"
	import="java.util.ArrayList" import="model.Room" import="java.sql.Time"%>

<%
User user = (User) session.getAttribute("loginUser");
if (user == null) {
	response.sendRedirect(request.getContextPath() + "/index.jsp");
	return;
}
%>
<%
List<Room> roomList = (List<Room>) request.getAttribute("roomList");
if (roomList == null) {
	response.sendRedirect(request.getContextPath() + "/RoomListServlet");
	return;
}
%>
<!DOCTYPE html>
<html lang="ja">
<head>
<meta charset="UTF-8">
<title>部屋状況画面</title>
<link rel="stylesheet" type="text/css"
	href="<%=request.getContextPath()%>/css/11_01.css">
<link rel="stylesheet" type="text/css"
	href="<%=request.getContextPath()%>/css/header.css">
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
					<li><img class="user_img"
						src="<%=request.getContextPath()%>/img/user.png" alt="cart"
						width="25" height="25"><%=user.getUserName()%></li>
					<li><a class="gnav_botton"
						href="<%=request.getContextPath()%>/LogoutServlet">ログアウト</a></li>
				</ul>
			</nav>
		</div>
	</header>
	<main>
		<div class="bodymsg">
			<div>
				<div></div>
				<h2>部屋状況一覧</h2>
				<div class="block">
					<form action="<%=request.getContextPath()%>/RoomListServlet" method="get">
						<select name="statusId">
							<option value="0" selected>すべて</option>
							<option value="1">空き</option>
							<option value="2">予約</option>
							<option value="3">受付済み</option>
							<option value="4">会計済み</option>
						</select>
						<button type="submit">絞り込み</button>
					</form>
					<button id="reloadButton">更新</button>

				</div>
				<table>
					<tr>
						<th></th>
						<th>部屋番号</th>
						<th>酒類提供</th>
						<th>受付時間</th>
						<th>退室時間</th>
						<th>状態</th>
						<th>予約受付時間</th>
						<th></th>
					</tr>
					<!-- 1行目 -->
					<%
					for (Room room : roomList) {
					%>
					<form action="<%=request.getContextPath()%>/RoomUpdateServlet"
						method="post">
						<tr>
							<input type="hidden" name="roomId" value="<%=room.getId()%>">
							<td>
								<button type="button"
									onclick="location.href='front_cus_top.jsp?roomId=<%=room.getId()%>'"
									style="background-color: black; color: aliceblue;">
									延長・注文</button>
							</td>
							<!-- 部屋番号 -->
							<td><%=room.getRoomNo()%></td>
							<!-- 酒類の提供 -->
							<td><select name="alcohol">
									<option value="1" <%=room.isAlcohol() ? "selected" : ""%>>可能</option>
									<option value="0" <%=!room.isAlcohol() ? "selected" : ""%>>不可</option>
							</select></td>
							<!-- 受付時間 -->
							<td><input type="time" name="receptionTime"
								value="<%=room.getReceptionTime() != null ? room.getReceptionTime().toLocalTime() : ""%>"></td>
							<!-- 退室時間 -->
							<td>
								<%
								// 予約あり.
								if (room.getRes_receptionTime() != null) {
								%> <input type="time" name="leavingTime"
								value="<%=room.getLeavingTime().toLocalTime()%>" readonly>
								<%
								} else {
								// 予約なし.
								%><input type="time" name="leavingTime"
								value="<%=room.getLeavingTime() != null ? room.getLeavingTime().toLocalTime() : ""%>">
								<%
								}
								%>
							</td>
							<!-- 状態 -->
							<td><select name="statusId">
									<option value="1"
										<%=room.getStatusId() == 1 ? "selected" : ""%>>空き</option>
									<option value="2"
										<%=room.getStatusId() == 2 ? "selected" : ""%>>予約</option>
									<option value="3"
										<%=room.getStatusId() == 3 ? "selected" : ""%>>受付済み</option>
									<option value="4"
										<%=room.getStatusId() == 4 ? "selected" : ""%>>会計済み</option>
							</select></td>
							<!-- 予約受付時間 -->
							<td>
								<%
								// reservationTime が null でない場合のみ表示
								if (room.getRes_receptionTime() != null) {
									out.print(room.getRes_receptionTime().toLocalTime());
								} else {
									out.print(""); // 空白
								}
								%>
							</td>
							<td>
								<button type="submit">更新</button>
							</td>
						</tr>
					</form>
					<%
					}
					%>
				</table>
				<div class="action-buttons">
					<button type="button" class="btn-back"
						onclick="location.href='front_top.jsp'">表示選択画面へ</button>
				</div>
			</div>
		</div>

	</main>
	<script>
		// ページ更新ボタンのクリックイベントを設定
		document.getElementById('reloadButton').addEventListener('click',
				function() {
					location.reload(); // ページをリロード
				});
	</script>
</body>
</html>