<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>쇼핑몰 관리자 페이지</title>
<link rel="stylesheet" href="<%=request.getContextPath() %>/resources/index/css/index.css">
<script src="<%=request.getContextPath() %>/resources/index/js/index.js"></script>
</head>
<body>

<c:set var="contextPath" value="${ pageContext.servletContext.contextPath }" scope="application"/>

<script>
	window.onload = function(){
			location.href="${contextPath}/main.go";
		}
	/* test ver2 */
</script>

</body>
</html>