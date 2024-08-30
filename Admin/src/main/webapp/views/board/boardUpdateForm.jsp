<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<!DOCTYPE html>
<html lang="ko">
<head>
<meta charset="UTF-8">
<title>게시글 상세보기</title>
<link rel="stylesheet" href="${contextPath}/resources/board/css/board.css">
</head>
<body>
    <div class="content">
	    <form action="boardUpdateOk.do" method="post">	
	    	<input type="hidden" name="userNo" value="${sessionScope.userNo}">
	    	<input type="hidden" name="boardNo" value="${info.boardNo}">
	        <!-- 카테고리 이름 -->
	        <!-- 카테고리 선택 -->
	        <div class="form-group">
	            <select name="categoryNo" id="categoryNo" required>
	                <c:forEach var="category" items="${categoryList}">
	                    <option value="${category.categoryNo}">${category.name}</option>
	                </c:forEach>
	            </select>
	        </div> 
	
	        <!-- 제목 -->
	        <h2 class="content-title form-group">
	        	<input type="text" name="boardTitle" value="${info.title}" required="required">
	        </h2>
	
	        <!-- 작성자 및 작성/수정일 정보 -->
	        <div class="content-info">
	            <span>작성자: ${info.userId} (${info.userName})</span>
	            <c:choose>
	                <c:when test="${not empty info.updateAt}">
	                    <span>수정일: <fmt:formatDate value="${info.updateAt}" pattern="yyyy-MM-dd HH:mm:ss"/></span>
	                </c:when>
	                <c:otherwise>
	                    <span>작성일: <fmt:formatDate value="${info.createAt}" pattern="yyyy-MM-dd HH:mm:ss"/></span>
	                </c:otherwise>
	            </c:choose>
	        </div>
	
	        <!-- 본문 내용 -->
		    <!-- 내용 입력 (CKEditor 적용) -->
	        <div class="form-group">
	            <textarea id="content" name="content" required><c:out value="${info.content}" escapeXml="false"/></textarea>
	            <script>
	                CKEDITOR.replace('content');
	            </script>
	        </div>            
			
	        <!-- 뒤로가기 버튼 -->
	        <button type="button" onclick="location.href='boardList.do?status=${status}&currentPage=${currentPage}'" class="btn btn_space_tb">뒤로가기</button>
			<!-- 수정하기 버튼 (수정 권한 : 본인이 작성한 게시판 or session의 userType : admin 
				그러나 현재 admin 관련 로직이 미구현 상태 이므로 해당 로직은 추후에 구축 예정 by 두리
			-->
			<button type="submit" class="btn btn_space_tb">수정하기</button>
		</form>	
    </div>
</body>
</html>
