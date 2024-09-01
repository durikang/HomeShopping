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
		<h3>${dto.getName() } 카테고리 상세내역 페이지</h3>
		<hr width="30%" color="red">
		<br>
		<br>

		<table border="1" width="400">
			<tr>
				<th>카테고리 번호</th>
				<td>${dto.getCategory_No() }</td>
			</tr>

			<tr>
				<th>카테고리 목록</th>
				<td>${dto.getName() }</td>
			</tr>
			<tr>
				<th>카테고리 정보</th>
				<td>${fn:replace(dto.getDescription(), newLine, br) }</td>
			</tr>
			
			<c:if test="${empty dto }">
				<tr>
					<td colspan="2" align="center">
						<h3>해당하는 게시글이 없습니다.</h3>
					</td>
				</tr>
			</c:if>
		</table>
		<br>
		
		<input type="button" value="카테고리수정"
	        onclick="location.href='productCategoryModify.do?no=${dto.getCategory_No() }'">
	   &nbsp;&nbsp;
	   <input type="button" value="글삭제"
	        onclick="location.href='productCategoryDelete.do?no=${dto.getCategory_No() }'">
	   &nbsp;&nbsp;
	   <input type="button" value="전체목록"
	        onclick="location.href='productCategory.do'">
	</div>
</body>
</html>