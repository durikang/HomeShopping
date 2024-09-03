<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
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
			<c:forEach var ="item" items="${ CartItemList }">
				<tr>
					<td>${ item.cartItem_cartNo }</td>
				</tr>
			</c:forEach>
		</table>
		
		
	</div>
</body>
</html>