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
			<h3>관리자 상세 정보 페이지</h3>
		<hr width = "50%" color = "red">
		
		<br><br>
		
		<table border = "1" width = "auto">
			<c:set var = "dto" value = "${Cont }"/>
			
			<c:if test = "${!empty dto }">
				<tr>
					<th>No</th>
					<td>${dto.getUserNo() }</td>
				</tr>
				
				<tr>
					<th>아이디</th>
					<td>${dto.getUserId() }</td>
				</tr>
				
				<tr>
					<th>이름</th>
					<td>${dto.getName() }</td>
				</tr>
				
				<tr>
					<th>이메일</th>
					<td>${dto.getEmail() }</td>
				</tr>
				
				<tr>
					<th>직책 코드</th>
					<td>${dto.getRoleCode() }</td>
				</tr>
				
				<tr>
					<th>직책 이름</th>
					<td>${dto.getRoleName() }</td>
				</tr>

				<tr>
					<th>생성날짜</th>
					<td>${dto.getCreatedAt() }</td>
				</tr>
				
				<tr>
					<th>업데이트날짜</th>
					<td>${dto.getUpdatedAt() }</td>
				</tr>
			</c:if>
			
			<c:if test = "${empty dto }">
	         <tr>
	            <td colspan = "2" align = "center">
	               <h3>관리자의 정보가 없습니다.</h3>
	            </td>
	         </tr>
	      </c:if>
		</table>
		
		<br>
		
		<input type = "button" value = "수정" onclick = "location.href='modify.do?num=${dto.getUserNo() }'">&nbsp;
	    <input type = "button" value = "목록" onclick = "location.href='select.do'">
	</div>
</body>
</html>