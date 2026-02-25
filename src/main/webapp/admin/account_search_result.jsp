<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" %>
<%@ page import="java.util.*, model.User" %>

<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="fmt" uri="jakarta.tags.fmt" %>

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
<title>アカウント情報一覧画面</title>

<link rel="stylesheet" type="text/css"
    href="<%=request.getContextPath()%>/css/header.css">
<link rel="stylesheet" type="text/css"
    href="<%=request.getContextPath()%>/css/default.css">
<link rel="stylesheet" type="text/css"
    href="<%=request.getContextPath()%>/css/01_05.css">
</head>

<body>
<%@ include file="/shared/biz_header.jsp"%>

<main class="container">
<div class="text-center">
<h2 class="bodytitle">検索結果</h2>

<c:choose>
    <c:when test="${empty userList}">
        <p>検索結果はありません。</p>
    </c:when>

    <c:otherwise>
        <table>
            <tr>
                <th>アカウントID</th>
                <th>アカウント名</th>
                <th>役割</th>
                <th>パスワード</th>
                <th>最終ログイン日時</th>
                <th></th>
                <th></th>
            </tr>

            <c:forEach var="u" items="${userList}">
                <tr>
                    <td>${u.userId}</td>
                    <td>${u.userName}</td>
                    <td>${u.roleName}</td>
                    <td>XXXXXXXX</td>

                    <td>
                        <c:choose>
                            <c:when test="${empty u.lastLoginTime}">
                                ログイン履歴がありません
                            </c:when>
                            <c:otherwise>
                                <fmt:formatDate
                                    value="${u.lastLoginTime}"
                                    pattern="yyyy/MM/dd HH:mm:ss" />
                            </c:otherwise>
                        </c:choose>
                    </td>

                    <td>
                        <form action="${pageContext.request.contextPath}/AccountUpdateInitServlet"
                              method="post">
                            <input type="hidden" name="userId" value="${u.userId}">
                            <button type="submit">変更</button>
                        </form>
                    </td>

                    <td>
                        <form action="${pageContext.request.contextPath}/AccountDeleteInitServlet"
                              method="post">
                            <input type="hidden" name="userId" value="${u.userId}">
                            <button class="btn delete" type="submit">削除</button>
                        </form>
                    </td>
                </tr>
            </c:forEach>
        </table>
    </c:otherwise>
</c:choose>

<div class="action-buttons flex-center">
    <button type="button" class="btn-back"
        onclick="location.href='${pageContext.request.contextPath}/admin/account_search.jsp'">
        アカウント検索へ戻る
    </button>
</div>

</div>
</main>
</body>
</html>