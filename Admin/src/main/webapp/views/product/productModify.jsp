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
		<c:set var="dto" value="${Modify }" />
		<c:set var="list" value="${CategoryList }"/>

		<h3>${dto.getName() }수정 페이지</h3>
		<br> <br>

		<form action="<%=request.getContextPath()%>/productModifyOk.do" method="post" 
		enctype="multipart/form-data">
		<input type="hidden" name="product_no" value="${dto.getProduct_no() }">
		<%-- <input type="hidden" name="cerated_at" value=${dto.getCreated_at() }> --%>
		<input type="hidden" name="user_no" value="${sessionScope.user.userNo}">
			<table border="1" width="400">
				<tr>
					<th>카테고리 번호</th>
					<td><select name="category_no">
							<c:if test="${empty CategoryList }">
								<option value="">:::카테고리 코드 없음:::</option>
							</c:if>

							<c:if test="${!empty CategoryList }">
								<c:forEach items="${CategoryList }" var="category_dto">
									<option value="${category_dto.getCategory_No() }">
										${category_dto.getName() } (${ category_dto.getCategory_No() })</option>
								</c:forEach>
							</c:if>
					</select>
				</tr>

				<tr>
					<th>상품 이름</th>
					<td><input name="product_name" required value="${dto.getName() }"></td>
				</tr>
				<tr>
					<th>상품 정보</th>
					<td><textarea rows="8" cols="22" name="description"
							value="${dto.getDescription() }"></textarea></td>
				</tr>
				<tr>
					<th>상품 이미지</th>
					<td><input type="file" name="image_url"></td>
				</tr>
				<tr>
					<th>상품 이미지 정보</th>
					<td><textarea rows="8" cols="22" name="img_description"
							value="${dto.getImg_description() }"></textarea></td>
				</tr>
				<tr>
					<th>상품 가격</th>
					<td><input name="product_price" required></td>
					
					
				</tr>
				<tr>
					<th>상품 재고</th>
					<td><input name="stock_quantity" required></td>
				</tr>

				<tr>
					<th>상품 상태</th>
					<td>
					<select name="product_state">
						<option value="N">제품 수정</option>
						<option value="Y">제품 삭제</option>
					</select>
					</td>
				</tr>
																								
				<tr>
					<td class="table_bottom button categoryInsertBtn" colspan="3">
					<button type="button" onclick="location.href='productList.do?status=${status}&currentPage=${currentPage}'" class="btn btn_space_tb">뒤로가기</button>
						<input class="btn" type="submit" value="카테고리 수정"> 
						<input class="btn" type="reset" value="초기화">
					</td>
				</tr>
			</table>

			<br>



		</form>



	</div>
</body>
</html>