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
		<h3>장바구니 전체리스트</h3>

		<br><br>
		
		<table width="300">
		
		<c:set var="list" value="${CartList }" />
			<tr>
				<th>장바구니 No.</th>
				<th>유저이름</th>
				<th>장바구니 생성일</th>
				<th>장바구니 수정</th>
				<th>장바구니 삭제</th>
			</tr>
			
			<c:if test="${!empty list }">
				<c:forEach  items="${list }" var="dto" >
					<tr>
						<td><a href="<%=request.getContextPath()%>/cartItem_list.do?no=${dto.getCart_userNo() }">${dto.getCart_no() }</a></td>
						<td>${dto.getCart_userNo() }</td>
						<td>
							<fmt:formatDate value="${dto.getCart_createdAt() }"/>
						</td>
						<td>
							<input type="button" value="수정" onclick="location.href='cartItem_update.do?no=${dto.getCart_userNo()}'">
						</td>
						<td>
							<input type="button" value="삭제" onclick="location.href='cartItem_delete.do?no=${dto.getCart_userNo()}'">
						</td>
					</tr>
				</c:forEach>
			</c:if>
				<c:if test="${empty list }">
				<tr>
					<td colspan="5" align="center">
						<h3>장바구니가 비어있습니다.</h3>
					</td>
					</tr>
				</c:if>
			
		</table>
		
	</div>
</body>
</html>