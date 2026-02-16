<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<link rel="stylesheet" type="text/css"
	href="<%=request.getContextPath()%>/css/common.css">

<!-- ===== 通知モーダル ===== -->
<div id="noticeModal" class="hidden">
	<div class="notice-modal-content">
		<p id="noticeText"></p>
		<small id="noticeNote"> （残り時間及び通知は多少前後することがあります） </small>
		<button onclick="closeNotice()">確認</button>
	</div>
</div>

<!-- 通知＆時間表示のJavascript -->
<script>const CONTEXT_PATH = "<%=request.getContextPath()%>
	";
</script>
<script src="<%=request.getContextPath()%>/js/notice.js"></script>

