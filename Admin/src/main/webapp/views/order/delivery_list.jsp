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
				<th>설 정</th>
			</tr>

			<c:if test="${not empty List }">
				<c:forEach items="${ List }" var="dto">
					<tr>
						<td>${dto.getDelivery_no() }</td>
						<td>${dto.getOrder_no()}</td>
						<td>${dto.getDelivery_date()}</td>
						<c:if test="${dto.delivery_status eq 'PENDING'}">
						<td>배송 대기</td>
						</c:if>
						<c:if test="${dto.delivery_status eq 'SHIPPED'}">
							<td>배송 중</td>
						</c:if>
						<c:if test="${dto.delivery_status eq 'DELIVERED'}">
							<td>배송 완료</td>
						</c:if>
						<c:if test="${dto.delivery_status eq 'CANCELLED'}">
							<td>배송 취소</td>
						</c:if>
							<td>
						<c:if test="${sessionScope.user.userType == 'ADMIN'}">
							<input type="button" value="배송상태설정" onclick="location.href='orderitem_delete.do?no=${dto.oreder_no}'">
						</c:if>
					</td> 
						
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