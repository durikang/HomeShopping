<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fm" %>

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

</script>



            <div class="search-container">
                <form action="boardSearchList.do" method="get">
                	<input type="hidden" name="subtitle" value="${param.subtitle }">
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