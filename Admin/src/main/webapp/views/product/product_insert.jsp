<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html lang="ko">
<head>
<meta charset="UTF-8">
<title>카테고리 등록</title>
</head>
<body>
	<div align="center">
    <h3>카테고리 등록</h3>

    <form action="${ contextPath }/productCategoryInsert.do" method="post" onsubmit="return validatePasswords()">
        <table class="insertTableForm">
            <tr>
                <th>카테고리 번호</th>
                <td>
                    <input type="text" name="cateNo" required>
                </td>
            </tr>
            <tr>
                <th>카테고리 이름</th>
                <td>
                    <input type="text" name="cateName" required>
                </td>
            </tr>
             <tr>
                <th>카테고리 정보</th>
                <td>
                    <textarea rows="8" cols="22" name="cateInfo"></textarea>
                </td>
            </tr>
            
                        <tr>
                <td class="table_bottom button categoryInsertBtn" colspan="2">
                    <input class="btn" type="submit" value="카테고리 등록">
                    <input class="btn" type="reset" value="초기화">
                </td>
            </tr>
         </table>
     </form>
     </div>

</body>
</html>