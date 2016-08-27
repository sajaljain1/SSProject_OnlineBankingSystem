<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="sforms" uri="http://www.springframework.org/tags/form" %>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
<script type="text/javascript">
function fnShowHideComponents(type){
	//alert(type);
	if(type=='credit' || type=='debit'){
		document.getElementById("accountTo").style.visibility = "hidden";
	}else{
		document.getElementById("accountTo").style.visibility = "visible";
	}
}
</script>
</head>
<h1>Create transaction for external user <c:out value="${username }"></c:out></h1>
<body onload="fnShowHideComponents('credit')">
	<c:if test="${not empty successMessage }">
		<c:out value="${successMessage }"></c:out>
	</c:if>
	<c:if test="${not empty errorMessage }">
		<c:out value="${errorMessage }"></c:out>
	</c:if>
	<sforms:form action="${pageContext.request.contextPath}/regularEmployee/raiseTransaction" method="POST">
		<table>
			<tr>
				<td>
					Select the type of transaction :
					<input type="radio" name="type" value="credit" checked="checked" onclick="fnShowHideComponents('credit');">Credit
					<input type="radio" name="type" value="debit" onclick="fnShowHideComponents('debit');">Debit
					<input type="radio" name="type" value="transfer" onclick="fnShowHideComponents('transfer');">Transfer
				</td>
			</tr>
			<tr id="accountFrom">
				<td>
					Select user account : 
					<c:forEach items="${userAccounts }" var="accout">
								<input type="radio" value="${accout }"	name="selectedAccount" checked="checked">${accout }
					</c:forEach>
				</td>
			</tr>
			<tr id="accountTo">
				<td>
					Select Another account to transfer money to: <input type="text" name="account_to" value="0">
				</td>
			</tr>
			<tr>
				<td>
					Amount : <input type="text" name="amount">
				</td>
			</tr>
			<tr>
				<td>
					<input type="hidden" value="${username }" name="username">
					<input type="submit" value="create transaction">
				</td>
			</tr>
		</table>
	</sforms:form>
	<br><br>
<a href="${pageContext.request.contextPath}/userHome">Home</a>
</body>
</html>