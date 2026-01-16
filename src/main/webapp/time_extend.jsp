<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="ja">
<head>
    <meta charset="UTF-8">
    <title>延長時間選択画面</title>
    <link rel="stylesheet" type="text/css" href="./css/04_01.css">
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
                <h2>延長が可能です！</h2>
                <h2>ご希望の延長時間を選択し、確認ボタンを押してください</h2>
                <table>
                    <tr>
                        <th>部屋番号</th>
                        <th>受付時間</th>
                        <th>退室時間</th>
                        <th>延長（分）</th>
                    </tr>
                    <!-- 1行目 -->
                    <tr>
                        <td>101</td>
                        <td>10:30 </td>
                        <td>11:30</td>
                        <td><!-- 延長（分） -->
                            <div class="block">
                                <select name="extend">
                                    <option value="30" selected>30 分</option>
                                    <option value="60">60 分</option>
                                    <option value="90">90 分</option>
                                    <option value="120">120 分</option>
                                    <option value="150">150 分</option>
                                    <option value="180">180 分</option>
                                </select>
                            </div>
                        <!-- ＊料金設定詳細 -->
                        <div class="small-text">
                            ＊<a href="time_extend_fee.jsp">料金設定詳細</a>
                        </div>
                        </td>
                        
                    </tr>
                </table>
                <div class="action-buttons">
                    <button type="button" class="btn-back" onclick="location.href='cus_top.jsp'">トップページへ戻る</button>
                    <button type="submit" class="btn-next" onclick="location.href='time_extend_confirm.jsp'">確認する</button>
                </div>
            </div>
        </div>
        <div class="footer-wrap"><h1>部屋番号　101</h1><h1>残り時間　10分</h1></div>
    </main>
    
</body>
</html>