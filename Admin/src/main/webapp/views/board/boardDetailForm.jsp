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

    /* 추가적으로 필요하면 더 깊은 레벨도 스타일링 가능 */
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
		<div class="comment-list">
		    <h3>댓글</h3>
		    <div id="commentsContainer">
		        <c:forEach var="comment" items="${comments}">
		            <div class="comment" data-node-level="${comment.nodeLevel}" style="margin-left: ${comment.nodeLevel * 20}px;">
		                <div class="comment-info">
		                    <strong>${comment.userId}</strong>
		                    <c:if test="${comment.userId == info.userId}">
		                        <span class="author-label">(작성자)</span>
		                    </c:if>
		                    <span><fmt:formatDate value="${comment.createdAt}" pattern="yyyy-MM-dd HH:mm:ss"/></span>
		                </div>
		                <div class="comment-content">
		                    <c:out value="${comment.content}" escapeXml="false"/>
		                </div>
		                <!-- 댓글 달기 링크 항상 보임 -->
		                <a href="#" class="reply-link" data-reply-no="${comment.replyNo}" data-user-id="${comment.userId}">댓글 달기</a>
		                <!-- 대댓글 입력 폼 -->
		                <div class="reply-form" id="reply-form-${comment.replyNo}">
		                    <textarea class="reply-content" rows="3" placeholder="${comment.userId}의 답변을 입력하세요"></textarea>
		                    <button class="submit-reply" data-parent-reply-no="${comment.replyNo}">대댓글 작성</button>
		                </div>
		            </div>
		        </c:forEach>
		    </div>
		</div>

    </div>

<script>
document.addEventListener("DOMContentLoaded", function() {
    // 일반 댓글 작성 처리
    document.getElementById("commentForm").addEventListener("submit", function(event) {
        event.preventDefault();
        
        var content = document.getElementById('commentContent').value;
        if (content.trim() === "") {
            alert("내용을 입력하세요.");
            return;
        }

        var formData = {
            boardNo: document.querySelector('input[name="boardNo"]').value || null,
            userNo: document.querySelector('input[name="userNo"]').value || null || 4, // null일 경우 기본값 4
            content: content,
            parentReplyNo: null // 일반 댓글이므로 parentReplyNo는 null
        };

        var xhr = new XMLHttpRequest();
        xhr.open("POST", "BoardReplyInsert.do", true);
        xhr.setRequestHeader("Content-Type", "application/json;charset=UTF-8");

        xhr.onload = function() {
            if (xhr.status >= 200 && xhr.status < 300) {
                var response = JSON.parse(xhr.responseText);
                if (response.success) {
                    location.reload(); // 댓글이 성공적으로 추가된 후 페이지를 새로고침
                } else {
                    alert('댓글 등록에 실패했습니다.');
                }
            } else {
                alert('댓글 등록에 실패했습니다.');
            }
        };

        xhr.send(JSON.stringify(formData));
    });

    // 대댓글 폼 토글 처리
    var replyLinks = document.querySelectorAll(".reply-link");
    
    replyLinks.forEach(function(link) {
        link.addEventListener("click", function(event) {
            event.preventDefault(); // 기본 동작 방지
            var replyNo = event.target.getAttribute("data-reply-no");
            var replyUserId = event.target.getAttribute("data-user-id");
            var replyForm = document.getElementById("reply-form-" + replyNo);
            var replyContentPlaceholder = replyForm.querySelector(".reply-content");

            // " ~의 답변"을 placeholder에 설정
            replyContentPlaceholder.placeholder = replyUserId + "의 답변을 입력하세요";

            // 대댓글 폼을 토글(보이기/숨기기)
            if (replyForm.style.display === "none" || replyForm.style.display === "") {
                replyForm.style.display = "block";
            } else {
                replyForm.style.display = "none";
            }
        });
    });

    // 대댓글 작성 처리
    var submitReplyButtons = document.querySelectorAll(".submit-reply");
    
    submitReplyButtons.forEach(function(button) {
        button.addEventListener("click", function(event) {
            var parentReplyNo = event.target.getAttribute("data-parent-reply-no");
            var content = document.querySelector("#reply-form-" + parentReplyNo + " .reply-content").value;
            
            if (content.trim() === "") {
                alert("내용을 입력하세요.");
                return;
            }

            var formData = {
                boardNo: document.querySelector('input[name="boardNo"]').value || null,
                userNo: 4,  // admin 유저 고정
                content: content,
                parentReplyNo: parentReplyNo // 대댓글이므로 parentReplyNo 설정
            };

            var xhr = new XMLHttpRequest();
            xhr.open("POST", "BoardReplyInsert.do", true);
            xhr.setRequestHeader("Content-Type", "application/json;charset=UTF-8");

            xhr.onload = function() {
                if (xhr.status >= 200 && xhr.status < 300) {
                    var response = JSON.parse(xhr.responseText);
                    if (response.success) {
                        location.reload(); // 대댓글이 성공적으로 추가된 후 페이지를 새로고침
                    } else {
                        alert('대댓글 등록에 실패했습니다.');
                    }
                } else {
                    alert('대댓글 등록에 실패했습니다.');
                }
            };

            xhr.send(JSON.stringify(formData));
        });
    });
});

</script>

</body>
</html>
