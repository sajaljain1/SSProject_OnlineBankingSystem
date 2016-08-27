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
	<c:if test="${not empty successMessage }">
		<c:out value="${successMessage }"></c:out>
	</c:if>
	<c:if test="${not empty errorMessage }">
		<c:out value="${errorMessage }"></c:out>
	</c:if>
	<h1>User Details</h1><br><br>
	<h2>User details</h2>
	<h3>First Name : <c:out value="${firstname }"></c:out></h3>
	<h3>First Name : <c:out value="${lastname }"></c:out></h3><br><br>
	<c:if test="${not empty accounts }">
		<h2>Account details</h2>
		<ul>
			<c:forEach items="${accounts }" var="accout">
				<li>
					<c:out value="${accout }"></c:out>
				</li>
			</c:forEach>
		</ul>
	</c:if>
	<table>
		<tr>
			<td>
	<c:choose>
		<c:when test="${role == 'ROLE_REGULAR_EMP' }">
			<c:choose>
				<c:when test="${accountPermission == 'yes' }">
						<sforms:form action="${pageContext.request.contextPath}/regularEmployee/viewAccountDetails" method="POST">
							<input type="hidden" value="${username }" name="username">
							<input type="submit" name="submit" value="View Account Details">
						</sforms:form>
					</c:when>
					<c:otherwise>
						<sforms:form action="${pageContext.request.contextPath}/regularEmployee/askAccountPermission" method="POST">
							<input type="hidden" value="${username }" name="username">
							<input type="hidden" value="Profile Permission" name="type">
							<input type="submit" name="submit" value="Ask For Permission for account access">
						</sforms:form>
					</c:otherwise>
			</c:choose>
		</c:when>
	</c:choose>
	</td>
	<td>
	<c:choose>
		
		<c:when test="${transactionPermission == 'yes' }">
		<table><tr><td>
			<sforms:form action="${pageContext.request.contextPath}/regularEmployee/viewTransactionDetails" method="POST">
					<input type="hidden" value="${username }" name="username">
					<input type="submit" name="submit" value="View Transaction Details">
			</sforms:form>
			</td><td>
			<c:if test="${role == 'ROLE_REGULAR_EMP' }">
				<sforms:form action="${pageContext.request.contextPath}/createTransactionForExternal" method="POST">
						<input type="hidden" value="${username }" name="username">
						<input type="hidden" value="${accounts }" name="accounts">
						<input type="submit" name="submit" value="Create transaction">
				</sforms:form>
			</c:if>
			</td>
			</tr>
			</table>
		</c:when>
		<c:otherwise>
			<sforms:form action="${pageContext.request.contextPath}/regularEmployee/askTransactionPermission" method="POST">
					<input type="hidden" value="${username }" name="username">
					<input type="hidden" value="Transaction Permission" name="type">
					<input type="submit" name="submit" value="Ask For Permission for Transaction access">
				</sforms:form>
		</c:otherwise>
	</c:choose>
	</td>
	</tr>
	</table>
</body>
</html>