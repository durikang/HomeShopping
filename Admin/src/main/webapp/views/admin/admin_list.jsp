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
		
		<form method = "post" action = "<%= request.getContextPath() %>/search.do">
			<select name = "field">
				<option value="id">아이디</option>
	        	<option value="name">이름</option>
			</select>
			
			<input type = "text" name = "keyword">&nbsp;
	   	 	<input type = "submit" value = "검색">
	   	 </form>
	   	 
	   	 <br>
			
		 <table border = "1" width = "auto">
		 	<tr>
		 		<th>No</th> <th>이름</th> <th>이메일</th>
		 	</tr>
		 	
		 	<c:set var = "list" value = "${List }"/>
		 		<c:forEach items = "${list }" var = "dto">
		 			<tr>
		 				<td>${dto.getNum() }</td>
		 			</tr>
		 		</c:forEach>
		 </table>
	</div>
</body>
</html>