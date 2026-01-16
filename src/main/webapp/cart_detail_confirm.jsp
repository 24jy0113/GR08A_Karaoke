<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="ja">
<head>
    <meta charset="UTF-8">
    <title>カート内容確認画面</title>
    <link rel="stylesheet" type="text/css" href="./css/07_02.css">
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
            <div>
                <div class="msg">
                    <h2>ご注文の確認</h2>
                    <p>受け取り方法を選択し、注文内容に問題なければ「注文を確定する」を押してください</p>
                </div>
                <h3>受取方法</h3>
                <p>ご自身で受け取りに行くか、お部屋までお届けするか選択してください</p>
                <form method="post" action="cart_order_finished.jsp">
                    <input type="radio" id="self-pickup" name="pickup-method" value="self-pickup" checked>
                    <label for="self-pickup">ご自身で受け取りに行く</label><br>
                    <input type="radio" id="room-delivery" name="pickup-method" value="room-delivery">
                    <label for="room-delivery">お部屋までお届けする</label><br>
                <h3>カート内容</h3>
                <div class="cart-contents">
                    <table>
                        <th>サラダ</th>
                        <th>個数</th>
                        <th>小計</th>
                        </tr>
                        <!-- 1行目 -->
                        <tr>
                            <td>300円（税込）</td>
                            <td>1</td>
                            <td>300円（税込）</td>
                        </tr>
                        <tr>
                            <th>生ビール　M</th>
                            <th>個数</th>
                            <th>小計</th>
                        </tr>
                        <!-- 2行目 -->
                        <tr>
                            <td>300円（税込）</td>
                            <td>2</td>
                            <td>600円（税込）</td>
                        </tr>
                        <tr>
                            <th>チキン</th>
                            <th>個数</th>
                            <th>小計</th>
                        </tr>
                        <!-- 3行目 -->
                        <tr>
                            <td>1000円（税込）</td>
                            <td>1</td>
                            <td>1000円（税込）</td>
                        </tr>
                    </table>
                    
                </div>
                <section class="order-summary">
                    <div class="summary-row">
                        <div class="summary-label">商品合計金額:</div>
                        <div class="summary-value">1,960円(税込)</div>
                    </div>
                </section>
                <div class="action-buttons">
                    <button type="button" class="btn-back" onclick="location.href='cart_detail.jsp'">カート内容へ戻る</button>
                    <button type="submit" class="btn-next">注文を確定する</button>
                </div>
                </form>
            </div>
        </div>

        <div class="footer-wrap">
            <h1>部屋番号　101</h1>
            <h1>残り時間　50分</h1>
        </div>
    </main>
    
</body>
</html>