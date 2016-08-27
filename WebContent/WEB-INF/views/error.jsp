<%@ taglib prefix="sec"
	uri="http://www.springframework.org/security/tags"%>
<%@page session="false"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<!DOCTYPE html>
<html lang="en" class="no-js">
<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<title>Secure Banking System</title>

<meta name="description" content="">
<meta name="viewport" content="width=device-width, initial-scale=1.0">

<link rel="shortcut icon"
	href="<c:url value="/resources/img/favicon.png" />">
<link rel="apple-touch-icon" sizes="57x57"
	href="<c:url value="/resources/img/apple-touch-icon.png" />">
<link rel="apple-touch-icon" sizes="72x72"
	href="<c:url value="/resources/img/apple-touch-icon-72x72.png" />">
<link rel="apple-touch-icon" sizes="114x114"
	href="<c:url value="/resources/img/apple-touch-icon-114x114.png" />">

<link rel="stylesheet" href="<c:url value="/resources/css/style.css" />">
<script src="<c:url value="/resources/js/jquery-1.10.2.min.js" />"></script>
</head>
<body>

	<div>
	 Looks like an error has occurred but we will not allow you to see the stacktrace. Kindly go back to the previous page while we try and fix this.
	 </br>
		<a href="${pageContext.request.contextPath}/userHome">Click here</a>	to go to your home page.
		
	</div>

	<!--Links seen only by admin  -->



</body>
</html>

<%-- <%@ page isErrorPage="true" import="java.io.*" contentType="text/plain"%>

Message:
<%=exception.getMessage()%>

StackTrace:
<%
	StringWriter stringWriter = new StringWriter();
	PrintWriter printWriter = new PrintWriter(stringWriter);
	exception.printStackTrace(printWriter);
	out.println(stringWriter);
	printWriter.close();
	stringWriter.close();
%> --%>