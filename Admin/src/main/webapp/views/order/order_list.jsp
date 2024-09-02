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
		<h3>주문 목록</h3>
		<table border="1">
			<tr>
				<th>주문 번호</th>
				<th>주문한 고객</th>
				<th>주문 일자</th>
				<th>주문 상태</th>
				<th>총 주문 금액</th>
			</tr>

			<c:if test="${not empty List }">
				<c:forEach items="${ List }" var="dto">
					<tr>
						<td><a href="<%=request.getContextPath() %>/orderitemcontent.do?no=${dto.getOrder_no() }">${dto.getOrder_no()}</a></td>
						<td>${dto.getUser_no()}</td>
						<td>${dto.getOrder_date()}</td>
						<td>${dto.getStatus() }
						<td>${dto.getTotal_amount()}</td>
					</tr>
				</c:forEach>
			</c:if>
			<c:if test="${empty List }">
				<tr>
					<td colspan="5" align="center">
						<h3>상품 목록이 없습니다.</h3>
					</td>
				</tr>
			</c:if>

		</table>
	</div>


</body>
</html>