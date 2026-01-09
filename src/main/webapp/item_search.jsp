<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang=ja>
<head>
    <meta charset="UTF-8">
    <title>メニュー番号入力</title>
    <link rel="stylesheet" type="text/css" href="./css/06_01.css">
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
                    <li><a href="front_cus_top.jsp">トップへ</a></li>
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
                <h1>メニュー番号で商品を探す</h1>
            </div>
            <p>メニュー番号を入力してください</p>
            <div class="container">
                <!-- 左側：画像領域 -->
                <div>
                    <div class="left-box">画像</div>
                    <button class="back-btn" onclick="location.href='front_cus_top.jsp'">トップページへ</button>
                </div>
                <!-- 右側：検索欄＋テンキー -->
                <div class="right-box">
                    <div class="input-row">
                        <input id="menuInput" type="text" maxlength="4" readonly/>
                        <button id="searchBtn" onclick="location.href='item_detail.jsp'">検索</button>
                    </div>
                    <div class="pad">
                        <button>1</button>
                        <button>2</button>
                        <button>3</button>
                        <button>4</button>
                        <button>5</button>
                        <button>6</button>
                        <button>7</button>
                        <button>8</button>
                        <button>9</button>
                        <button id="clear">消</button>
                        <button>0</button>
                    </div>
                </div>
            </div>
            <script>
                const input = document.getElementById('menuInput');
                const padButtons = document.querySelectorAll('.pad button');
                
                padButtons.forEach(btn => {
                    btn.addEventListener('click', () => {
                        const val = btn.textContent;
                        if (val === '消') { input.value = input.value.slice(0, -1); return; }
                        if (input.value.length < 4) input.value += val;
                    });
                });
            </script>
        </div>
        <div class="footer-wrap">
            <h1>部屋番号　101</h1>
            <h1>残り時間　50分</h1>
        </div>
    </main>
    
</body>
</html>