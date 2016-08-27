<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="sforms" uri="http://www.springframework.org/tags/form" %>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
</head>
<body>
	<h1>Message</h1>
	<c:if test="${not empty successMessageRequest }">
		<c:out value="${successMessageRequest }"></c:out><br>
		
	</c:if>
	<c:if test="${not empty successMessage }">
		<c:out value="${successMessage }"></c:out><br>
		
	</c:if>
	<c:if test="${not empty errorMessage }">
		<c:out value="${errorMessage }"></c:out><br>
		
	</c:if>
	<c:if test="${not empty assignedTo }">
		The request was assigned to <c:out value="${assignedTo }"></c:out><br>
		
	</c:if>
	

	<br><br><br><br>
<a href="${pageContext.request.contextPath}/userHome">Home</a>
</body>
</html>