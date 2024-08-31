<%@ page import="com.global.board.model.BoardDTO"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"
    import="java.util.ArrayList,java.util.List ,com.global.board.model.*"%>
<%@ page import="com.global.utils.StringUtils" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>게시판 리스트</title>

<script type="text/javascript">
 
//JavaScript 함수: 필터 UI 표시/숨기기
function toggleFilter() {
    const filterContainer = document.querySelector('.filter-container');
    const mainSearchBtn = document.querySelector('.search-btn-main');
    const filterSearchBtn = document.querySelector('.search-btn-filter');

    if (filterContainer.style.display === 'none' || filterContainer.style.display === '') {
        filterContainer.style.display = 'block';
        mainSearchBtn.style.display = 'none';  // 원래 위치의 검색 버튼 숨김
        filterSearchBtn.style.display = 'inline-block';  // 필터 내 검색 버튼 표시
    } else {
        filterContainer.style.display = 'none';
        mainSearchBtn.style.display = 'inline-block';  // 원래 위치의 검색 버튼 표시
        filterSearchBtn.style.display = 'none';  // 필터 내 검색 버튼 숨김
    }
}

// JavaScript 함수: 클릭한 행의 ID와 userType을 가져와서 상세 페이지로 이동
function goToDetailPage(event) {
    const target = event.currentTarget;
    const No = target.getAttribute('data-id');
    const userType = target.getAttribute('data-user-type');
    const currentPage = ${pi.currentPage};  // pi.currentPage를 자바스크립트 변수로 설정
    const status = '${status}';
    
    if (No) {
        window.location.href = '${contextPath}/boardDetailForm.do?no=' + No + '&userType=' + userType + '&status=' + status + '&currentPage=' + currentPage;
    }
}

// 모든 행에 클릭 이벤트 리스너 추가
document.addEventListener('DOMContentLoaded', function() {
    const rows = document.querySelectorAll('table tr[data-id]');

    rows.forEach(row => {
        row.addEventListener('click', goToDetailPage);
    });
});
</script>
<style type="text/css">

</style>

