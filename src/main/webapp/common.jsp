<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<!-- ===== 通知モーダル ===== -->
<div id="noticeModal" class="hidden">
	<div class="notice-modal-content">
		<p id="noticeText"></p>
		<small id="noticeNote"> （残り時間及び通知は多少前後することがあります） </small>
		<button onclick="closeNotice()">確認</button>
	</div>
</div>

<!-- ===== 通知用JavaScript ===== -->
<script src="<%=request.getContextPath()%>/js/notice.js"></script>

<!-- ===== 簡易CSS（なければ最低限） ===== -->
<style>
.hidden {
	display: none;
}

#noticeModal {
	position: fixed;
	top: 0;
	left: 0;
	width: 100%;
	height: 100%;
	background: rgba(0, 0, 0, 0.5);
	z-index: 9999;
}

.notice-modal-content {
	background: #fff;
	width: 300px;
	margin: 30% auto;
	padding: 20px;
	text-align: center;
	border-radius: 8px;
}

#noticeNote {
	display: none;
	color: #555;
	font-size: 0.8em;
}
</style>
