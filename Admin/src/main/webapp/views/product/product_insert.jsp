<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE html>
<html lang="ko">
<head>
<meta charset="UTF-8">
<title>상품 등록</title>
</head>
<body>
	<div align="center">
		<h3>상품 등록</h3>

		<form action="<%=request.getContextPath()%>/productInsertOk.do" method="post"
			enctype="multipart/form-data">
		 <input type="hidden" name="user_no" value="${sessionScope.user.userNo}">
			<table class="insertTableForm">
				<c:set var="catlist" value="${CategoryList }" />
				<tr>
					<th>상품 이름</th>
					<td><input type="text" name="product_name" required></td>
				</tr>
				<tr>
					<th>카테고리 번호</th>
					<td><select name="category_no">
							<c:if test="${empty CategoryList }">
								<option value="">:::카테고리 코드 없음:::</option>
							</c:if>

							<c:if test="${!empty CategoryList }">
								<c:forEach items="${CategoryList }" var="dto">
									<option value="${dto.getCategory_No() }">
										${dto.getName() } (${ dto.getCategory_No() })</option>
								</c:forEach>
							</c:if>
					</select></td>
				</tr>
				<tr>
					<th>상품 정보</th>
					<td><textarea rows="8" cols="22" name="product_info"></textarea></td>
				</tr>
				<tr>
					<th>상품 가격</th>
					<td><input type="text" name="product_price" required></td>
				</tr>
				<tr>
					<th>제고 수량</th>
					<td><input type="text" name="stock_quantity" required></td>
				</tr>
				<tr>
					<th>상품 이미지</th>
					<td><input type="file" name="image_url"></td>
				</tr>
				<tr>
					<th>상품 이미지 정보</th>
					<td><textarea rows="8" cols="22" name="product_image_info"></textarea></td>
				</tr>
				<tr>
					<td colspan="2" align="center">
						<input type="submit" value="상품 등록"> 
						<input type="reset" value="초기화">
					</td>
				</tr>

			</table>
		</form>
	</div>

</body>
</html>