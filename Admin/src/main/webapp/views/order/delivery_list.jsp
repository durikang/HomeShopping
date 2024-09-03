<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
	<div align="center">
		<h3>배송 정보</h3>
		<table border="1" >
			<tr>
				<th>배송 번호</th>
				<th>주문 번호</th>
				<th>배송일</th>
				<th>배송 상태</th>
			</tr>

			<c:if test="${not empty List }">
				<c:forEach items="${ List }" var="dto">
					<tr>
						<td>${dto.getDelivery_no() }</td>
						<td>${dto.getOrder_no()}</td>
						<td>${dto.getDelivery_date()}</td>
						<td>${dto.getDelivery_status()}</td>
					</tr>
				</c:forEach>
			</c:if>
			<c:if test="${empty List }">
				<tr>
					<td colspan="4" align="center">
						<h3>배송 목록이 없습니다.</h3>
					</td>
				</tr>
			</c:if>

		</table>
	</div>

</body>
</html>