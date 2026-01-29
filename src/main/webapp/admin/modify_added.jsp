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
    <title>商品追加完了画面</title>
    <link rel="stylesheet" type="text/css" href="<%= request.getContextPath() %>/css/08_10.css">
    <link rel="stylesheet" type="text/css" href="<%= request.getContextPath() %>/css/header.css">
</head>

<body>
    <!-- Header -->
    <header>
        <div class="header_inner">
            <img class="title_img" src="<%= request.getContextPath() %>/img/logo.png" alt="logo" width="60"
				height="60">
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
                <h2>商品追加完了</h2>
                <h3>下記の内容で商品を追加しました</h3>
                <table>
                    <tr>
                        <th>商品名</th>
                        <td>サラダ</td>
                    </tr>
                    <tr>
                        <th>価格(税込)</th>
                        <td>1000円（税込）</td>
                    </tr>
                    <tr>
                        <th>商品画像</th>
                        <td><img src="./img/melon.png" alt="salad" width="30px" height="30px"></td>
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
                    
                    <button type="button" class="btn-back" onclick="history.back()">商品検索画面へ戻る</button>
                </div>
            </div>
        </div>
    </main>
    
</body>
</html>