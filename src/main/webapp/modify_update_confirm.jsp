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
    <title>商品情報確認画面-変更-管理者</title>
    <link rel="stylesheet" type="text/css" href="./css/08_04.css">
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
                <h2>商品情報確認</h2>
                <table>
                    <tr>
                        <th>商品名</th>
                        <td>サラダ</td>
                    </tr>
                    <tr>
                        <th>価格(税込)</th>
                        <td>300円（税込）</td>
                    </tr>
                    <tr>
                        <th>商品画像</th>
                        <td><img src="./img/salad.png" alt="salad" width="30px" height="30px"></td>
                    </tr>
                    <tr>
                        <th>注文番号</th>
                        <td>001</td>
                    </tr>
                    <tr>
                        <th>カテゴリー</th>
                        <td>フードメニュー</td>
                    </tr>
                    <tr>
                        <th>在庫</th>
                        <td>なし</td>
                    </tr>
                    <tr>
                        <th>オプション</th>
                        <td>なし</td>
                    </tr>
                </table>
                <div class="action-buttons">
                    
                    <button type="button" class="btn-back" onclick="location.href='modify_update.jsp'">修正する</button>
                    <button type="submit" class="btn-next" onclick="location.href='modify_updated.jsp'">確認する</button>
                </div>
            </div>
        </div>
    </main>
    
</body>
</html>