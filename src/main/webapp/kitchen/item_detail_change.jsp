<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang=ja>
<head>
    <meta charset="UTF-8">
    <title>商品詳細情報画面</title>
    <link rel="stylesheet" type="text/css" href="./css/06_03.css">
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
                    <li><a href="cus_top.jsp">トップへ</a></li>
                    <li><a href="time_extend.jsp">延長申請</a></li>
                    <li><a href="item_search.jsp">メニューを番号で探す</a></li>
                    <li><a href="item_list.jsp">フード・ドリンク</a></li>
                    <li><a href="cus_purchase_history.jsp">注文履歴</a></li>
                    <li><a class="gnav_botton" href="cart_detail.jsp">
                            <img class="cart_img" src="./img/cart.png" alt="cart" width="20" height="20">カート内容を確認
                        </a>
                    </li>
                </ul>
            </nav>
        </div>
    </header>
    <main>
        <div class="bodymsg">
            <div class="msg">
                <h1>生ビール</h1>
                <h2>600円(税込)</h2>
            </div>
            <div class="container">
                <!-- 左側：画像領域 -->
                <div>
                    <div class="left-box">画像</div>
                   
                </div>
                <!-- 右側：検索欄＋テンキー -->
                <div class="right-box">  
                    <div class="pad">
                        <button style="color: white; background-color: black;" onclick="location.href='07_04オプション選択-変更時.html'">変更する</button>
                        <button onclick="location.href='cart_detail.jsp'">カートに戻る</button>
                        
                    </div>
                </div>
            </div>
            
        </div>
        <div class="footer-wrap">
            <h1>部屋番号　101</h1>
            <h1>残り時間　50分</h1>
        </div>
    </main>
    
</body>
</html>