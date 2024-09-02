<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fm" %>    
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
                
                <div class="comment-actions">
                    <!-- 댓글 달기 링크 항상 보임 -->
                    <a href="#" class="reply-link" data-reply-no="${comment.replyNo}" data-user-id="${comment.userId}">댓글 달기</a>
                    
                    <!-- 수정 및 삭제 버튼 (작성자 또는 어드민만) -->
                    <c:if test="${comment.userId == sessionScope.userId || sessionScope.userType == 'ADMIN'}">
                        <a href="#" class="edit-link" data-reply-no="${comment.replyNo}">수정</a>
                        <a href="#" class="delete-link" data-reply-no="${comment.replyNo}">삭제</a>
                    </c:if>
                </div>
                
                <!-- 대댓글 입력 폼 -->
                <div class="reply-form" id="reply-form-${comment.replyNo}">
                    <textarea class="reply-content" rows="3" placeholder="${comment.userId}의 답변을 입력하세요"></textarea>
                    <button class="btn submit-reply" data-parent-reply-no="${comment.replyNo}">대댓글 작성</button>
                </div>
            </div>
        </c:forEach>
    </div>
</div>

		
		
<script type="text/javascript">
document.addEventListener("DOMContentLoaded", function() {
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

    // 댓글 수정 처리
    var editLinks = document.querySelectorAll(".edit-link");

    editLinks.forEach(function(link) {
        link.addEventListener("click", function(event) {
            event.preventDefault();
            var replyNo = event.target.getAttribute("data-reply-no");
            var currentContent = document.querySelector("#reply-form-" + replyNo + " .reply-content").value;

            // 수정 로직 구현 (예: 수정 모달 창 또는 인라인 수정 가능)
            var newContent = prompt("수정할 내용을 입력하세요:", currentContent);
            if (newContent !== null && newContent.trim() !== "") {
                // 수정된 내용을 서버로 전송
                var formData = {
                    replyNo: replyNo,
                    content: newContent
                };

                var xhr = new XMLHttpRequest();
                xhr.open("POST", "BoardReplyUpdate.do", true);
                xhr.setRequestHeader("Content-Type", "application/json;charset=UTF-8");

                xhr.onload = function() {
                    if (xhr.status >= 200 && xhr.status < 300) {
                        location.reload(); // 수정 성공 후 페이지를 새로고침
                    } else {
                        alert('댓글 수정에 실패했습니다.');
                    }
                };

                xhr.send(JSON.stringify(formData));
            }
        });
    });

    // 댓글 삭제 처리
    var deleteLinks = document.querySelectorAll(".delete-link");

    deleteLinks.forEach(function(link) {
        link.addEventListener("click", function(event) {
            event.preventDefault();
            var replyNo = event.target.getAttribute("data-reply-no");

            if (confirm("정말로 이 댓글을 삭제하시겠습니까?")) {
                // 삭제 요청을 서버로 전송
                var formData = {
                    replyNo: replyNo
                };

                var xhr = new XMLHttpRequest();
                xhr.open("POST", "BoardReplyDelete.do", true);
                xhr.setRequestHeader("Content-Type", "application/json;charset=UTF-8");

                xhr.onload = function() {
                    if (xhr.status >= 200 && xhr.status < 300) {
                        location.reload(); // 삭제 성공 후 페이지를 새로고침
                    } else {
                        alert('댓글 삭제에 실패했습니다.');
                    }
                };

                xhr.send(JSON.stringify(formData));
            }
        });
    });
});


</script>