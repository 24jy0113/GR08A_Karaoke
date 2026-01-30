<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="java.util.*, model.Item" %>

<%
ArrayList<Item> itemList = (ArrayList<Item>)request.getAttribute("itemList");
%>
<%
int currentPage = (Integer)request.getAttribute("currentPage");
int totalPages = (Integer)request.getAttribute("totalPages");
Integer categoryId = (Integer)request.getAttribute("categoryId");

String catParam = categoryId != null ? "&category=" + categoryId : "";
%>
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
	    <a href="<%=request.getContextPath()%>/item_list?category=1" class="tab">アルコール</a>
		<a href="<%=request.getContextPath()%>/item_list?category=2" class="tab">ソフトドリンク</a>
		<a href="<%=request.getContextPath()%>/item_list?category=3" class="tab">フードメニュー</a>
		<a href="<%=request.getContextPath()%>/item_list?category=4" class="tab">サイドメニュー</a>
		<a href="<%=request.getContextPath()%>/item_list?category=5" class="tab">デザート</a>
	</div>
    <!-- ▼ メニュー表示部分 -->
    <div class="wrapper">
      <% if (currentPage > 1) { %>
		  <a class="arrow"
		     href="<%=request.getContextPath()%>/item_list?page=<%=currentPage-1%><%=catParam%>">
		     ❮
		  </a>
	 <% } else { %>
  		 <span class="arrow disabled">❮</span>
	 <% } %>

      <div class="grid">
      	<% if (itemList != null) {
   		for (Item item : itemList) { %>

  		<div class="item">
    		<a href="<%=request.getContextPath()%>/item_detail?id=<%=item.getId()%>">
      			<div class="item-img">
        			<img src="<%=request.getContextPath()%>/img/<%=item.getImage()%>" alt="productImage">
      			</div>
      			<div class="item-name"><%= item.getName() %></div>
      			<div class="item-price"><%= item.getPrice() %>円(税込)</div>
    		</a>
 		</div>

		<% } } %>
        
      </div>
      <span><%= currentPage %> / <%= totalPages %></span>
      <% if (currentPage < totalPages) { %>
		  <a class="arrow"
		     href="<%=request.getContextPath()%>/item_list?page=<%=currentPage+1%><%=catParam%>">
		     ❯
		  </a>
	  <% } else { %>
  		<span class="arrow disabled">❯</span>
	  <% } %>

    </div>
    <div class="footer-wrap">
      <h1>部屋番号　101</h1>
      <h1>残り時間　50分</h1>
    </div>
  </main>
  
</body>

</html>