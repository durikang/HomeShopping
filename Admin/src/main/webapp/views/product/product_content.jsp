<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>    
<%
	request.setAttribute("newLine", "\n");
	request.setAttribute("br", "<br>");
%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
	<div align="center">
		<c:set var="dto" value="${Content }" />

		<hr width="30%" color="red">
		<h3>${dto.getName() } 상세내역 페이지</h3>
		<hr width="30%" color="red">
		<br>
		<br>

		<table border="1" width="400">
			<tr>
				<th>유저 번호</th>
				<td>${dto.getUser_no() }</td>
			</tr>

			<tr>
				<th>상품 번호</th>
				<td>${dto.getProduct_no() }</td>
			</tr>
		
			<tr>
				<th>카테고리 번호</th>
				<td>${dto.getCategory_no() }</td>
			</tr>

			<tr>
				<th>상품 이름</th>
				<td>${dto.getName() }</td>
			</tr>
			
			<tr>
				<th>상품 이미지</th>
				<td>
					<img src="${contextPath }/${dto.getImage_url()}">
				</td>
			</tr>
		
			
			<tr>
				<th>상품 정보</th>
				<td>${fn:replace(dto.getDescription(), newLine, br) }</td>
			</tr>
			
			<tr>
				<th>상품 가격</th>
				<td>${dto.getPrice() }</td>
			</tr>
			
			<tr>
				<th>상품 재고</th>
				<td>${dto.getStock_quantity() }</td>
			</tr>
		
			
			<tr>
				<th>상품 조회수</th>
				<td>${dto.getViews() }</td>
			</tr>
		
			
			<tr>
				<th>상품 등록일</th>
				<td>${dto.getCreated_at() }</td>
			</tr>
		
			
			<tr>
				<th>상품 수정일</th>
				<td>${dto.getUpdated_at() }</td>
			</tr>
		
			
			<tr>
				<th>상품 상태</th>
				<td>${dto.getIs_deleted() }</td>
			</tr>
		
			
			<tr>
				<th>상품 판매량</th>
				<td>${dto.getTotal_sales() }</td>
			</tr>
		
			
			
			
			<c:if test="${empty dto }">
				<tr>
					<td colspan="13" align="center">
						<h3>해당하는 게시글이 없습니다.</h3>
					</td>
				</tr>
			</c:if>
		</table>
		<br>
		
		<input type="button" value="상품수정"
	        onclick="location.href='productModify.do?no=${dto.getProduct_no() }&currentPage=${pi}&status=${status}'">
	   &nbsp;&nbsp;
	   <input type="button" value="상품삭제"
	        onclick="location.href='productDelete.do?no=${dto.getProduct_no() }&currentPage=${pi}&status=${status}'">
	   &nbsp;&nbsp;
	   
	   <input type="button" value="전체목록"
	        onclick="location.href='productList.do?currentPage=${pi}&status=&subtitle='"><br><br>
				<input class="btn" type="button" value="찜하기" oncick="location.href='cart_list.do?no=${dto.getProduct_no()() }&num=${sessionScope.user.userNo}'">
				<input class="btn" type="button" value="구매하기" oncick="location.href='order_list.do?no=${dto.getProduct_no() }&num=${sessionScope.user.userNo}'">	        		
	        	
	</div>
</body>
</html>
</body>
</html>