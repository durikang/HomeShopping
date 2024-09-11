<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<script type="text/javascript">
// JavaScript 함수: 클릭한 행의 ID와 userType을 가져와서 상세 페이지로 이동
function goToDetailPage(event) {
    const target = event.currentTarget;
    const No = target.getAttribute('data-id');
    const userType = target.getAttribute('data-user-type');
    const currentPage = ${pi.currentPage};  // pi.currentPage를 자바스크립트 변수로 설정
    const status = '${status}';
    const subtitle = '${param.subtitle}';

    if (No) {
        // 서버에 방문 기록 저장 요청
        const xhr = new XMLHttpRequest();
        xhr.open('POST', '${contextPath}/boardSaveVisit.do', true);
        xhr.setRequestHeader('Content-Type', 'application/x-www-form-urlencoded');
        
        xhr.send('userNo=${sessionScope.user.userNo}&cart_no=' + No);
        
        // 페이지 이동
        window.location.href = '${contextPath}/boardDetailForm.do?cart_no=' + No + '&userType=' + userType + '&status=' + status + '&currentPage=' + currentPage +'&subtitle='+subtitle;
    }
}

// 모든 행에 클릭 이벤트 리스너 추가
document.addEventListener('DOMContentLoaded', function() {
    const rows = document.querySelectorAll('table tr[data-id]');
    rows.forEach(row => {
        row.addEventListener('click', goToDetailPage);
    });
    
    // 전체 선택 및 개별 선택 기능
    const selectAllCheckbox = document.getElementById('selectAll');
    if (selectAllCheckbox) {
        selectAllCheckbox.addEventListener('change', function() {
            const checkboxes = document.querySelectorAll('.deleteCheckbox');
            checkboxes.forEach(checkbox => {
                checkbox.checked = this.checked;
            });
        });
    }
    
});

</script>
<style type="text/css">


</style>
</head>
<body>
	<div align="title">
		<h3>장바구니 전체리스트</h3>
	</div>
	<div>
		
		<table width="300">
		
		<c:set var="list" value="${CartList }" />
			<tr>
				<th>장바구니 No.</th>
				<th>유저No.</th>
				<th>장바구니 생성일</th>
				<th>장바구니 삭제</th>
			</tr>
			
			<c:if test="${!empty list }">
				<c:forEach  items="${list }" var="dto" >
					<tr>
						<td><a href="cartItem_list.do?no=${dto.cart_no }">${dto.cart_no }</a></td>
						<td>${dto.user_no }</td>
						<td>
							<fmt:formatDate value="${dto.created_at }"/>
						</td>
						<td>											
							<input type="button" value="삭제" onclick="if(confirm('정말로 게시글을 삭제하시겠습니까?')){
											location.href='cart_delete.do?no=${dto.cart_no}'} else{return;}">
						</td>
					</tr>
				</c:forEach>
			</c:if>
				<c:if test="${empty list }">
				<tr>
					<td colspan="5" align="center">
						<h3>장바구니가 비어있습니다.</h3>
					</td>
					</tr>
				</c:if>
			
		</table>
		
	</div>
</body>
</html>