<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8" import="model.Room,java.util.List"%>
<%
Room room = (Room) session.getAttribute("room");
Integer remainingMinutes = (Integer) session.getAttribute("remainingMinutes");
%>
<%
List<Integer> availableMinutes = (List<Integer>) request.getAttribute("availableMinutes");
%>
<!DOCTYPE html>
<html lang="ja">
<head>
<meta charset="UTF-8">
<title>延長時間選択画面</title>

<link rel="stylesheet" type="text/css"
	href="<%=request.getContextPath()%>/css/header.css">
<link rel="stylesheet" type="text/css"
	href="<%=request.getContextPath()%>/css/default.css">
<link rel="stylesheet" type="text/css"
	href="<%=request.getContextPath()%>/css/04.css">
</head>
<body>
	<%@ include file="/shered/cus_header.jsp"%>
	<main>
		<div class="container text-center">
			<h1 class="bodymsg">延長が可能です！</h1>
			<h2 class="bodymsg">ご希望の延長時間を選択し、確認ボタンを押してください</h2>
			<form action="<%=request.getContextPath()%>/ExtendServlet"
				method="post">
				<table>
					<tr>
						<th>部屋番号</th>
						<th>受付時間</th>
						<th>退室時間</th>
						<th>延長（分）</th>
					</tr>
					<!-- 1行目 -->
					<tr>
						<td><%=room.getRoomNo()%></td>
						<td><%=room.getReceptionTime()%></td>
						<td><%=room.getLeavingTime()%></td>
						<td>
							<!-- 延長（分） -->
							<div class="block">
								<select name="extendMinutes">
									<%
									for (Integer m : availableMinutes) {
									%>
									<option value="<%=m%>"><%=m%> 分
									</option>
									<%
									}
									%>
								</select>
							</div> <!-- ＊料金設定詳細 -->
							<div class="small-text">
								＊<a href="time_extend_fee.jsp">料金設定詳細</a>
							</div>
						</td>
					</tr>
				</table>
				<div class="action-buttons flex-center">
					<button type="button" class="btn-back"
						onclick="location.href='<%=request.getContextPath()%>/cus_top.jsp'">トップページへ戻る</button>
					<button type="submit" class="btn-next">確認する</button>
				</div>
			</form>
		</div>
	</main>
	<%@ include file="/shered/cus_footer.jsp"%>
</body>
</html>