<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="ja">
<head>
    <meta charset="UTF-8">
    <title>注文完了画面</title>
    <link rel="stylesheet" type="text/css" href="./css/07_01.css">
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
                <h2>ご注文の完了</h2>
                <p>ご注文いただき、誠にありがとうございました</p>
                <h1 style="color: rgb(17, 106, 223);">受取番号 : 0036</h1>
                <p>商品を受け取る際に、受取番号をスタッフにお伝えください</p>
                
                <div class="action-buttons">
                    
                    <button type="button" class="btn-back" onclick="location.href='cus_top.jsp'">トップページへ戻る</button>
        
                </div>
            </div>
           
        </div>
        
        <div class="footer-wrap"><h1>部屋番号　101</h1><h1>残り時間　50分</h1></div>
    </main>
    
    
</body>
</html>