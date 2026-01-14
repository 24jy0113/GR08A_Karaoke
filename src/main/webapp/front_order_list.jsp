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
    <title>伝票一覧-フロント</title>
    <link rel="stylesheet" type="text/css" href="./css/12_03.css">
    <link rel="stylesheet" type="text/css" href="./css/header.css">
</head>
<body>
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
    <h1>調理済み一覧</h1>
    <div class="card-container">
        <div class="card">
            <strong>101室</strong><br>
            生ビール　M　x1<br>
            サラダ　　 x2<br>
            ポテト S　 x1<br>
            <br>
            受取番号：S110101<br>
            <button>完了</button>
        </div>
        <div class="card">
            <strong>102室</strong><br>
            生ビール　L　x1<br>
            サラダ　　 x2<br>
            ポテト S　 x1<br>
            <br>
            受取番号：S110102<br>
            <button>完了</button>
        </div>
        <div class="card">
            <strong>103室</strong><br>
            生ビール　L　x1<br>
            サラダ　　 x2<br>
            ポテト S　 x1<br>
            <br>
            受取番号：S110103<br>
            <button>完了</button>
        </div>
        <div class="card">
            <strong>104室</strong><br>
            生ビール　 x1<br>
            サラダ　　 x2<br>
            ポテト S　 x1<br>
            <br>
            受取番号：S110104<br>
            <button>完了</button>
        </div>
        <div class="card">
            <strong>105室</strong><br>
            生ビール　S x1<br>
            サラダ　　 x2<br>
            ポテト S　 x1<br>
            <br>
            受取番号：S110105<br>
            <button>完了</button>
        </div>
        <div class="card">
            <strong>106室</strong><br>
            生ビール　S x1<br>
            サラダ　　 x2<br>
            ポテト S　 x1<br>
            <br>
            受取番号：S110106<br>
            <button>完了</button>
        </div>
    </div>
    <div class="footer-buttons">
        
        <button type="button" onclick="location.href='front_top.jsp'">表示選択画面へ戻る</button>
        <button type="button" onclick="location.href='front_order_finished.jsp'">完了一覧</button>
    </div>
</body>
</html>