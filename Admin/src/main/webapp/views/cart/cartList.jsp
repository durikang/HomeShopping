<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
	<div align="center">
		<hr width="50%" color="red">
			<h3>장바구니 전체리스트</h3>
		<hr width="50%" color="red">
		<br><br>
		<c:set var="list" value="CartList"></c:set>
		<table border="1" width="300">
		
			<tr>
				<th>장바구니 No.</th>
				<th>유저이름</th>
				<th>장바구니 생성일</th>
			</tr>
			
			<c:if test="${not empty list }">
				<c:forEach  items="${list }" var="dto" >
					<tr>
						<td></td>
						<td></td>
						<td></td>
					</tr>
				</c:forEach>
			</c:if>
				<c:if test="${empty list }">
				<tr>
					<td colspan="3" align="center">
						<h3>장바구니가 비어있습니다.</h3>
					</td>
					</tr>
				</c:if>
			
		</table>
		
	</div>
</body>
</html>