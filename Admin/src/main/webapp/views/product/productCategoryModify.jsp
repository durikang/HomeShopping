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

		<hr width="30%" color="red">
		<h3>${dto.getName() } 카테고리 수정 페이지</h3>
		<hr width="30%" color="red">
		<input type="hidden" name="uno" value="${dto.getCategory_No() }">
		<br>
		<br>

		<form method="post" enctype="multipart/form-date"
			action="<%=request.getContextPath()%>/product_category_modify_ok.do">
			<table border="1" width="400">
			<tr>
				<th>카테고리 번호</th>
				<td><input name="category_no" readonly
						value="${dto.getCategory_No() }"></td>
			</tr>

			<tr>
				<th>카테고리 목록</th>
				<td><input name="name" 
						value="${dto.getName() }"></td>
			</tr>
			<tr>
				<th>카테고리 정보</th>
				<td><textarea rows="8" cols="22" name="description" value="${dto.getDescription() }"></textarea></td>
			</tr>
			            
                        <tr>
                <td class="table_bottom button categoryInsertBtn" colspan="2">
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