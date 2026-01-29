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
    <title>注文情報確認ーキッチン</title>
    <link rel="stylesheet" type="text/css" href="<%= request.getContextPath() %>/css/12_01.css">
    <link rel="stylesheet" type="text/css" href="<%= request.getContextPath() %>/css/header.css">
</head>
<body>
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
    <h1>注文一覧</h1>
    <div class="card-container">
        <div class="card">
            <strong>101室</strong><br>
            生ビール　M　x1<br>
            サラダ　　 x2<br>
            ポテト S　 x1<br>
            <br>
            受取番号：S110101<br>
            <button>調理済み</button>
        </div>
        <div class="card">
            <strong>102室</strong><br>
            生ビール　L　x1<br>
            サラダ　　 x2<br>
            ポテト S　 x1<br>
            <br>
            受取番号：S110102<br>
            <button>調理済み</button>
        </div>
        <div class="card">
            <strong>103室</strong><br>
            生ビール　L　x1<br>
            サラダ　　 x2<br>
            ポテト S　 x1<br>
            <br>
            受取番号：S110103<br>
            <button>調理済み</button>
        </div>
        <div class="card">
            <strong>104室</strong><br>
            生ビール　 x1<br>
            サラダ　　 x2<br>
            ポテト S　 x1<br>
            <br>
            受取番号：S110104<br>
            <button>調理済み</button>
        </div>
        <div class="card">
            <strong>105室</strong><br>
            生ビール　S x1<br>
            サラダ　　 x2<br>
            ポテト S　 x1<br>
            <br>
            受取番号：S110105<br>
            <button>調理済み</button>
        </div>
        <div class="card">
            <strong>106室</strong><br>
            生ビール　S x1<br>
            サラダ　　 x2<br>
            ポテト S　 x1<br>
            <br>
            受取番号：S110106<br>
            <button>調理済み</button>
        </div>
    </div>
    <div class="footer-buttons">
        <button type="button" onclick="location.href='modify_search_kitchen.jsp'">在庫状況の更新</button>
        <button type="button" onclick="location.href='kitchen_order_finished.jsp'">調理済み一覧</button>
    </div>
    <button class="select" onclick="location.href='<%= request.getContextPath() %>/index_select.jsp'">担当選択画面へ戻る</button>
</body>
</html>