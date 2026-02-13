<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" import="java.util.*,model.*"%>
<%
int index = Integer.parseInt(request.getParameter("index"));
ArrayList<OrderItem> cart =
	(ArrayList<OrderItem>) session.getAttribute("cart");
OrderItem oi = cart.get(index);
Item item = oi.getItem();
%>
<%
Room room = (Room) session.getAttribute("room");
Integer remainingMinutes = (Integer) session.getAttribute("remainingMinutes");
%>
<!DOCTYPE html>
<html lang=ja>
<head>
    <meta charset="UTF-8">
    <title>商品内容変更</title>
    <link rel="stylesheet" type="text/css" href="<%= request.getContextPath() %>/css/06_03.css">
    <link rel="stylesheet" type="text/css" href="<%= request.getContextPath() %>/css/header.css">
</head>
<body>
    <!-- Header -->
    <%@ include file="/shered/cus_header.jsp" %>
    <main class="bodymsg">

            <div class="container">
                <!-- 左側：画像領域 -->
                <div>
                    <div class="left-box"><img class="item-img" src="<%=request.getContextPath()%>/img/<%=item.getImage()%>" alt="product"></div>
                    <h2><%= item.getPrice() %>　円(税込)</h2>
                </div>
                <!-- 右側：検索欄＋テンキー -->
                <div class="right-box">  
                    <div class="pad">
                    <form action="CartItemUpdateServlet" method="post">
						<input type="hidden" name="index" value="<%= index %>">
					
						<h3><%= item.getName() %></h3>
					
						<% for (Option opt : item.getOptionList()) {
							OrderItem.SelectedOption so =
								oi.findSelectedOptionById(opt.getId());
						%>
						<p><strong><%= opt.getName() %></strong></p>
					
						<% for (Option.Selection sel : opt.getSelectionList()) { %>
						<label>
							<input type="radio"
							       name="opt_<%= opt.getId() %>"
							       value="<%= sel.id() %>"
							       <%= (so != null && so.selectionId() == sel.id()) ? "checked" : "" %>
							       required>
							<%= sel.name() %>（+<%= sel.price() %>円）
						</label><br>
						<% } %>
					
						<% } %>
					
						<h3>数量</h3>
						<input type="number" name="count" min="1"
						       value="<%= oi.getCount() %>" required>
					
						<br><br>
                        <button style="color: white; background-color: black;" type="submit">変更する</button>
                        <button onclick="history.back()">カートに戻る</button>
                      </form>  
                    </div>
                </div>
            </div>
            
    </main>
    <%@ include file="/shered/cus_footer.jsp" %>
    
</body>
</html>