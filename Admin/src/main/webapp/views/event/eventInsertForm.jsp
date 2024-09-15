<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>이벤트 등록</title>
    <link rel="stylesheet" href="${contextPath}/resources/master.css">

    <style>
        h2 {
            margin-bottom: 20px;
            color: #333;
            font-size: 28px;
            text-align: center;
        }
        .event-form {
            display: none;
        }

        .event-form.active {
            display: block;
        }

        small {
            font-size: 12px;
            color: #888;
        }

        /* 숨겨진 상태의 알람 메세지 */
        .alertMessage {
            display: none;
			font-size: 14px;
		    margin-top: 15px;
		    margin-bottom: 15px;
        }

        /* 긍정의 메세지 */
        .alert-success {
            color: green;
        }

        /* 부정의 메세지 */
        .alert-error {
            color: red;
        }
    </style>

    <script type="text/javascript">
    document.addEventListener('DOMContentLoaded', function() {
        const eventForms = document.querySelectorAll('.event-form');
        const alertMessageElement = document.querySelector('.alertMessage');

        // 이벤트 유형 변경 시 폼 변경
        document.querySelectorAll('input[name="eventType"]').forEach(function(radio) {
            radio.addEventListener('change', function() {
                eventForms.forEach(function(form) {
                    form.style.display = 'none';
                });
                document.getElementById(this.value + "Form").style.display = 'block';
            });
        });

        // 쿠폰 코드 자동 생성 및 중복 체크
        document.querySelector('input[name="couponCode"]').addEventListener('click', function() {
            generateCouponCode(this);
        });

        function generateCouponCode(inputElement) {
            const couponCode = 'COUPON-' + Math.random().toString(36).substr(2, 8).toUpperCase();
            
            fetch('checkCouponCode.do', {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/x-www-form-urlencoded'
                },
                body: `couponCode=${couponCode}`
            })
            .then(response => response.json())
            .then(data => {
                if (data.isDuplicate) {
                    // 부정의 메시지 표시
                    alertMessageElement.textContent = '쿠폰 코드가 중복되었습니다. 다른 코드를 생성하세요.';
                    alertMessageElement.className = 'alertMessage alert-error';
                    alertMessageElement.style.display = 'block';
                    generateCouponCode(inputElement);  // 다시 쿠폰 코드 생성
                } else {
                    // 긍정의 메시지 표시
                    alertMessageElement.textContent = '쿠폰 코드를 사용할 수 있습니다.';
                    alertMessageElement.className = 'alertMessage alert-success';
                    alertMessageElement.style.display = 'block';
                    inputElement.value = couponCode;  // 쿠폰 코드 필드에 값 설정
                }
            })
            .catch(error => {
                console.error('에러 발생:', error);
            });
        }

        // 배너 이미지 크기 및 형식 유효성 검사
        document.querySelector('input[name="bannerImage"]').addEventListener('change', function(event) {
            const bannerImage = event.target.files[0];
            const img = new Image();

            img.src = URL.createObjectURL(bannerImage);
            img.onload = function() {
                if (img.width !== 1400 || img.height !== 150) {
                    alert('이미지 크기가 1400x150 픽셀이어야 합니다.');
                    event.target.value = '';  // 잘못된 파일을 제거
                    return;
                }
                const fileSizeMB = bannerImage.size / (1024 * 1024);
                if (fileSizeMB > 2) {
                    alert('이미지 크기는 2MB를 초과할 수 없습니다.');
                    event.target.value = '';  // 잘못된 파일을 제거
                    return;
                }

                const allowedTypes = ['image/jpeg', 'image/png', 'image/gif'];
                if (!allowedTypes.includes(bannerImage.type)) {
                    alert('JPEG, PNG 또는 GIF 형식의 이미지만 업로드할 수 있습니다.');
                    event.target.value = '';  // 잘못된 파일을 제거
                    return;
                }
            };
        });

        // 폼 제출 시 유효성 검사
        document.querySelector('form').addEventListener('submit', function(event) {
            const eventType = document.querySelector('input[name="eventType"]:checked').value;

            // 날짜 유효성 검사 추가
            const startDate = document.querySelector('input[name="startDate"]').value;
            const endDate = document.querySelector('input[name="endDate"]').value;

            if (startDate && endDate) {
                if (new Date(startDate) > new Date(endDate)) {
                    alert('시작 날짜는 종료 날짜보다 이후일 수 없습니다.');
                    event.preventDefault();
                    return;
                }
            }
            
            if (eventType === 'coupon') {
                const discountAmount = document.querySelector('input[name="discountAmount"]').value;
                const discountPercent = document.querySelector('input[name="discountPercent"]').value;

                if (!discountAmount && !discountPercent) {
                    alert('할인 금액 또는 할인율 중 하나는 입력해야 합니다.');
                    event.preventDefault();
                    return;
                }
            }
        });
    });
    </script>
