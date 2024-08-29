<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fm" %>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">    

<link rel="stylesheet" href="${contextPath }/resources/master.css">
<link href="${contextPath }/resources/common/css/pwd.css" rel="stylesheet" type="text/css"/>
<link href="${contextPath }/resources/common/css/table.css" rel="stylesheet" type="text/css"/>
    
<script src="https://cdn.jsdelivr.net/npm/chart.js"></script>

<!-- 내비게이션 바 -->
<!-- 내비게이션 바 -->
<nav class="navbar">
    <div class="container">
        <a href="#" class="logo">관리자 대시보드</a>
        <ul class="nav-links">
            <li><a href="index.html">홈</a></li>
            
            <!-- 사용자 관리 -->
            <li class="dropdown">사용자 관리
                <ul class="dropdown-menu">
                    <li><a href="user_list.html">사용자 목록</a></li>
                    <li><a href="user_add.html">사용자 추가</a></li>
                    <li><a href="user_stats.html">사용자 통계</a></li>
                </ul>
            </li>
            
            <!-- 상품 관리 -->
            <li class="dropdown">상품 관리
                <ul class="dropdown-menu">
                    <li><a href="product_list.do">상품 목록</a></li>
                    <li><a href="product_add.do">상품 추가</a></li>
                    <li><a href="product_category.do">카테고리 관리</a></li>
                    <li><a href="product_stats.html">상품 통계</a></li>
                </ul>
            </li>
            
            <!-- 주문 관리 -->
            <li class="dropdown">주문 관리
                <ul class="dropdown-menu">
                    <li><a href="order_list.html">주문 목록</a></li>
                    <li><a href="order_stats.html">주문 통계</a></li>
                    <li><a href="delivery_status.html">배송 상태 관리</a></li>
                </ul>
            </li>
            
            <!-- 게시판 관리 -->
            <li class="dropdown">게시판 관리
                <ul class="dropdown-menu">
                    <li><a href="boardList.do">게시글 목록</a></li>
                    <li><a href="board_category.html">카테고리 관리</a></li>
                    <li><a href="board_stats.html">게시글 통계</a></li>
                </ul>
            </li>
            
            <!-- 리뷰 관리 -->
            <li class="dropdown">리뷰 관리
                <ul class="dropdown-menu">
                    <li><a href="review_list.html">리뷰 목록</a></li>
                    <li><a href="review_stats.html">리뷰 통계</a></li>
                </ul>
            </li>
            
            <!-- 관리자 관리 -->
            <li class="dropdown">관리자 관리
                <ul class="dropdown-menu">
                    <li><a href="admin_list.html">관리자 목록</a></li>
                    <li><a href="admin_role.html">역할 관리</a></li>
                    <li><a href="admin_logs.html">시스템 로그</a></li>
                </ul>
            </li>

            <!-- 이벤트 관리 -->
            <li class="dropdown">이벤트 관리
                <ul class="dropdown-menu">
                    <li><a href="event_list.html">이벤트 목록</a></li>
                    <li><a href="event_add.html">이벤트 추가</a></li>
                    <li><a href="coupon_list.html">쿠폰 목록</a></li>
                    <li><a href="email_log.html">이메일 발송 기록</a></li>
                </ul>
            </li>

            <!-- 로그아웃 -->
            <li><a href="logout.html">로그아웃</a></li>
        </ul>
    </div>
</nav>