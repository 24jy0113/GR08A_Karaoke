<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8" import="model.*"%>
<%@ taglib prefix="c" uri="jakarta.tags.core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
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
<link rel="stylesheet" type="text/css"
	href="<%=request.getContextPath()%>/css/header.css">
<link rel="stylesheet" type="text/css"
	href="<%=request.getContextPath()%>/css/default.css">
<link rel="stylesheet" type="text/css"
	href="<%=request.getContextPath()%>/css/10.css">
</head>
<body>
	<%@ include file="/shared/biz_header.jsp"%>
	<main class="container-base">
		<div class="text-center">
			<h2 class="bodytitle">予約一覧</h2>
			<form method="get"
				action="<%=request.getContextPath()%>/ResListFrontServlet"
				class="block">
				<label>部屋番号<input type="text" name="room_num"></label>
				<button type="submit" class="btn-filter">絞り込み</button>
			</form>

			<c:if test="${not empty error}">
			    <div style="color: red; margin: 10px 0; padding: 10px;">
			        <p>${fn:escapeXml(error)}</p>
			    </div>
			</c:if>
			<c:if test="${not empty errors}">
			    <div style="color: red; margin: 10px 0; padding: 10px; border: 1px solid red;">
			        <c:forEach var="err" items="${errors}">
			            <p>${fn:escapeXml(err)}</p>
			        </c:forEach>
			    </div>
			</c:if>

			<form method="post"
				action="<%=request.getContextPath()%>/ResListUpdateServlet">
				<table>
					<tr>
						<th>予約番号</th>
						<th>部屋番号</th>
						<th>日付</th>
						<th>予約受付時間</th>
						<th>予約退室時間</th>
						<th>状態</th>
						<th>操作</th>
					</tr>
					<c:forEach var="r" items="${reservationList}" varStatus="idx">
						<tr>
							<td>${r.reservationNumber}<input type="hidden"
								name="reservationNumber" value="${r.reservationNumber}">
							</td>
							<td>${r.roomNumber}</td>
							<td>${r.date}</td>
							<td>
								<input type="time" name="startTime" id="start_${idx.index}"
									value="${fn:substring(r.receptionTime,0,5)}"
									onchange="onStartTimeChange(${idx.index})">
							</td>
							<td>
								<select name="endTime" id="end_${idx.index}"
							            data-current="${fn:substring(r.leavingTime,0,5)}">
							    </select>
							</td>
							<td>${r.statusName}</td>
							<td>
								<button type="button" class="btn-cancel"
									onclick="if(confirm('予約番号${r.reservationNumber}をキャンセルしますか？')){
										var f=document.createElement('form');
										f.method='post';
										f.action='<%=request.getContextPath()%>/ResCancelServlet';
										var inp=document.createElement('input');
										inp.type='hidden';inp.name='reservationNumber';inp.value='${r.reservationNumber}';
										f.appendChild(inp);document.body.appendChild(f);f.submit();
									}">キャンセル</button>
							</td>
						</tr>
					</c:forEach>
				</table>
				<div class="action-buttons flex-center">
					<button type="button" class="btn-back"
						onclick="location.href='<%=request.getContextPath()%>/front/front_top.jsp'">表示選択画面へ</button>
					<button type="submit" class="btn-next">変更の確定</button>
				</div>
			</form>
		</div>
	</main>
<script>
/**
 * 受付時間変更時 → 退室時間selectを再生成
 * 退室時間 = 受付時間 + 1:00, +1:30, +2:00, +2:30 ...
 * 例: 14:05 → 15:05, 15:35, 16:05, 16:35 ...
 */
function onStartTimeChange(index) {
    var startInput = document.getElementById('start_' + index);
    var endSelect  = document.getElementById('end_'   + index);
    var startVal   = startInput.value;
    if (!startVal) return;

    var parts = startVal.split(':');
    var startMinutes = parseInt(parts[0]) * 60 + parseInt(parts[1]);

    // 30分丸めは行わない（自由入力のまま）
    buildEndOptions(endSelect, startMinutes, null);
}

/**
 * 退室時間の選択肢を生成
 * 受付時間 + 60分 から30分刻み、23:59まで
 */
function buildEndOptions(endSelect, startMin, currentEnd) {
    endSelect.innerHTML = '';

    var minEnd = startMin + 60;  // 最低1時間後
    var maxEnd = 24 * 60;        // 24:00

    var selected = false;
    for (var m = minEnd; m <= maxEnd; m += 30) {
        var label = minutesToHHmm(m);
        var opt = document.createElement('option');
        opt.value = label;
        opt.textContent = label;

        if (currentEnd && label === currentEnd) {
            opt.selected = true;
            selected = true;
        }
        endSelect.appendChild(opt);
    }

    if (!selected && endSelect.options.length > 0) {
        endSelect.options[0].selected = true;
    }
}

function minutesToHHmm(totalMinutes) {
    var h = Math.floor(totalMinutes / 60);
    var m = totalMinutes % 60;
    return String(h).padStart(2, '0') + ':' + String(m).padStart(2, '0');
}

/**
 * ページ読み込み時：全行の退室時間selectを初期化
 */
window.addEventListener('DOMContentLoaded', function() {
    var index = 0;
    while (true) {
        var startInput = document.getElementById('start_' + index);
        var endSelect  = document.getElementById('end_'   + index);
        if (!startInput || !endSelect) break;

        var startVal = startInput.value;
        if (startVal) {
            var parts = startVal.split(':');
            var startMinutes = parseInt(parts[0]) * 60 + parseInt(parts[1]);
            var currentEnd = endSelect.getAttribute('data-current');
            buildEndOptions(endSelect, startMinutes, currentEnd);
        }
        index++;
    }
});
</script>

</body>
</html>