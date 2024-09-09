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
		<h3>[${user.getName() }]의 임시 마이페이지</h3>
		
		<table border = "1" width = "auto">
			<c:set var="buyList" value="${list }"/>
			<tr>
				<td>주문번호</td><td>회원번호</td><td>주문날짜</td>
				<td>주문상태</td><td>총 주문 갯수</td><td>리뷰</td>
			</tr>
			<c:if test="${!empty buyList }">
				<c:forEach items="${buyList }" var ="dto">
				<tr>
					<td>${dto.getOrder_no() }</td>
					<td>${dto.getUser_no() }</td>
					<td>${dto.getOrder_date() }</td>
					<td>${dto.getStatus() }</td>
					<td>${dto.getTotal_amount() }</td>
					<c:if test="${dto.getStatus().equals('DELIVERED') }">
					<td>리뷰작성</td>
					</c:if>
					<c:if test="${!dto.getStatus().equals('DELIVERED') }">
					<td>리뷰 작성 불가능</td>
					</c:if>
				</tr>
				</c:forEach>
			</c:if>
			
		</table>
		
	</div>
</body>
</html>