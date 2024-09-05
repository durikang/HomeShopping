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
			<h3>관리자 검색 목록 페이지</h3>
		<hr width = "50%" color = "red">
		
		<br><br>
		
		<form method="post"	action="<%=request.getContextPath() %>/search.do">
	   
	      <select name="field">
	         <option value="id">아이디</option>
	         <option value="name">이름</option>
	         <option value="job">직업</option>
	         <option value="addr">주소</option>
	      </select>
	      
	      <input type="text" name="keyword">&nbsp;&nbsp;
	   	  <input type="submit" value="검색">
	   </form>
	   
	   <br>
	   
	   <table border="1" width="500">
	      <tr>
	         <th>회원No.</th> <th>회원명</th>
	         <th>회원직업</th> <th>회원가입일</th>
	      </tr>
	      
	      <c:set var="list" value="${Search }" />
	      
	      <c:if test="${!empty list }">
	         <c:forEach items="${list }" var="dto">
	            <tr>
	               <td> ${dto.getNum() } </td>
	               <td> ${dto.getMemname() } </td>
	               <td> ${dto.getJob() } </td>
	               <td> ${dto.getRegdate().substring(0,10) } </td>
	            </tr>
	         </c:forEach>
	      
	      </c:if>
	      
	      <c:if test="${empty list }">
	         <tr>
	            <td colspan="4" align="center">
	               <h3>검색 회원 리스트가 없습니다.....</h3>
	            </td>
	         </tr>
	      </c:if>
	   </table>
	   
	   <br>
	   
	   <input type="button" value="회원목록"
	   				onclick="location.href='select.do'">
	   
	</div>
</body>
</html>