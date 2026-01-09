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
                    <li><a href="03_01顧客側トップ画面.html">トップへ</a></li>
                    <li><a href="04_01_01延長時間選択画面.html">延長申請</a></li>
                    <li><a href="item_search.jsp">メニューを番号で探す</a></li>
                    <li><a href="item_list.jsp">フード・ドリンク</a></li>
                    <li><a href="05_01注文履歴一覧画面.html">注文履歴</a></li>
                    <li><a class="gnav_botton" href="07_01カート内容画面.html">
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
                        <button style="color: white; background-color: black;" onclick="location.href='item_option_pick.jsp'">カートに入れる</button>
                        <button onclick="location.href='item_list.jsp'">メニュー一覧に戻る</button>
                        <button onclick="location.href='item_search.jsp'">メニューを番号で探す</button>
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