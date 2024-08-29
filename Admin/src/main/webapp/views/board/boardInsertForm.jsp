<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html lang="ko">
<head>
<meta charset="UTF-8">
<title>게시판 작성 폼</title>
<!-- 스타일링은 이미 준비된 CSS 파일을 사용 -->
<link rel="stylesheet" href="${contextPath }/resources/master.css">
<!-- CKEditor 스크립트 추가 -->
<script src="https://cdn.ckeditor.com/4.16.2/standard/ckeditor.js"></script>
<style type="text/css">
	body {
		font-family: Arial, sans-serif;
		background-color: #f8f9fa;
		margin: 0;
		padding: 0;
	}
	
	.content {
		max-width: 800px;
		margin: 50px auto;
		padding: 20px;
		background-color: #ffffff;
		border-radius: 8px;
		box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);
	}
	
	h2 {
		margin-bottom: 20px;
		color: #333;
		font-size: 28px;
		text-align: center;
	}
	
    .form-group {
        margin-bottom: 20px;
    }
    .form-group label {
        display: block;
        margin-bottom: 8px;
        font-weight: bold;
        color: #495057;
    }
    .form-group select,
    .form-group input[type="text"],
    .form-group textarea {
        width: 100%;
        padding: 10px;
        font-size: 16px;
        border-radius: 4px;
        border: 1px solid #ced4da;
        background-color: #f8f9fa;
        transition: border-color 0.2s;
        box-sizing: border-box;
    }
    .form-group select:focus,
    .form-group input[type="text"]:focus,
    .form-group textarea:focus {
        border-color: #80bdff;
        outline: none;
    }
    .form-group textarea {
        height: 200px;
    }
</style>
</head>
<body>
    <div class="content">
        <h2>게시글 작성</h2>
        <form action="boardInsert.do" method="post">
            <input type="hidden" name="userNo" value="${sessionScope.userNo}">
            
            <!-- 카테고리 선택 -->
            <div class="form-group">
                <label for="categoryNo">카테고리</label>
                <select name="categoryNo" id="categoryNo" required>
                    <c:forEach var="category" items="${categoryList}">
                        <option value="${category.categoryNo}">${category.name}</option>
                    </c:forEach>
                </select>
            </div>

            <!-- 제목 입력 -->
            <div class="form-group">
                <label for="title">제목</label>
                <input type="text" id="title" name="title" required>
            </div>

            <!-- 내용 입력 (CKEditor 적용) -->
            <div class="form-group">
                <label for="content">내용</label>
                <textarea id="content" name="content" required></textarea>
                <script>
                    CKEDITOR.replace('content');
                </script>
            </div>

            <!-- 제출 버튼 -->
            <div class="form-group">
                <button type="submit" class="btn btn-submit">게시글 등록</button>
            </div>
        </form>
    </div>
</body>
</html>
