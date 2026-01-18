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
    <title>アカウント削除</title>
    <link rel="stylesheet" type="text/css" href="../css/01_09.css">
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
                    <li><img class="user_img" src="../img/user.png" alt="cart"
						width="25" height="25"><%= user.getUserName() %></li>
					<li><a class="gnav_botton" href="<%= request.getContextPath() %>/LogoutServlet">ログアウト</a></li>
                </ul>
            </nav>
        </div>
    </header>
    <main>
        <div>
            <div class="bodymsg">
                <h1>アカウント削除</h1>
                <h3>本当にこのアカウントを削除しますか？</h3>
                <h4>※この操作は元に戻せません。</h4>
            </div>
            <table class="tbstyle">
                <tbody>
                    <tr>
                        <th>アカウントID</th>
                        <td>SF0112</td>
                    </tr>
                    <tr>
                        <th>アカウント名</th>
                        <td>佐藤花子</td>
                    </tr>
                    <tr>
                        <th>パスワード</th>
                        <td>XXXXXXXX</td>
                    </tr>
                    <tr>
                        <th>権限情報</th>
                        <td>フロント</td>
                        <td>フロア</td>
                        <td>キッチン</td>
                    </tr>
                    <tr>
                        <th></th>
                        <td>O</td>
                        <td>X</td>
                        <td>O</td>
                    </tr>
                </tbody>
            </table>
            <div class="action-buttons">
                <button type="button" class="btn-back" onclick="location.href='account_search_result.jsp'">キャンセル</button>
                <button class="btn delete" onclick="location.href='account_deleted_msg.jsp'">削除する</button>
            </div>
        </div>
    </main>
    
</body>
</html>