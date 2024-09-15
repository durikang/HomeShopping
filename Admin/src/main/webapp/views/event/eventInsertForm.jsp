<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>이벤트 등록</title>
    <script type="text/javascript">
        document.addEventListener('DOMContentLoaded', function() {
            const eventForms = document.querySelectorAll('.event-form');

            document.querySelectorAll('input[name="eventType"]').forEach(radio => {
                radio.addEventListener('change', function() {
                    eventForms.forEach(form => form.style.display = 'none');
                    document.getElementById(`${this.value}Form`).style.display = 'block';
                });
            });
        });
    </script>
</head>
<body>

<h2>이벤트 등록</h2>

<form action="eventInsertOk.do" method="post" enctype="multipart/form-data">
    <!-- 공통 이벤트 정보 -->
    <h3>기본 이벤트 정보</h3>
    <label>이벤트 이름: <input type="text" name="eventName" required></label><br>
    <label>이벤트 설명: <textarea name="eventDescription" required></textarea></label><br>
    <label>시작 날짜: <input type="date" name="startDate" required></label><br>
    <label>종료 날짜: <input type="date" name="endDate" required></label><br>

    <!-- 이벤트 유형 선택 -->
    <h3>이벤트 유형 선택</h3>
    <label>
        <input type="radio" name="eventType" value="general" checked> 일반 이벤트
    </label>
    <label>
        <input type="radio" name="eventType" value="banner"> 베너 이벤트
    </label>
    <label>
        <input type="radio" name="eventType" value="coupon"> 쿠폰 이벤트
    </label>

    <!-- 일반 이벤트 폼 (기본적으로 표시됨) -->
    <div id="generalForm" class="event-form" style="display:block;">
        <p>일반 이벤트는 추가 정보가 필요하지 않습니다.</p>
    </div>

    <!-- 베너 이벤트 폼 -->
    <div id="bannerForm" class="event-form" style="display:none;">
        <h3>베너 이벤트</h3>
        <label>배너 링크 URL: <input type="text" name="linkUrl"></label><br>
        <label>배너 이미지: <input type="file" name="bannerImage"></label><br>
    </div>

    <!-- 쿠폰 이벤트 폼 -->
    <div id="couponForm" class="event-form" style="display:none;">
        <h3>쿠폰 이벤트</h3>
        <label>쿠폰 코드: <input type="text" name="couponCode"></label><br>
        <label>할인 금액: <input type="number" name="discountAmount"></label><br>
        <label>할인율: <input type="number" name="discountPercent"></label><br>
        <label>쿠폰 만료 날짜: <input type="date" name="expiryDate"></label><br>
    </div>

    <!-- 등록 버튼 -->
    <input type="submit" value="이벤트 등록">
</form>

</body>
</html>
