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
<h1><c:out value="${Title }"></c:out></h1>
<sforms:form action="${pageContext.request.contextPath}/regEmployee/raiseATArrovalRequest" method="POST">
	<br>
	Select from whom you want to take permission<br><br>
	<input type="radio" name="approver" value="${username }">${username }<br>
	<input type="radio" name="approver" value="systemAdmin" checked="checked">System Admin
	<br><br>
	The request will be raised with desc as : <br>
	<textarea rows="4" cols="50" name="desc" >
		<c:out value="${desc}"></c:out>
	</textarea>
	<input type="hidden" value="${type }" name="type">
	<input type="hidden" value="${username }" name="username">
	<input type="submit" value="Raise Request">
</sforms:form>
</body>
</html>