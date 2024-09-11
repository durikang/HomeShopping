<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>카테고리 목록 페이지</title>
<script type="text/javascript">
/**
 * 
 */
 
 // JavaScript 함수: 클릭한 행의 ID를 가져와서 상세 페이지로 이동
function goToDetailPage(event) {
    const target = event.currentTarget;
    const No = target.getAttribute('data-id');
    if (No) {
        /* window.location.href = '${contextPath}/detail.do?no=' + userNo + '&status='+${status}+'&currentPage='+${pi.currentPage}; */
        window.location.href = '${contextPath}/productCategoryContent.do?no=' + No + '&status=' + '${status}' + '&currentPage=' + '${pi.currentPage}';
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
		<hr width="50%" color="red">
		<h3>카테고리 목록 페이지</h3>
		<hr width="50%" color="red">
		<br> <br>


		<table border="1" width="650">
			<tr>

				<th>카테고리 번호</th>
				<th>카테고리 목록</th>
				<th>카테고리 정보</th>

			</tr>
			<c:set var="list" value="${List }" />
			<c:if test="${!empty list }">
				<c:forEach items="${list }" var="dto">
					<tr data-id=${dto.getCategory_No() }>
						<td>${dto.getCategory_No() }</td>
						<td>${dto.getName() }</td>
						<td>${dto.getDescription() }</td>
					</tr>
				</c:forEach>
			</c:if>
			<c:if test="${empty list }">
				<tr>
					<td colspan="3" align="center">
						<h3>카테고리가 없습니다.</h3>
					</td>
				</tr>
			</c:if>
			<tr>
				<td colspan="3" align="center">
					<input type="button" value="카테고리 등록" onclick="location.href='productCategoryInsert.go'">
				</td>
			</tr>

		</table>
	</div>
</body>
</html>