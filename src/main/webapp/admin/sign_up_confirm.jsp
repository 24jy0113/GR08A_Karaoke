<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"
    import="model.User"
%>
<%@ taglib prefix="auth" uri="/auth" %>
<%
User user = (User) session.getAttribute("loginUser");
if (user == null) {
    response.sendRedirect(request.getContextPath() + "/index.jsp");
    return;
}
%>
<%
String userId = (String) session.getAttribute("SIGNUP_USER_ID");
String userName = (String) session.getAttribute("SIGNUP_USER_NAME");
String roleName = (String) session.getAttribute("SIGNUP_ROLE_NAME");
%>

<!DOCTYPE html>
<html lang=ja>
<head>
    <meta charset="UTF-8">
    <title>アカウント登録確認</title>
    <link rel="stylesheet" type="text/css" href="<%= request.getContextPath() %>/css/header.css">
    <link rel="stylesheet" type="text/css" href="<%= request.getContextPath() %>/css/default.css">
    <link rel="stylesheet" type="text/css" href="<%= request.getContextPath() %>/css/01_02.css">
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
            <h1 class="bodymsg">アカウント登録情報の確認</h1>
            <p class="bodymsg">下記の情報を確認してください</p>
            <table class="tbstyle">
                <tbody>
                    <tr>
                        <th>アカウントID</th>
                        <td><%= userId %></td>
                    </tr>
                    <tr>
                        <th>アカウント名</th>
                        <td><%= userName %></td>
                    </tr>
                    <tr>
                        <th>パスワード</th>
                        <td>XXXXXXXX</td>
                    </tr>
                    <tr>
                        <th>役割情報</th>
                        <td><%= roleName %></td>
                    </tr>
                    
            
                </tbody>
            </table>
            
            <div class="action-buttons flex-center">
                <button type="button" class="btn-back" onclick="location.href='sign_up.jsp'">修正する</button>
                <form action="<%=request.getContextPath()%>/SignUpCompleteServlet" method="post">
				    <button type="submit" class="btn-next">ユーザ登録を完了する</button>
				</form>
            </div>
        </div>
    </main>
    
</body>
</html>