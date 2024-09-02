<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<!DOCTYPE html>
<html lang="ko">
<head>
<meta charset="UTF-8">
<title>게시글 상세보기</title>
<link rel="stylesheet" href="${contextPath}/resources/board/css/board.css">
<script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
<style>
    /* 댓글 및 대댓글 스타일링 */
    .comment {
        border: 1px solid #e1e1e1;
        padding: 10px;
        margin-bottom: 10px;
        border-radius: 4px;
        background-color: #f9f9f9;
    }

    .comment-info {
        font-size: 14px;
        color: #555;
        margin-bottom: 5px;
    }

    .comment-content {
        font-size: 16px;
        color: #333;
        margin-bottom: 10px;
    }

    .reply-link {
        display: inline-block;
        margin-top: 5px;
        font-size: 14px;
        color: #007bff;
        cursor: pointer;
    }

    .reply-link:hover {
        text-decoration: underline;
    }

    .reply-form {
        margin-top: 10px;
        margin-left: 20px;
        display: none;
        background-color: #f1f1f1;
        padding: 10px;
        border-radius: 4px;
    }

    .reply-form textarea {
        width: 100%;
        height: 100px;
        border-radius: 4px;
        border: 1px solid #ced4da;
        padding: 8px;
        font-size: 14px;
        box-sizing: border-box;
    }

    .submit-reply {
        margin-top: 10px;
        padding: 8px 16px;
        background-color: #007bff;
        color: white;
        border: none;
        border-radius: 4px;
        cursor: pointer;
    }

    .submit-reply:hover {
        background-color: #0056b3;
    }

    /* 대댓글 구분 스타일 */
    /* 추가적으로 필요하면 더 깊은 레벨도 스타일링 가능 */
    .comment[data-node-level="2"] {
        margin-left: 40px;
        background-color: #f0f8ff;
    }

    .comment[data-node-level="3"] {
        margin-left: 60px;
        background-color: #e6f2ff;
    }

    .comment[data-node-level="4"] {
        margin-left: 80px;
        background-color: #cce0ff;
    }
    /* 댓글 액션 버튼 스타일 */
	.comment-actions {
	    margin-top: 5px;
	}
	
	.comment-actions a {
	    margin-right: 10px;
	    font-size: 14px;
	    color: #007bff;
	    cursor: pointer;
	}
	
	.comment-actions a:hover {
	    text-decoration: underline;
	}
	    
    
</style>
</head>
<body>
    <div class="content">
        <!-- 카테고리 이름 -->
        <div class="category-label">${info.categoryName}</div>

        <!-- 제목 -->
        <h2 class="content-title">${info.title}</h2>

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
        <div class="content-body">
            <c:out value="${info.content}" escapeXml="false"/>
        </div>

        <!-- 뒤로가기 버튼 -->
        <button type="button" onclick="location.href='boardList.do?status=${status}&currentPage=${currentPage}'" class="btn btn_space_tb">뒤로가기</button>
        <!-- 수정하기 버튼 -->
        <button type="button" onclick="location.href='boardUpdateForm.do?no=${info.boardNo}&userType=${info.userType}&status=${status}&currentPage=${currentPage}'" class="btn btn_space_tb">수정하기</button>

        <!-- 댓글 입력 폼 -->
		<c:import url="board/import/boardCommentInsertForm.jsp"/>

        <!-- 기존 댓글 표시 -->
		<c:import url="board/import/boardCommentList.jsp"/>

    </div>



</body>
</html>
