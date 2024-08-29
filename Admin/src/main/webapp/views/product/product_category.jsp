<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>카테고리 리스트 페이지</title>
</head>
<body>
	<div align="center">
		<hr width="50%" color="red">
		<h3>카테고리 전체 리스트 페이지</h3>
		<hr width="50%" color="red">
		<br> <br>


		<table border="1" width="650">
			<tr>

				<th>카테고리 번호</th>
				<th>카테고리 목록</th>
				<th>카테고리 정보</th>

			</tr>
			<c:set var="list" value="${List }" />
			<c:if test="${!empty list }">
				<c:forEach items="${list }" var="dto">
					<tr>
						<td>${dto.getCategory_No() }</td>
						<td>${dto.getName() }</td>
						<td>${dto.getDescription() }</td>
					</tr>
				</c:forEach>
			</c:if>
			<c:if test="${empty list }">
				<tr>
					<td colspan="3" align="center">
						<h3>카테고리가 없습니다.</h3>
					</td>
				</tr>
			</c:if>
			<tr>
				<td colspan="3" align="center">
					<input type="button" value="카테고리 등록" onclick="location.href='product_category_insert.go'">&nbsp;&nbsp; 
					<input type="button" value="카테고리 수정" onclick="location.href='product_category_modify.do'">&nbsp; &nbsp;
					<input type="button" value="카테고리 삭제" onclick="location.href='product_category_delete.do'">
				</td>
			</tr>

		</table>
	</div>
</body>
</html>