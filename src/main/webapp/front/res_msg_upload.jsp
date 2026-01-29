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
<html lang="ja">
<head>
    <meta charset="UTF-8">
    <title>予約情報取得画面</title>
    <link rel="stylesheet" type="text/css" href="<%= request.getContextPath() %>/css/10_03.css">
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
            <div>
                <div class="msg">
                    <h2>予約情報取得</h2>
                    <h3>読み込む予約情報のデータをアップロードしてください</h3>
                </div>
                <div class="input-row">
                    <label>予約データ</label>
                    <label class="csv-box">
                        CSV
                        <input type="file" name="csv" class="hidden-file">
                    </label>
                    <img src="./img/file.png" alt="file">
                </div>
                <div class="action-buttons">
                    <button type="button" class="btn-back" onclick="location.href='res_list_manager.jsp'">予約情報画面へ戻る</button>
                    <button type="submit" class="btn-next" onclick="location.href='res_msg_uploaded.jsp'">予約情報を取得する</button>
                </div>
            </div>
        </div>
    </main>
    
</body>
</html>