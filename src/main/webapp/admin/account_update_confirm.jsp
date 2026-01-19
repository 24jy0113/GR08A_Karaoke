<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"
    import="model.User"
%>
<%
User user = (User) session.getAttribute("loginUser");
if (user == null) {
    response.sendRedirect(request.getContextPath() + "/index.jsp");
    return;
}
%>
<%
User u = (User) session.getAttribute("UPDATE_USER");
%>
<!DOCTYPE html>
<html lang=ja>
<head>
    <meta charset="UTF-8">
    <title>アカウント情報の変更確認</title>
    <link rel="stylesheet" type="text/css" href="<%= request.getContextPath() %>/css/01_07.css">
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
        <div>
            <h1 class="bodymsg">アカウント情報の変更確認</h1>
            <p class="bodymsg">下記の情報を確認してください</p>
            <table class="tbstyle">
                <tbody>
                    <tr>
                        <th>アカウントID</th>
                        <td><%= u.getUserId() %></td>
                    </tr>
                    <tr>
                        <th>アカウント名</th>
                        <td><%= u.getUserName() %></td>
                    </tr>
                    <tr>
                        <th>パスワード</th>
                        <td>XXXXXXXX</td>
                    </tr>
                    <tr>
                        <th>役割</th>
                        <td><%= u.getRoleName() %></td>
                    </tr>
                    
                </tbody>
            </table>
            
            <div class="action-buttons">
                <button type="button" class="btn-back" onclick="history.back()">修正する</button>
                <form action="AccountUpdateConfirmServlet" method="post">
				    <button type="submit" class="btn-next">変更を完了する</button>
				</form>
            </div>
        </div>
    </main>
    
</body>
</html>