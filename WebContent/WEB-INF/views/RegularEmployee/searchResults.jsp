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
<c:if test="${empty searchUserData }">
	<h1>There are no users by these details</h1>
</c:if>
<c:if test="${not empty searchUserData }">
	<table border="1">
		<tr>
			<td>First Name</td><td>last Name</td><td>Account 1</td>
			<td>Account 2</td><td>Creation Time</td>
		</tr>
		<c:forEach items="${searchUserData }" var="searchUser">
			<tr>
				<td>
					<c:out value="${searchUser.firstName }"></c:out>
				</td>
				<td>
					<c:out value="${searchUser.lastName }"></c:out>
				</td>
				<td>
					<c:out value="${searchUser.account1 }"></c:out>
				</td>
				<td>
					<c:out value="${searchUser.account2 }"></c:out>
				</td>
				<td>
					<c:out value="${searchUser.creationTime }"></c:out>
				</td>
				<c:if test="${role == 'ROLE_ADMIN' }">
					<td>
						<sforms:form action="${pageContext.request.contextPath}/regularEmployee/deleteAccount" method="POST">
								<input type="hidden" value="${searchUser.username }" name="username">
								<input type="submit" name="submit" value="Delete Account">
						</sforms:form>
					</td>
				</c:if>
				<td>
				<table>
			<tr>
			<td>
			<c:choose>
				<c:when test="${role == 'ROLE_REGULAR_EMP' }">
					<c:choose>
						<c:when test="${searchUser.accountPermission == 'yes' }">
								<sforms:form action="${pageContext.request.contextPath}/regularEmployee/viewAccountDetails" method="POST">
									<input type="hidden" value="${searchUser.username }" name="username">
									<input type="submit" name="submit" value="View Account Details">
								</sforms:form>
							</c:when>
							<c:otherwise>
								<sforms:form action="${pageContext.request.contextPath}/regularEmployee/askAccountPermission" method="POST">
									<input type="hidden" value="${searchUser.username }" name="username">
									<input type="hidden" value="Profile Permission" name="type">
									<input type="submit" name="submit" value="Ask For Permission for account access">
								</sforms:form>
							</c:otherwise>
					</c:choose>
				</c:when>
			</c:choose>
			</td>
			<td>
			<c:if test="${role == 'ROLE_REGULAR_EMP' || role == 'ROLE_SYS_MANAGER'   }">
			<c:choose>
				
				<c:when test="${searchUser.transactionPermission == 'yes' }">
				<table><tr><td>
					<sforms:form action="${pageContext.request.contextPath}/regularEmployee/viewTransactionDetails" method="POST">
							<input type="hidden" value="${searchUser.username }" name="username">
							<input type="submit" name="submit" value="View Transaction Details">
					</sforms:form>
					</td><td>
					<c:if test="${role == 'ROLE_REGULAR_EMP' }">
						<sforms:form action="${pageContext.request.contextPath}/createTransactionForExternal" method="POST">
								<input type="hidden" value="${searchUser.username }" name="username">
								<input type="submit" name="submit" value="Create transaction">
						</sforms:form>
					</c:if>
					</td>
					</tr>
					</table>
				</c:when>
				<c:otherwise>
					<sforms:form action="${pageContext.request.contextPath}/regularEmployee/askTransactionPermission" method="POST">
							<input type="hidden" value="${searchUser.username }" name="username">
							<input type="hidden" value="Transaction Permission" name="type">
							<input type="submit" name="submit" value="Ask For Permission for Transaction access">
						</sforms:form>
				</c:otherwise>
			</c:choose>
			</c:if>
	</td>
	</tr>
	</table>
	</td>
			</tr>
		</c:forEach>
	</table>
</c:if>
<br><br>
<a href="${pageContext.request.contextPath}/userHome">Home</a>
</body>
</html>