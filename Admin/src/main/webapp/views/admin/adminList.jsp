<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
  
<%@ taglib prefix = "c" uri = "http://java.sun.com/jsp/jstl/core" %>
  
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
	<div align = "center">
		<hr width = "50%" color = "red">
			<h3>관리자 리스트 페이지</h3>
		<hr width = "50%" color = "red">
		
		<br><br>
		
		<table border = "1" width = "auto">
			<tr>
				<th>사용자 번호</th> <th>사용자 이름</th> <th>역할 이름</th> 
			</tr>
			
			<c:set  var="" value="${ }"/>		
	
		</table>
	</div>
</body>
</html>