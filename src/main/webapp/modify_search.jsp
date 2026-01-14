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
<!DOCTYPE html>
<html lang=ja>
<head>
    <meta charset="UTF-8">
    <title>商品変更検索画面-管理者</title>
    <link rel="stylesheet" type="text/css" href="./css/08_01.css">
    <link rel="stylesheet" type="text/css" href="./css/header.css">
</head>

<body>
    <!-- Header -->
    <header>
        <div class="header_inner">
            <img class="title_img" src="./img/logo.png" alt="logo" width="60" height="60">
            <h1 class="title_name">七福サウンド</h1>
            <nav class="gnav">
                <ul class="gnav_list">
                     <li><img class="user_img" src="./img/user.png" alt="cart" width="25" height="25"><%= user.getUserName() %></li>
                    <li><a class="gnav_botton" href="<%= request.getContextPath() %>/LogoutServlet">ログアウト</a></li>
                </ul>
            </nav>
        </div>
    </header>
    <main>
        <div class="bodymsg">
            <div class="msg">
                <h1>商品検索</h1>
                <h3>変更する商品名を入力してください</h3>
            </div>
            <input type="search" name="q" />
            <button class="search" onclick="location.href='08_02該当商品詳細画面-管理者.html'">検索</button>
            
            <div class="action-buttons">
                <button type="button" class="btn-back" onclick="location.href='manage_top.jsp'">表示選択画面へ戻る</button>
                <button type="submit" class="btn-next" onclick="location.href='modify_add.jsp'">商品新規追加</button>
            </div>
        </div>
    </main>
    
</body>
</html>