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
<sforms:form action="${pageContext.request.contextPath}/regEmployee/raiseProfileRequest" method="POST">
	
	First Name : <input type="text" value="${user.firstname }" name="firstname"><br>
	Last Name : <input type="text" value="${user.lastname }" name="lastname"><br>
	Email : <input type="text" value="${user.email }"  name="emailid"><br>
	Address : <input type="text" value="${user.address }" name ="address"><br>
	Contact : <input type="text" value="${user.contact }" name="contact"><br>
	<input type="hidden" name="username" value="${user.username }">
	<input type="submit" value="Request Change">
</sforms:form>
</body>
</html>