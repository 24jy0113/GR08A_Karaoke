<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<jsp:include page="/shared/remaining-timer.jsp" />
<c:if test="${!isStaffAction }">
	<jsp:include page="/shared/sse-handler.jsp" />
</c:if>
<script src="<%=request.getContextPath()%>/js/staff-exit.js"></script>