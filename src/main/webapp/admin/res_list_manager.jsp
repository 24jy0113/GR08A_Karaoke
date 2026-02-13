<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"
    import="model.*"
%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<%
User user = (User) session.getAttribute("loginUser");
if (user == null) {
    response.sendRedirect(request.getContextPath() + "/index.jsp");
    return;
}
%>
<!DOCTYPE html>
<html lang="ja">
<head>
    <meta charset="UTF-8">
    <title>予約情報画面</title>
    <link rel="stylesheet" type="text/css" href="<%= request.getContextPath() %>/css/10_03.css">
    <link rel="stylesheet" type="text/css" href="<%= request.getContextPath() %>/css/header.css">
</head>

<body>
    <!-- Header -->
    <%@ include file="/shered/biz_header.jsp" %>
    <main>
        <div class="bodymsg">
            <div>
                <div></div>
                <h2>予約一覧</h2>
                <table>
                    <tr>
                        <th>予約番号</th>
                        <th>部屋番号</th>
                        <th>日付</th>
                        <th>予約受付時間</th>
                        <th>予約退室時間</th>

                    </tr>
                    <c:forEach var="r" items="${reservationList}">
					<tr>
					    <td>${r.reservationNumber}</td>
					    <td>${r.roomNumber}</td>
					    <td>${r.date}</td>
					    <td>${r.receptionTime}</td>
					    <td>${r.leavingTime}</td>
					</tr>
					</c:forEach>

                    
                </table>
                <div class="link">
                    <a href="">次のページへ</a>
                </div>
                <div class="action-buttons">
                    
                    <button type="button" class="btn-back" onclick="location.href='<%= request.getContextPath() %>/admin/manage_top.jsp'">表示選択画面へ</button>
                   
                   <button type="button" class="btn-next" onclick="location.href='<%= request.getContextPath() %>/admin/res_msg_upload.jsp'">予約情報読み込み</button> 
                    
                </div>
            </div>
        </div>
        
    </main>
    
</body>
</html>