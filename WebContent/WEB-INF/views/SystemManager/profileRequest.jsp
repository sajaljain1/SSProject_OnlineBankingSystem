<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="sforms" uri="http://www.springframework.org/tags/form" %>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Approve profile request</title>
</head>
<body>
<h1>Request Id : <c:out value="${Request.requetId }"></c:out>
	 raised by <c:out value="${UserData.firstname }"></c:out>&nbsp;
	 <c:out value="${UserData.lastname }"></c:out></h1>
<table border="1">
	<tr>
		<td>
			<h3>Present User details</h3>
		</td>
		<td>
			<h3>Requested Details</h3>
		</td>
	</tr>
	<tr>
		<td>
			<table>
				<tr>
					<td>
						<c:out value="${UserData.firstname}"></c:out><br>
						<c:out value="${UserData.lastname}"></c:out><br>
						<c:out value="${UserData.email }"></c:out><br>
						<c:out value="${UserData.contact }"></c:out><br>
						<c:out value="${UserData.address }"></c:out><br>
					</td>		
				</tr>
			</table>
		</td>
		<td>	
			<table>
				<tr>
					<td>
						<c:if test="${not empty Request.updatedFirstName}">First Name : <c:out value="${Request.updatedFirstName }"></c:out><br></c:if>
						<c:if test="${not empty Request.updatedLastName}">Last Name : <c:out value="${Request.updatedLastName }"></c:out><br></c:if>
						<c:if test="${not empty Request.updatedEmail}">Email : <c:out value="${Request.updatedEmail }"></c:out><br></c:if>
						<c:if test="${not empty Request.updatedPhone}">Phone : <c:out value="${Request.updatedPhone }"></c:out><br></c:if>
						<c:if test="${not empty Request.updatedAddress}">Address : <c:out value="${Request.updatedAddress }"></c:out><br></c:if>
					</td>
				</tr>
			</table>
		</td>
	</tr>
</table>
<table>
	<tr>
		<td>
			<sforms:form action="${pageContext.request.contextPath}/systemManager/approveProfileChange">
			<input type="hidden" value="${Request.requetId }" name="requestId">
				<input type="submit" value="approve" >
			</sforms:form>
		</td>
		<td>
			<sforms:form action="${pageContext.request.contextPath}/systemManager/denyProfileChange" >
				<input type="hidden" value="${Request.requetId }" name="requestId">
				<input type="submit" value="deny" >
			</sforms:form>
		</td>
	</tr>

</table>
</body>
</html>