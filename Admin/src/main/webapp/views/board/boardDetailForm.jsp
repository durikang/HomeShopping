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
        <div class="comment-section">
            <h3>댓글 작성</h3>
            <form id="commentForm">
                <input type="hidden" name="boardNo" value="${info.boardNo}">
                <input type="hidden" name="userNo" value="${sessionScope.userNo}">
                <input type="hidden" name="status" value="${status}">
                <input type="hidden" name="currentPage" value="${currentPage}">
                <div class="form-group">
                    <label for="commentContent">내용:</label>
                    <textarea id="commentContent" name="content" rows="4" class="input-field" required></textarea>
                </div>
                <button type="submit" class="btn btn_primary">댓글 작성</button>
            </form>
        </div>

        <!-- 기존 댓글 표시 -->
        <c:import url="${replyUrl}" />
    </div>
    <!-- AJAX로 댓글 전송 및 추가 -->
<script>
    $(document).ready(function() {
        $('#commentForm').on('submit', function(event) {
            event.preventDefault(); // 폼 제출 기본 동작 방지

            // Form 데이터를 JSON으로 변환
            var formData = {
                status: $('input[name="status"]').val(),
                currentPage: $('input[name="currentPage"]').val(),
                boardNo: $('input[name="boardNo"]').val(),
                userNo: $('input[name="userNo"]').val(),
                content: $('#commentContent').val(),
                parentReplyNo: $('input[name="parentReplyNo"]').val() // 부모 댓글 번호 (없으면 null)
            };

            $.ajax({
                type: 'POST',
                url: 'BoardReplyInsert.do',
                contentType: 'application/json', // 서버로 JSON 데이터 전송
                data: JSON.stringify(formData), // JSON으로 변환된 데이터를 전송
                dataType: 'json', // 서버 응답도 JSON으로 기대
                success: function(response) {
                    if (response.success) {
                        // 서버에서 성공적으로 응답을 받았을 때의 처리
                        var commentsHtml = '';
                        $.each(response.comments, function(index, comment) {
                            commentsHtml += '<div class="comment" style="margin-left: ' + (comment.nodeLevel * 20) + 'px;">';
                            commentsHtml += '<div class="comment-info">';
                            commentsHtml += '<strong>' + comment.userId + '</strong>';
                            commentsHtml += '<span>' + comment.createdAt + '</span>';
                            commentsHtml += '</div>';
                            commentsHtml += '<div class="comment-content">';
                            commentsHtml += comment.content;
                            commentsHtml += '</div></div>';
                        });
                        $('#commentsContainer').html(commentsHtml); // 댓글 목록 업데이트
                        $('#commentContent').val(''); // 입력창 초기화
                    } else {
                        alert('댓글 등록에 실패했습니다.');
                    }
                },
                error: function(xhr, status, error) {
                    alert('댓글 등록에 실패했습니다.');
                }
            });
        });
    });
</script>
</body>
</html>
