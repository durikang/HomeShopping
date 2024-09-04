<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
	<div align="center">
		<h3>장바구니 목록</h3>
		<br><br>
		
		<table>
			<tr>
				<th>순번</th> <th>상품번호</th> <th>상품명</th>
				<th>수량</th> <th>가격</th> <th>수정</th> <th>삭제</th>
			</tr>
			<c:forEach var ="item" items="${ CartItemList }">
				<tr>
					<td>${ item.cartItem_no }</td>
					<td>${ item.cartItem_productNo }</td>
					<td>${ item.cartItem_productName }</td>
					<td><fmt:formatNumber value="${ item.cartItem_quantity }"/>개 </td>
					<td><fmt:formatNumber value="${ item.cartItem_productPrice }"/>원 </td>
					<td>
						<input type="button" value="수정" onclick="location.href='cartItem_update.do?no=${item.cartItem_no}'">
					</td>
					<td>
						<input type="button" value="삭제" onclick="location.href='cartItem_delete.do?no=${item.cartItem_no}'">
					</td>
				</tr>
			</c:forEach>
		</table>	
			<br><br>
	</div>
</body>
</html>