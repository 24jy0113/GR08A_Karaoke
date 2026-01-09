<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang=ja>
<head>
	<meta charset="UTF-8">
	<title>表示選択画面ーフロントのトップ画面</title>
	<link rel="stylesheet" type="text/css" href="./css/09_01.css">
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
		<div>
			<p class="bodymsg">利用する項目を選択してください</p>
			<div class="container">
				<a class="button" href="room_list.jsp">部屋状況</a>
				<a class="button" href="res_list_front.jsp">予約情報</a>
				
			</div>
			<div class="container">
				<a class="button" href="front_order_list.jsp">伝票一覧</a>
				<a class="button" href="front_room_search.jsp">部屋番号</a>
			</div>
		</div>
	</main>
	
</body>
</html>