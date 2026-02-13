<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"
    import="model.*"
%>
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
    <title>予約情報取得画面</title>
    <link rel="stylesheet" type="text/css" href="<%= request.getContextPath() %>/css/10_03.css">
    <link rel="stylesheet" type="text/css" href="<%= request.getContextPath() %>/css/header.css">
</head>

<body>
    <!-- Header -->
    <%@ include file="/shered/biz_header.jsp" %>
    <main>
        <div class="bodymsg">
            <div>
                <form action="<%= request.getContextPath() %>/ResMsgUploadServlet" method="post" enctype="multipart/form-data">

	                <div class="msg">
	                    <h2>予約情報取得</h2>
	                    <h3>読み込む予約情報のデータをアップロードしてください</h3>
	                </div>
	
	                <div class="input-row">
	                    <label>予約データ</label>
	
	                    <label class="csv-box">
	                        CSV
	                        <input type="file" name="csvFile" class="hidden-file" required>
	                    </label>

	                </div>
	                <div class="action-buttons">
	                    <button type="button" class="btn-back" onclick="location.href='<%= request.getContextPath() %>/ResListManagerServlet'">予約情報画面へ戻る</button>
	                    <button type="submit" class="btn-next">予約情報を取得する</button>
	                </div>
                </form>
            </div>
        </div>
    </main>
    
</body>
</html>