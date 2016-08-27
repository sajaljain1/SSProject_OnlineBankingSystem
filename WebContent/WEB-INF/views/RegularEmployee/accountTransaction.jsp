<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="sforms" uri="http://www.springframework.org/tags/form" %>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Search user</title>
<script type="text/javascript">
function fnValidate(form){
	if(document.getElementById("firstname").valueOf() == "" || document.getElementById("lastname").valueOf() == ""){
		alert("Please fill all the details for user you want to search");
		return;
	}else{
		form.submit();
	}
	
}
</script>
</head>
<body>
<h1 style="color: blue">Access account and transaction</h1>
<c:if test="${role == 'ROLE_REGULAR_EMP' }">
	<sforms:form id="searchForm" action="${pageContext.request.contextPath}/regEmployee/searchUserByName" method="POST">
		Enter the details of user you want to look for : 
			First Name* : <input id="firstname" type="text" name="firstname">
			Last Name* : <input id="lastname" type="text" name="lastname">
		<input type="submit" value="Search User" onclick="fnValidate(this)">
	</sforms:form>
</c:if>
<c:if test="${role == 'ROLE_SYS_MANAGER' }">
	<sforms:form id="searchForm" action="${pageContext.request.contextPath}/regEmployee/searchUserByName" method="POST">
		Enter the details of user you want to look for : 
			First Name* : <input id="firstname" type="text" name="firstname">
			Last Name* : <input id="lastname" type="text" name="lastname">
		<input type="submit" value="Search User" onclick="fnValidate(this)">
	</sforms:form>
</c:if>
<br><br><br><br>
<a href="${pageContext.request.contextPath}/userHome">Home</a>
</body>
</html>