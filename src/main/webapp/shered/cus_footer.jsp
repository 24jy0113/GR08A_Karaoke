<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<footer class="footer-wrap">
		<h1>部屋番号${room.getRoomNo() }</h1>
		<c:if test="${isStaffAction }">
			<button type="button" class="btn-back"
				onclick="location.href='<%=request.getContextPath()%>/RoomListServlet'">部屋一覧画面へ戻る</button>
		</c:if>
		<c:if test="${remainingMinutes != null }">
			<h1>
				残り時間 <span id="remainingTime">--:--</span>
			</h1>
		</c:if>
	<jsp:include page="common.jsp" />
</footer>