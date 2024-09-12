<%@page import="com.global.product.model.ProductDTO"%>
<%@ page import="com.global.utils.StringUtils"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"
	import="java.util.ArrayList,java.util.List ,com.global.product.model.*"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<link href="${contextPath }/resources/product/css/product_list.css"
	rel="stylesheet" type="text/css" />
<title>상품 목록 페이지</title>
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
        window.location.href = '${contextPath}/productContent.do?no=' + No + '&status=' + '${status}' + '&currentPage=' + '${pi.currentPage}';
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
		<h3>상품 목록 페이지</h3>
		<hr width="50%" color="red">
		<br> <br>


		<table border="1" width="650">
			<tr>
				<th>유저 번호</th>
				<th>상품 번호</th>
				<th>카테고리 번호</th>
				<th>상품 이름</th>
				<th>상품 이미지</th>
				<th>상품 정보</th>
				<th>상품 가격</th>
				<th>상품 재고</th>
				<th>상품 조회수</th>
				<th>상품 등록일</th>
				<th>상품 수정일</th>
				<th>상품 상태</th>
				<th>상품 판매량</th>

			</tr>
			<c:set var="list" value="${List }" />
			<c:if test="${!empty list }">
				<c:forEach items="${list }" var="dto">
					<tr data-id=${dto.getProduct_no() }>
						<td>${dto.getUser_no() }</td>
						<td>${dto.getProduct_no()}</td>
						<td>${dto.getCategory_no() }</td>
						<td>${dto.getName() }</td>
						<td>
							<img class="product_img" src="${contextPath }/${dto.getImage_url()}" />
						</td>
						<td>${dto.getDescription() }</td>
						<td><fmt:formatNumber value="${dto.getPrice() }" pattern="#,###" /></td>
						<td><fmt:formatNumber value="${dto.getStock_quantity() }" pattern="#,###" /></td>
						<td><fmt:formatNumber value="${dto.getViews() }" pattern="#,###" /></td>
						<td>${dto.getCreated_at() }</td>
						<td>${dto.getUpdated_at() }</td>
						<td>${dto.getIs_deleted() }</td>
						<td><fmt:formatNumber value="${dto.getTotal_sales() }" pattern="#,###" /></td>
					</tr>
				</c:forEach>
			</c:if>
			<c:if test="${empty list }">
				<tr>
					<td colspan="13" align="center">
						<h3>카테고리가 없습니다.</h3>
					</td>
				</tr>
			</c:if>
		</table>
	</div>
</body>
</html>
</body>
</html>