</head>
<body>
	<div class="title">
	    <h1>게시판</h1>
	    <c:if test="${ not empty param.subtitle}">
	        <nav aria-label="breadcrumb">
	            <ol class="breadcrumb">
	                <li>홈</li> <!-- 홈으로 이동 -->
	                <li>게시판 관리</li> <!-- 게시판 관리 페이지로 이동 -->
	                <li class="active" aria-current="page">${ param.subtitle }</li> <!-- 현재 위치 -->
	            </ol>
	        </nav>
	    </c:if>
	</div>

    <div class="container">
        <!-- 사이드바 메뉴 -->
        <div class="sidebar">
            <h3>카테고리</h3>
            <ul>
                <li><a href="boardList.do?subtitle='${param.subtitle}'">전체 게시글</a></li>
                <c:forEach var="category" items="${categoryList}">
                    <li><a href="boardList.do?categoryNo=${category.categoryNo}">${category.name}</a></li>
                </c:forEach>
            </ul>
        </div>

        <!-- 메인 콘텐츠 -->
        <div class="main-content">
            <!-- 검색 바 추가 -->
            <div class="search-container">
                <form action="boardSearchList.do" method="get">
					<div class="content-title form-group">
					    <div class="search-bar">
					        <i class="fa-solid fa-magnifying-glass search-icon"></i>
					        <input type="search" name="searchKeyword" class="search-input" placeholder="검색" value="${searchKeyword}"> <!-- 기존 검색어 값 유지 -->
					    </div>
					    <button type="button" class="btn filter-icon" onclick="toggleFilter()">
					        <span class="material-symbols-outlined">tune</span>
					    </button>
					</div>


                    
                    <!-- 필터 UI 추가 -->
                    <div id="filterContainer" class="filter-container">
                        <!-- 카테고리 필터 -->
                        <div class="filter-item">
                            <label for="categoryFilter" class="filter-label filter-category-label">카테고리</label>
                            <select id="categoryFilter" name="categoryNo" class="filter-select">
                                <option value="">전체</option>
                                <c:forEach var="category" items="${categoryList}">
                                    <option value="${category.categoryNo}" <c:if test="${category.categoryNo == filter.categoryNo}">selected</c:if>>${category.name}</option>
                                </c:forEach>
                            </select>
                        </div>

                        <!-- 사용자 번호 필터 -->
                        <div class="filter-item">
                            <label for="userNoFilter" class="filter-label">사용자 번호</label>
                            <input type="number" id="userNoFilter" name="userNo" class="filter-input" placeholder="사용자 번호" value="${filter.userNo}">
                        </div>

                        <!-- 조회수 필터 -->
                        <div class="filter-item">
                            <label for="minViewsFilter" class="filter-label">최소 조회수</label>
                            <input type="number" id="minViewsFilter" name="minViews" class="filter-input" placeholder="최소 조회수" value="${filter.minViews}">
                        </div>
                        <div class="filter-item">
                            <label for="maxViewsFilter" class="filter-label">최대 조회수</label>
                            <input type="number" id="maxViewsFilter" name="maxViews" class="filter-input" placeholder="최대 조회수" value="${filter.maxViews}">
                        </div>

                        <!-- 작성일 필터 -->
                        <div class="filter-item">
                            <label for="startDateFilter" class="filter-label">작성일 시작</label>
                            <input type="date" id="startDateFilter" name="startDate" class="filter-input" value="${filter.startDate}">
                        </div>
                        <div class="filter-item">
                            <label for="endDateFilter" class="filter-label">작성일 종료</label>
                            <input type="date" id="endDateFilter" name="endDate" class="filter-input" value="${filter.endDate}">
                        </div>

                        <!-- 상태 필터 -->
                        <div class="filter-item">
                            <label for="statusFilter" class="filter-label filter-status-label">상태</label>
                            <select id="statusFilter" name="isDeleted" class="filter-select">
                                <option value="">전체</option>
                                <option value="N" <c:if test="${filter.isDeleted == 'N'}">selected</c:if>>정상</option>
                                <option value="Y" <c:if test="${filter.isDeleted == 'Y'}">selected</c:if>>삭제됨</option>
                            </select>
                        </div>

                        <!-- 검색 버튼을 필터 컨테이너 안으로 이동 -->
                        <div class="filter-item filter-btn-container">
                            <button type="submit" class="btn search-btn search-btn-filter btn-w btn-h">검색</button>
                        </div>
                    </div>
                </form>
            </div>

            <!-- 게시판 테이블 -->
            <div class="table-container">
                <table>
				    <tr>
				        <td class="table_header button" colspan="9" align="right">전체 게시글 수: ${count}</td>
				    </tr>
				    <tr>
				        <th class="border-th">BoardNo.</th>
				        <th class="border-th">UserID. <span class="subtitle">(userNo)</span></th>
				        <th class="border-th">Category <span class="subtitle">(categoryCode)</span></th>
				        <th class="border-th">제목</th>
				        <th class="border-th">내용</th>
				        <th class="border-th">작성일</th>
				        <th class="border-th">수정일</th>
				        <th class="border-th">조회수</th>
				        <th class="border-th">상태</th>
				    </tr>
				    <c:choose>
				        <c:when test="${not empty list}">
				            <c:forEach items="${list}" var="board" varStatus="status">
				                <c:set var="truncatedTitle" value="${fn:length(board.title) > 20 ? fn:substring(board.title, 0, 20) + '...' : board.title}" />
				                <c:set var="truncatedContent" value="${fn:length(board.content) > 30 ? fn:substring(board.content, 0, 30) + '...' : board.content}" />
				
				                <tr class="trlist" data-id="${board.boardNo}" data-user-type="${board.userType}">
				                    <td class="board-td">${board.boardNo}</td>
				                    <td class="board-td">
				                        <c:choose>
				                            <c:when test="${board.userNo == null}">
				                                null
				                            </c:when>
				                            <c:otherwise>
				                                ${board.userId}
				                                <span class="subtitle">(${board.userNo})</span>
				                            </c:otherwise>
				                        </c:choose>
				                    </td>
				                    <td class="board-td">${board.categoryName} <span class="subtitle">(${board.categoryNo})</span></td>
				                    <td class="board-td">${truncatedTitle}</td>
				                    <td class="board-td">${StringUtils.stripHtml(truncatedContent)}</td>
				                    <td class="board-td"><fmt:formatDate value="${board.createAt}" /></td>
				                    <td class="board-td"><fmt:formatDate value="${board.updateAt}" /></td>
				                    <td class="board-td">${board.views}</td>
				                    <td class="board-td">
				                        <c:choose>
				                            <c:when test="${board.isDeleted == 'N'}">
				                                <c:set var="status" value="정상" />
				                            </c:when>
				                            <c:otherwise>
				                                <c:set var="status" value="삭제됨" />
				                            </c:otherwise>
				                        </c:choose>
				                        <c:out value="${status}" />
				                    </td>
				                </tr>
				            </c:forEach>
				
				            <!-- 빈 행 추가 -->
				            <c:forEach var="i" begin="${list.size() + 1}" end="100">
				                <tr class="trlist">
				                    <c:forEach var="i" begin="1" end="9">
				                        <td class="board-td" style="height: 40px;">&nbsp;</td> <!-- 빈 셀 생성 -->
				                    </c:forEach>
				                </tr>
				            </c:forEach>
				        </c:when>
				        <c:otherwise>
				            <tr>
				                <td colspan="9" align="center">
				                    <h3>게시판 데이터가 없습니다.</h3>
				                </td>
				            </tr>
				
				            <!-- 빈 행 추가 -->
				            <c:forEach begin="1" end="10">
				                <tr class="trlist">
				                    <td class="board-td" style="height: 40px;">&nbsp;</td> <!-- 빈 공간 표시 -->
				                </tr>
				            </c:forEach>
				        </c:otherwise>
				    </c:choose>
				
				    <tr>
				        <td class="table_bottom button" colspan="9" align="center">
				            <input type="button" class="btn" value="게시글 작성" onclick="location.href='boardInsertForm.do'">
				        </td>
				    </tr>
				</table>

            </div>
        </div>
        <!-- 사이드바 메뉴 -->
        <div class="sidebar">
            <h3>카테고리</h3>
            <ul>
                <li><a href="boardList.do">전체 게시글</a></li>
                <c:forEach var="category" items="${categoryList}">
                    <li><a href="boardList.do?categoryNo=${category.categoryNo}">${category.name}</a></li>
                </c:forEach>
            </ul>
        </div>
    </div>
</body>
</html>
