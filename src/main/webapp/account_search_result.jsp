<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="ja">
<head>
    <meta charset="UTF-8">
    <title>アカウント情報一覧画面</title>
    <link rel="stylesheet" type="text/css" href="./css/01_05.css">
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
                <h2>検索結果</h2>
                <table>
                    <tr>
                        <th>アカウントID</th>
                        <th>アカウント名</th>
                        <th>フロント</th>
                        <th>フロア</th>
                        <th>キッチン</th>
                        <th>パスワード</th>
                        <th>最終ログイン日時</th>
                        <th></th>
                    </tr>
                    <!-- 1行目 -->
                    <tr>
                        <td>SF0112</td>
                        <td>佐藤花子</td>
                        <td>O</td>
                        <td>X</td>
                        <td>O</td>
                        <td>asau122ea</td>
                        <td>2025/01/01/09:55</td>
                        <td>
                            <button class="btn edit" onclick="location.href='account_update.jsp'">変更</button>
                            <button class="btn delete" onclick="location.href='account_delete_notice.jsp'">削除</button>
                        </td>
                    </tr>
                    <!-- 2行目 -->
                    <tr>
                        <td>SF0113</td>
                        <td>山田太郎</td>
                        <td>X</td>
                        <td>X</td>
                        <td>O</td>
                        <td>qsxada2sa</td>
                        <td>2025/10/01/13:05</td>
                        <td>
                            <button class="btn edit" onclick="location.href='account_update.jsp'">変更</button>
                            <button class="btn delete" onclick="location.href='account_delete_notice.jsp'">削除</button>
                        </td>
                    </tr>
                </table>
                <div class="action-buttons">
                    <button type="button" class="btn-back" onclick="location.href='account_search.jsp'">アカウント検索へ戻る</button>
                </div>
            </div>
        </div>
    </main>
    
</body>
</html>