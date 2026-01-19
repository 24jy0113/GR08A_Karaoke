<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"
    import="java.util.*, model.User"
%>
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
    <link rel="stylesheet" type="text/css" href="<%= request.getContextPath() %>/css/01_05.css">
    <link rel="stylesheet" type="text/css" href="<%= request.getContextPath() %>/css/header.css">
</head>

<body>
    <!-- Header -->
    <header>
        <div class="header_inner">
            <img class="title_img" src="<%= request.getContextPath() %>/img/logo.png" alt="logo" width="60" height="60">
            <h1 class="title_name">七福サウンド</h1>
            <nav class="gnav">
                <ul class="gnav_list">
                     <li><img class="user_img" src="<%= request.getContextPath() %>/img/user.png" alt="cart" width="25" height="25"><%= user.getUserName() %></li>
                    <li><a class="gnav_botton" href="<%= request.getContextPath() %>/LogoutServlet">ログアウト</a></li>
                </ul>
            </nav>
        </div>
    </header>
    <main>
        <div class="bodymsg">
            <div class="msg">
                <h2>検索結果</h2>
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
						    <td><%= u.getUserId() %></td>
						    <td><%= u.getUserName() %></td>
						    <td><%= u.getRoleName() %></td>
						    <td>XXXXXXXX</td>
						    <td><%= u.getLastLoginTime() %></td>
						    <td>
						        <form action="<%= request.getContextPath() %>/admin/account_update.jsp" method="post">
						            <input class="btn edit" type="hidden" name="userId" value="<%= u.getUserId() %>">
						            <input type="hidden" name="userName" value="<%= u.getUserName() %>">
						            <button type="submit">変更</button>
						        </form>
						    </td>
						    <td>
						      <form action="<%= request.getContextPath() %>/admin/account_delete_notice.jsp" method="post">
	                            <input class="btn edit" type="hidden" name="userId" value="<%= u.getUserId() %>">
						        <input type="hidden" name="userName" value="<%= u.getUserName() %>">
						        <input type="hidden" name="roleName" value="<%= u.getRoleName() %>">
	                            <button class="btn delete" type="submit">削除</button>
	                          </form>
                        </td>
						</tr>
						<% } %>
                </table>
                <div class="action-buttons">
                    <button type="button" class="btn-back" onclick="history.back()">アカウント検索へ戻る</button>
                </div>
            </div>
        </div>
    </main>
    
</body>
</html>