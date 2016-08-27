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
	
	<c:if test="${empty requests }">
		<h1>There are no requests raised by you</h1>
	</c:if>
	<c:if test="${not empty requests }">
		<h1>These are requests raised by you</h1>
		<table border="1">
			<tr>
				<td>RequestId</td><td>Type</td><td>Desc</td>
				<td>Approved</td><td>Status</td>
			</tr>
			<c:forEach items="${requests }" var="request">
				<tr>
					<td><c:out value="${request.requetId }"></c:out></td>
					<td><c:out value="${request.type }"></c:out></td>
					<td><c:out value="${request.remarks }"></c:out></td>
					<td>
						<c:if test="${request.approved == '1'}">
							Approved
						</c:if>
						<c:if test="${request.approved == '0'}">
							Not Approved
						</c:if>
					</td>
					<td>
						<c:if test="${request.pending == '1'}">
							Pending
						</c:if>
						<c:if test="${request.pending == '0'}">
							Completed
						</c:if>
					</td>
				</tr>
			</c:forEach>
		</table>
	</c:if>
	<br><br>
<a href="${pageContext.request.contextPath}/userHome">Home</a>
</body>
</html>