</head>
<body>

    <div class="content">
        <h2>이벤트 등록</h2>

        <form action="eventInsertOk.do" method="post" enctype="multipart/form-data">
            <!-- 공통 이벤트 정보 -->
            <div class="form-group">
                <label>이벤트 이름:</label>
                <input type="text" name="eventName" required>
            </div>

            <div class="form-group">
                <label>이벤트 설명:</label>
                <textarea name="eventDescription" required></textarea>
            </div>

            <div class="form-group">
                <label>시작 날짜:</label>
                <input type="date" name="startDate" required>
            </div>

            <div class="form-group">
                <label>종료 날짜:</label>
                <input type="date" name="endDate" required>
            </div>

            <!-- 이벤트 유형 선택 -->
            <h3>이벤트 유형 선택</h3>
            <div class="form-group">
                <label>
                    <input type="radio" name="eventType" value="general" checked> 일반 이벤트
                </label>
                <label>
                    <input type="radio" name="eventType" value="banner"> 베너 이벤트
                </label>
                <label>
                    <input type="radio" name="eventType" value="coupon"> 쿠폰 이벤트
                </label>
            </div>

            <!-- 일반 이벤트 폼 -->
            <div id="generalForm" class="event-form active">
                <p>일반 이벤트는 추가 정보가 필요하지 않습니다.</p>
            </div>

            <!-- 베너 이벤트 폼 -->
            <div id="bannerForm" class="event-form">
                <h3>베너 이벤트</h3>
                <div class="form-group">
                    <label>배너 링크 URL:</label>
                    <input type="text" name="linkUrl">
                </div>
                <div class="form-group">
                    <label>배너 이미지:</label>
                    <input type="file" name="bannerImage" accept="image/jpeg, image/png, image/gif">
                </div>
                <small>이미지 크기는 1400x150 픽셀이어야 하며, 최대 크기는 2MB입니다.</small>
            </div>

            <!-- 쿠폰 이벤트 폼 -->
            <div id="couponForm" class="event-form">
                <h3>쿠폰 이벤트</h3>
                <div class="form-group">
                    <label>쿠폰 코드:</label>
                    <input type="text" name="couponCode" placeholder="자동 생성하려면 클릭하세요">
                </div>
                <div class="alertMessage">
                    <!-- 중복 체크 결과 메시지가 여기에 표시됨 -->
                </div>
                <div class="form-group">
                    <label>할인 금액:</label>
                    <input type="number" name="discountAmount" placeholder="금액을 입력하거나">
                </div>
                <div class="form-group">
                    <label>할인율:</label>
                    <input type="number" name="discountPercent" placeholder="할인율을 입력하세요">
                </div>
                <div class="form-group">
                    <label>쿠폰 만료 날짜:</label>
                    <input type="date" name="expiryDate">
                </div>
            </div>

            <!-- 등록 버튼 -->
            <input class="btn" type="submit" value="이벤트 등록">
        </form>
    </div>

</body>
</html>
