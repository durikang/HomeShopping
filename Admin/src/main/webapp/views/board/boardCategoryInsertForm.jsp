<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>

	<div class="content">
		<h2>게시글 작성</h2>
		<form action="boardCategoryInsert.do" method="post">
			<input type="hidden" name="userNo" value="${sessionScope.userNo}">

			<!-- 카테고리 이름 -->
			<div class="form-group">
				<label for="title">카테고리 이름</label> 
				<input type="text" id="title" name="name" required>
			</div>

			<!-- 내용 입력 (CKEditor 적용) -->
			<div class="form-group">
				<label for="content">내용</label>
				<textarea id="content" name="description" required></textarea>
			</div>

			<!-- 제출 버튼 -->
			<div class="form-group">
				<button type="submit" class="btn btn-submit">게시글 등록</button>
			</div>
		</form>
	</div>
</body>
</html>