<%@page import="com.global.board.model.BoardDTO"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"
	import="java.util.ArrayList,java.util.List ,com.global.board.model.*"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>게시판 리스트</title>

<script type="text/javascript">
 
 // JavaScript 함수: 클릭한 행의 ID를 가져와서 상세 페이지로 이동
function goToDetailPage(event) {
    const target = event.currentTarget;
    const userNo = target.getAttribute('data-id');
    if (userNo) {
        /* window.location.href = '${contextPath}/detail.do?no=' + userNo + '&status='+${status}+'&currentPage='+${pi.currentPage}; */
        window.location.href = '${contextPath}/customerDetail.do?no=' + userNo + '&status=' + '${status}' + '&currentPage=' + '${pi.currentPage}';
    }
}

// 모든 행에 클릭 이벤트 리스너 추가
document.addEventListener('DOMContentLoaded', function() {
    const rows = document.querySelectorAll('table tr[data-id]');

    rows.forEach(row => {
        row.addEventListener('click', goToDetailPage);
    });
});
</script>

</head>
<body>

	<div align="center">
		<h3>게시판 전체 리스트</h3>
		<div class="spacer"></div>
		<!-- 공백 추가 -->
		<table border="1">
			<tr>
				<td class="table_header button" colspan="9" align="right">전체 게시글 수
					수 : ${count}</td>
			</tr>
			<tr>
				<th>BoardNo.</th>
				<th>UserNo.</th>
				<th>CategoryNo</th>
				<th>제목</th>
				<th>내용</th>
				<th>작성일</th>
				<th>수정일</th>
				<th>상태</th>
			</tr>
			<c:choose>
				<c:when test="${not empty list}">
					<c:forEach items="${list}" var="board">
						<tr class="trlist" data-id="${board.userNo}">
							<td>${board.boardNo}</td>
							<td>${board.userNo}</td>
							<td>${board.categoryNo}</td>
							<td>${board.title}</td>
							<td>${board.content}</td>
							<td><fmt:formatDate value="${board.createAt }"/></td>
							<td><fmt:formatDate value="${board.updateAt }" /></td>
							<td><c:choose>
									<c:when test="${board.isDeleted == 'N'}">
										<c:set var="status" value="정상" />
									</c:when>
									<c:otherwise>
										<c:set var="status" value="삭제됨" />
									</c:otherwise>
								</c:choose> <c:out value="${status}" /></td>
						</tr>
					</c:forEach>
				</c:when>
				<c:otherwise>
					<tr>
						<td colspan="10" align="center">
							<h3>게시판 데이터가 없습니다.</h3>
						</td>
					</tr>
				</c:otherwise>
			</c:choose>
			<tr>
				<td class="table_bottom button" colspan="9" align="center">
					<input type="button" class="btn" value="게시글 작성" onclick="location.href='boardInsert.do'">
				</td>
			</tr>
		</table>
	</div>
</body>
</html>
