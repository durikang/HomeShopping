<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
    <style>
        body {
            font-family: Arial, sans-serif;
            background-color: #f8f9fa;
        }

        .content {
        	width: 1000px;
            max-width: 1250px;
            margin: 50px auto;
            padding: 20px;
            background-color: #ffffff;
            border-radius: 8px;
            box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);
        }

        h3 {
            margin-bottom: 20px;
            color: #333;
            font-size: 28px;
            text-align: center;
        }

    </style>
<script type="text/javascript"></script>
</head>
<body>	
	<div align="center" class="content">
		<c:set var="dto" value="${Modify }" />

		
		<h3> 배송 상태 수정 페이지</h3>
		
		
		<br>
		<br>

		<%-- <form method="post" enctype="multipart/form-date" action="${contextPath}/orderModifyOk.do"> --%>
		<form method="post" action="${contextPath}/orderModifyOk.do">
		<%-- <input type="hidden" name="status" value="${dto.status }"> --%>
			<table border="1" class="form-group">
			<tr>
				<th>주문 번호</th>
				<th>주문 고객 번호</th>
				<th>주문 일자</th>
				<th>주문 상태</th>
				<th>총 주문 금액</th>
			</tr>
			
			
			<tr>
				<td><input name="order_no" readonly value="${dto.order_no }"></td>
				<td><input name="user_no" readonly value="${dto.user_no }"></td>
				<td><input name="order_date" readonly value="${dto.order_date }"></td>
				<td>
				<!-- 
					<select name="status" >
			            <option value="PENDING">배송 대기</option>
			            <option value="SHIPPED">배송 중</option>
			            <option value="DELIVERED">배송 완료</option>
			            <option value="CANCELLED">배송 취소</option>
			        </select>
					 -->
					 <select name="status">
					    <option value="PENDING" ${dto.status == 'PENDING' ? 'selected' : ''}>배송 대기</option>
					    <option value="SHIPPED" ${dto.status == 'SHIPPED' ? 'selected' : ''}>배송 중</option>
					    <option value="DELIVERED" ${dto.status == 'DELIVERED' ? 'selected' : ''}>배송 완료</option>
					    <option value="CANCELLED" ${dto.status == 'CANCELLED' ? 'selected' : ''}>배송 취소</option>
					</select>
				</td>
				<td><input name="total_amount" readonly
					value="${dto.total_amount }"></td>
			</tr>
		
            <tr>
                <td colspan="5" align="center">
                    <input class="btn" type="submit" value="배송상태 수정">
                    <input class="btn" type="reset" value="초기화">
                </td>
            </tr>
		</table>
	
		<br>



		</form>



	</div>

</body>
</html>