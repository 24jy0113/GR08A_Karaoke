<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!doctype html>
<html lang="ja">

<head>
  <meta charset="utf-8" />
  <title>商品一覧画面</title>
  <link rel="stylesheet" type="text/css" href="<%= request.getContextPath() %>/css/06_02.css">
  <link rel="stylesheet" type="text/css" href="<%= request.getContextPath() %>/css/header.css">
</head>

<body>
  <!-- Header -->
  <header>
    <div class="header_inner">
      <img class="title_img" src="<%= request.getContextPath() %>/img/logo.png" alt="logo" width="60" height="60">
            <h1 class="title_name">七福サウンド</h1>
            <nav class="gnav">
                <ul class="gnav_list">
                    <li><a href="<%= request.getContextPath() %>/cus_top.jsp">トップへ</a></li>
                    <li><a href="<%= request.getContextPath() %>/time_extend.jsp">延長申請</a></li>
                    <li><a href="<%= request.getContextPath() %>/item_search.jsp">メニューを番号で探す</a></li>
                    <li><a href="<%= request.getContextPath() %>/item_list.jsp">フード・ドリンク</a></li>
                    <li><a href="<%= request.getContextPath() %>/cus_purchase_history.jsp">注文履歴</a></li>
                    <li><a class="gnav_botton" href="cart_detail.jsp">
                            <img class="cart_img" src="<%= request.getContextPath() %>/img/cart.png" alt="cart" width="20" height="20">カート内容を確認
                        </a>
                    </li>
                </ul>
            </nav>
    </div>
  </header>
  <main>
    <!-- ▼ カテゴリタブ -->
    <div class="tabs">
      <div class="tab active">アルコール</div>
      <div class="tab">ソフトドリンク</div>
      <div class="tab">フードメニュー</div>
      <div class="tab">サイドメニュー</div>
      <div class="tab">デザート</div>
    </div>
    <!-- ▼ メニュー表示部分 -->
    <div class="wrapper">
      <div class="arrow">❮</div>
      <div class="grid">
        <div class="item">
          <a href="item_detail.jsp">
            <div class="item-img">🍸</div>
            <div class="item-name">ドリンク</div>
            <div class="item-price">600円(税込)</div>
          </a>
        </div>
        <div class="item">
          <a href="item_detail.jsp">
            <div class="item-img">🍸</div>
            <div class="item-name">ドリンク</div>
            <div class="item-price">600円(税込)</div>
          </a>
        </div>
        <div class="item">
          <a href="item_detail.jsp">
            <div class="item-img">🍸</div>
            <div class="item-name">ドリンク</div>
            <div class="item-price">600円(税込)</div>
          </a>
        </div>
        <div class="item">
          <a href="item_detail.jsp">
          <div class="item-img">🍸</div>
          <div class="item-name">ドリンク</div>
          <div class="item-price">600円(税込)</div>
          </a>
        </div>
        <div class="item">
          <a href="item_detail.jsp">
          <div class="item-img">🍸</div>
          <div class="item-name">ドリンク</div>
          <div class="item-price">600円(税込)</div>
          </a>
        </div>
        <div class="item">
          <a href="item_detail.jsp">
            <div class="item-img">🍸</div>
            <div class="item-name">ドリンク</div>
            <div class="item-price">600円(税込)</div>
          </a>
        </div>
      </div>
      <div class="arrow">❯</div>
    </div>
    <div class="footer-wrap">
      <h1>部屋番号　101</h1>
      <h1>残り時間　50分</h1>
    </div>
  </main>
</body>

</html>