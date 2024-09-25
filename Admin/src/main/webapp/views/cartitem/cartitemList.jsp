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
		<c:set var="cartitem" value="${CartItemList }"/>
		<table>
		<tr>
				<th>순번</th> <th>상품번호</th> <th>상품명</th>
				<th>수량</th> <th>가격</th> <th>수정  /  삭제</th>
			</tr>
			<c:if test="${!empty cartitem}">
			<c:forEach var ="item" items="${ cartitem }">
				<tr>
					<td>${ item.cartItem_no }</td>
					<td>${ item.product_no }</td>
					<td>${ item.product_name }</td>
					<td><fmt:formatNumber value="${ item.quantity }"/>개 </td>
					<td><fmt:formatNumber value="${ item.product_price }"/>원 </td>
					<td class="table_bottom button" >
						<input type="button" class="btn" value="수정" onclick="location.href='cartItem_modify.do?no=${item.cartItem_no}'">&nbsp;&nbsp;&nbsp;&nbsp;
						<input type="button" class="btn" value="삭제" onclick="if(confirm('정말로 게시글을 삭제하시겠습니까?')){
											location.href='cartItem_delete.do?no=${item.cartItem_no}'} else{return;}">
					</td>
				</tr>
			</c:forEach>
			</c:if>
			<c:if test="${empty  cartitem }">
				<tr>
					<td colspan="7" align="center">
						<h3>장바구니가 비어있습니다.</h3>
					</td>
					</tr>
				</c:if>
		</table>	
			<br><br>
	</div>
</body>
</html>