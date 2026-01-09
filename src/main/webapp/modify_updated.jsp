<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang=ja>

<head>
    <meta charset="UTF-8">
    <title>商品更新完了画面-管理者</title>
    <link rel="stylesheet" type="text/css" href="./css/08_05.css">
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
            <div class="msg">
                <h2>商品情報更新完了</h2>
                <p>下記の内容で商品情報を更新しました</p>
                <table>
                    <tr>
                        <th>商品名</th>
                        <td>サラダ</td>
                    </tr>
                    <tr>
                        <th>価格(税込)</th>
                        <td>300円（税込）</td>
                    </tr>
                    <tr>
                        <th>商品画像</th>
                        <td><img src="./img/salad.png" alt="salad" width="30px" height="30px"></td>
                    </tr>
                    <tr>
                        <th>注文番号</th>
                        <td>001</td>
                    </tr>
                    <tr>
                        <th>カテゴリー</th>
                        <td>フードメニュー</td>
                    </tr>
                    <tr>
                        <th>在庫</th>
                        <td>なし</td>
                    </tr>
                    <tr>
                        <th>オプション</th>
                        <td>なし</td>
                    </tr>
                </table>

                <div class="action-buttons">

                    <button type="button" class="btn-back"
                        onclick="location.href='manage_top.jsp'">表示選択画面へ戻る</button>
                    <button type="submit" class="btn-next"
                        onclick="location.href='modify_search.jsp'">商品検索画面へ戻る</button>
                </div>
            </div>

        </div>
    </main>

</body>

</html>