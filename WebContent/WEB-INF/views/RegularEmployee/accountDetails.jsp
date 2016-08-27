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
<h1>Account Details</h1>
<table>
	<tr>
		<td>First Name   :    </td><td><c:out value="${UserData.firstname }"></c:out></td>
	</tr>
	<tr>
		<td>Last Name     : </td><td><c:out value="${UserData.firstname }"></c:out></td>
	</tr>
	<tr>
		<td>Contact     : </td><td><c:out value="${UserData.contact }"></c:out></td>
	</tr>
	<tr>
		<td>Email     : </td><td><c:out value="${UserData.email }"></c:out></td>
	</tr>
	<tr>
		<td>Address     : </td><td><c:out value="${UserData.address }"></c:out></td>
	</tr>
</table>
<br><br>
<a href="${pageContext.request.contextPath}/userHome">Home</a>
</body>
</html>