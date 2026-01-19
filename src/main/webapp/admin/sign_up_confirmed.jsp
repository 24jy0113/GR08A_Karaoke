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
String roleIdStr = (String) session.getAttribute("SIGNUP_ROLE_ID");
%>
<!DOCTYPE html>
<html lang=ja>
<head>
    <meta charset="UTF-8">
    <title>アカウント登録完了画面</title>
    <link rel="stylesheet" type="text/css" href="../css/01_02.css">
    <link rel="stylesheet" type="text/css" href="../css/header.css">
</head>
<body>
    <!-- Header -->
    <header>
        <div class="header_inner">
            <img class="title_img" src="../img/logo.png" alt="logo" width="60" height="60">
            <h1 class="title_name">七福サウンド</h1>
            <nav class="gnav">
                <ul class="gnav_list">
                     <li><img class="user_img" src="../img/user.png" alt="cart" width="25" height="25"><%= user.getUserName() %></li>
                    <li><a class="gnav_botton" href="<%= request.getContextPath() %>/LogoutServlet">ログアウト</a></li>
                </ul>
            </nav>
        </div>
    </header>
    <main>
        <div>
            <h1 class="bodymsg">アカウント登録完了</h1>
            <p class="bodymsg">下記の情報でアカウントを登録しました</p>
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
                        <td><%= roleIdStr %></td>
                    </tr>
            
                </tbody>
            </table>
            
            <div class="action-buttons">
                <button type="button" class="btn-back" onclick="location.href='account_search.jsp'">アカウント検索へ戻る</button>
                
            </div>
        </div>
    </main>
    
</body>
</html>