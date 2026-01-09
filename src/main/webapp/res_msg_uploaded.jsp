<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="ja">
<head>
    <meta charset="UTF-8">
    <title>予約情報登録完了画面</title>
    <link rel="stylesheet" type="text/css" href="./css/10_04.css">
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
                    <li><img class="user_img" src="./img/user.png" alt="cart" width="25" height="25">佐藤 花子</li>
                    <li><a class="gnav_botton" href="./index.jsp">ログアウト</a></li>
                </ul>
            </nav>
        </div>
    </header>
    <main>
        <div class="bodymsg">
            <div>
                
                <h2>予約情報取得完了</h2>
                <div class="msg">
                    <h3>予約情報の読み込みが完了しました！</h3>
                </div>     
                <div class="action-buttons">
                    <button type="button" class="btn-back" onclick="location.href='res_msg_manager.jsp'">予約情報画面へ戻る</button>
                </div>
            </div>
        </div>
        
    </main>
    
</body>
</html>