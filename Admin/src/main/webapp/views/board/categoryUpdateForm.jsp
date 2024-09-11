<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<!-- 스타일링은 이미 준비된 CSS 파일을 사용 -->
<link rel="stylesheet" href="${contextPath }/resources/master.css">
<%-- <link href="${contextPath }/resources/common/css/insertTableForm.css" rel="stylesheet" type="text/css" /> --%>
<!-- CKEditor 스크립트 추가 -->
<script src="https://cdn.ckeditor.com/4.16.2/standard/ckeditor.js"></script>
</head>
<body>

    <div class="content">
        <h2>카테고리 수정</h2>
        <form action="boardCategoryUpdateOk.do" method="post">
            <input type="hidden" name="userNo" value="${sessionScope.userNo}">
            <input type="hidden" name="categoryNo" value="${ category.categoryNo }">

            <!-- 카테고리 No. -->
            <div class="form-group">
                <label for="title">Category_No</label>
                ${ category.categoryNo }
            </div>
            <!-- 제목 입력 -->
            <div class="form-group">
                <label for="title">카테고리 제목</label>
                <input type="text" id="title" name="name" value="${ category.name }"required>
            </div>

            <!-- 내용 입력 -->
            <div class="form-group">
                <label for="title">카테고리 내용</label>
                <input type="text" id="title" name="description" value="${ category.description }"required>
            </div>
            

            <!-- 제출 버튼 -->
            <div class="form-group">
                <button type="submit" class="btn btn-submit">카테고리 수정</button>
            </div>
        </form>
    </div>

</body>
</html>