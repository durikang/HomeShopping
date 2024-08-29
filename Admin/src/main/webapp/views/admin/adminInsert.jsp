<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
	<div align = "center">
		<hr width = "50%" color = "red">
			<h3>관리자 등록 페이지</h3>
		<hr width = "50%" color = "red">
		
		<br><br>
		
		<form method = "post" action = "<%= request.getContextPath() %>/admin_insert_ok.do">
			<table border = "1" width = "auto">
	         <tr>
	            <th>사용자 번호</th>
	            <td>
	               <input type = "text" name = "user_no">
	            </td>
	         </tr>
	         
	         <tr>
	            <th>사용자 아이디</th>
	            <td>
	               <input type = "text" name = "user_id">
	            </td>
	         </tr>
	         
	         <tr>
	            <th>사용자 비밀번호</th>
	            <td>
	               <input type = "password" name = "password">
	            </td>
	         </tr>
	         
	         <tr>
	            <th>사용자 이름</th>
	            <td>
	               <input type = "text" name = "name">
	            </td>
	         </tr>
	         
	         <tr>
	            <th>사용자 이메일</th>
	            <td>
	               <input type = "text" name = "email">
	            </td>
	         </tr>
	         
	         <tr>
	            <th>역할 코드</th>
	            <td>
	               <input type = "text" name = "role_code">
	            </td>
	         </tr>
	         
	         <tr>
	            <th>역할 이름</th>
	            <td>
	               <input type = "text" name = "role_name">
	            </td>
	         </tr>
	         
	         <tr>
	            <td colspan="2" align="center">
	               <input type="submit" value="등록">&nbsp;
	         	   <input type="reset" value="초기화">
	            </td>
	         </tr>
	      </table>
		</form>
	</div>
</body>
</html>