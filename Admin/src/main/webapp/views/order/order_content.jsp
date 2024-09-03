<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>  
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
		<div align="center">
		<c:set var="dto" value="${Content }"></c:set>
		
		<h3>주문 목록 상세 페이지</h3>
		<table border="1">
			<tr>
				<th>주문아이템 번호</th>
				<th>주문 번호</th>
				<th>카테고리 번호</th>
				<th>주문 갯수</th>
				<th>개당 금액</th>
			</tr>

			<c:if test="${not empty dto }">
				<tr>
					<td>${dto.order_item_no }</td>
					<td>${dto.oreder_no }</td>
					<td>${dto.product_no }</td>
					<td>${dto.quantity }</td>
					<td>${dto.price }</td>
				</tr>
			</c:if>
			<c:if test="${empty dto}">
				<tr>
					<td colspan="5" align="center">
						<h3>상세 목록이 없습니다.</h3>
					</td>
				</tr>
			</c:if>

		</table>
		<br>
			<%-- <input type="button" value="주문목록삭제"
	        onclick="location.href='orderitem_delete.do?no=${dto.getOreder_no()}'"> --%>
	</div>

</body>
</html>