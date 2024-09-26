<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>배송 정보</title>
    <script type="text/javascript">
    </script>
</head>
<body onload="toggleInputFields()">
    <div align="center">
        <h3>배송 정보</h3>        

        <table id="deliveryTable" border="1">
            <tr>
                <th>배송 번호</th>
                <th>주문 번호</th>
                <th>배송일</th>
                <th>배송 상태</th>
                <th>설정</th>
            </tr>

            <c:if test="${not empty List}">
                <c:forEach items="${List}" var="dto">
                    <tr>
                        <td>${dto.delivery_no}</td>
                        <td>${dto.order_no}</td>
                        <td>${dto.delivery_date}</td>
                        <td>
                            <c:choose>
                                <c:when test="${dto.delivery_status == 'PENDING'}">배송 대기</c:when>
                                <c:when test="${dto.delivery_status == 'SHIPPED'}">배송 중</c:when>
                                <c:when test="${dto.delivery_status == 'DELIVERED'}">배송 완료</c:when>
                                <c:when test="${dto.delivery_status == 'CANCELLED'}">배송 취소</c:when>
                            </c:choose>
                        </td>
                        <td>
                            <c:if test="${sessionScope.user.userType == 'ADMIN'}">
                                <input class="btn" type="button" value="배송상태설정" onclick="location.href='delivery_modify.do?no=${dto.order_no}'">
                            </c:if>
                        </td>
                    </tr>
                </c:forEach>
            </c:if>
            <c:if test="${empty List}">
                <tr>
                    <td colspan="5" align="center">
                        <h3>배송 목록이 없습니다.</h3>
                    </td>
                </tr>
            </c:if>
        </table>
    </div>
</body>
</html>
