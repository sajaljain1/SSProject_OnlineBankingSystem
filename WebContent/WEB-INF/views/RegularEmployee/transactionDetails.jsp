<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="sforms" uri="http://www.springframework.org/tags/form" %>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Transactions of User</title>
</head>
<body>
<%@ include file="../header.jsp" %>
<table>
	<tr>
		<td>transactionId</td><td>time of transaction</td><td>account to</td>
		<td>account from</td><td>amount</td><td>type</td>
	</tr>
	<c:forEach items="${transactions }" var="trans">
		<tr>
			<td>
				<c:out value="${trans.transactionId }"></c:out>
			</td>
			<td>
				<c:out value="${trans.created_at }"></c:out>
			</td>
			<td>
				<c:out value="${trans.account_to }"></c:out>
			</td>
			<td>
				<c:out value="${trans.account_from }"></c:out>
			</td>
			<td>
				<c:out value="${trans.amount }"></c:out>
			</td>
			<td>
				<c:out value="${trans.type }"></c:out>
			</td>
		</tr>
	</c:forEach>
</table>
<br>
<a href="${pageContext.request.contextPath}/userHome">Home</a>
</body>
</html>