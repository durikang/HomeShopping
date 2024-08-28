<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib prefix="c"  uri="http://java.sun.com/jsp/jstl/core"%>   
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<link href="${contextPath }/resources/master.css" rel ="stylesheet" type="text/css"/>
<title>관리지 페이지</title>
</head>
<body>
<%-- header 영역 --%>
<c:import url="common/menubar.jsp"/>

    <!-- Main 영역 -->
    <!-- 서블릿에서 전달된 URL에 따라 동적으로 JSP 페이지를 포함 -->
<c:choose>
    <c:when test="${not empty param.url}">
        <c:import url="${param.url}" />
        
    </c:when>
    <c:when test="${not empty url}">
        <c:import url="${url}" />
       	<!-- Include the pagination JSP -->
        <c:if test="${not empty pi }">
		    <jsp:include page="/views/common/pagination.jsp"/>
        </c:if>
    </c:when>
    <c:otherwise>
<%-- 이곳에 컨텐츠가 표시됩니다 --%>
<div style="padding: 20px;">
	<h1>관리자 페이지 대시보드</h1>
	<p>이곳에서 관리할 수 있는 다양한 항목들을 관리하세요.</p>
</div>
    </c:otherwise>
</c:choose>

</body>
</html>