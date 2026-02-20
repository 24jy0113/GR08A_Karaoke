<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8" import="java.util.*, model.User"%>
<%
User user = (User) session.getAttribute("loginUser");
if (user == null) {
	response.sendRedirect(request.getContextPath() + "/index.jsp");
	return;
}
%>
<!DOCTYPE html>
<html lang="ja">
<head>
<meta charset="UTF-8">
<title>アカウント情報一覧画面</title>

<link rel="stylesheet" type="text/css"
	href="<%=request.getContextPath()%>/css/header.css">
<link rel="stylesheet" type="text/css"
	href="<%=request.getContextPath()%>/css/default.css">
<link rel="stylesheet" type="text/css"
	href="<%=request.getContextPath()%>/css/01_05.css">
</head>

<body>
	<%@ include file="/shared/biz_header.jsp"%>
	<main class="container">
		<div class="text-center">
			<h2 class="bodytitle">検索結果</h2>
			<table>
				<tr>
					<th>アカウントID</th>
					<th>アカウント名</th>
					<th>役割</th>
					<th>パスワード</th>
					<th>最終ログイン日時</th>
					<th></th>
					<th></th>
				</tr>
				<%
				List<User> list = (List<User>) request.getAttribute("userList");
				for (User u : list) {
				%>
				<tr>
					<td><%=u.getUserId()%></td>
					<td><%=u.getUserName()%></td>
					<td><%=u.getRoleName()%></td>
					<td>XXXXXXXX</td>
					<td><%=u.getLastLoginTime()%></td>
					<td>
						<form
							action="<%=request.getContextPath()%>/AccountUpdateInitServlet"
							method="post">
							<input class="btn edit" type="hidden" name="userId"
								value="<%=u.getUserId()%>">
							<button type="submit">変更</button>
						</form>
					</td>
					<td>
						<form
							action="<%=request.getContextPath()%>/AccountDeleteInitServlet"
							method="post">
							<input class="btn edit" type="hidden" name="userId"
								value="<%=u.getUserId()%>">
							<button class="btn delete" type="submit">削除</button>
						</form>
					</td>
				</tr>
				<%
				}
				%>
			</table>
			<div class="action-buttons flex-center">
				<button type="button" class="btn-back"
					onclick="location.href='<%=request.getContextPath()%>/admin/account_search.jsp'">アカウント検索へ戻る</button>
			</div>
		</div>
	</main>

</body>
</html>