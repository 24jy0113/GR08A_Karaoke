<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"
    import="model.*"
%>
<%@ taglib prefix="c" uri="jakarta.tags.core"%>

<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
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
    <link rel="stylesheet" type="text/css" href="<%= request.getContextPath() %>/css/10_01.css">
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
                <form method="get" action="<%= request.getContextPath() %>/ResListFrontServlet" class="block">
				    <select name="statusId">
				        <option value="">すべて</option>
				        <option value="1">空き</option>
				        <option value="2">予約</option>
				        <option value="3">受付済み</option>
				        <option value="4">会計済み</option>
				    </select>
				
				    <button type="submit">絞り込み</button>
				</form>
                <form method="post" action="<%= request.getContextPath() %>/ResListUpdateServlet">
					<table>
					<tr>
					  <th>予約番号</th>
					  <th>部屋番号</th>
					  <th>日付</th>
					  <th>予約受付時間</th>
					  <th>予約退室時間</th>
					  <th>状態</th>
					</tr>
					
					<c:forEach var="r" items="${reservationList}">
					<tr>
					  <td>
					    ${r.reservationNumber}
					    <input type="hidden" name="reservationNumber" value="${r.reservationNumber}">
					  </td>
					
					  <td>${r.roomNumber}</td>
					  <td>${r.date}</td>
					
					  <td>
					    <input type="time" name="startTime" value="${fn:substring(r.receptionTime,0,5)}">
					  </td>
					
					  <td>
					    <input type="time" name="endTime" value="${fn:substring(r.leavingTime,0,5)}">
					  </td>
					
					  <td>
					    <select name="statusId">
					      <option value="1" ${r.statusName=="空き"?"selected":""}>空き</option>
					      <option value="2" ${r.statusName=="予約"?"selected":""}>予約</option>
					      <option value="3" ${r.statusName=="受付済み"?"selected":""}>受付済み</option>
					      <option value="4" ${r.statusName=="会計済み"?"selected":""}>会計済み</option>
					    </select>
					  </td>
					</tr>
					</c:forEach>
					</table>

	                <div class="link">
	                    <a href="">次のページへ</a>
	                </div>
	  
	                <div class="action-buttons">  
	                    <button type="button" class="btn-back" onclick="location.href='<%= request.getContextPath() %>/front/front_top.jsp'">表示選択画面へ</button>
	                    <button type="submit" class="btn-next">変更の確定</button>  
	                </div>
                
                </form>
            </div>
        </div>
        
    </main>
    
</body>
</html>