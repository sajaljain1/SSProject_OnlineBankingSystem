<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@page session="true"%>
<html>
<body>
		<c:url value="/j_spring_security_logout" var="logoutUrl" />

	<!-- csrt for log out-->
	<form action="${logoutUrl}" method="post" id="logoutForm">
	  <input type="hidden" 
		name="${_csrf.parameterName}"
		value="${_csrf.token}" />
	</form>
	
	<script>
		function formSubmit() {
			document.getElementById("logoutForm").submit();
		}
	</script>

	<c:if test="${pageContext.request.userPrincipal.name != null}">
		<h2>
			Welcome : ${pageContext.request.userPrincipal.name} | <a
				href="javascript:formSubmit()"> Logout</a>
		</h2>
	</c:if>
<Table>
	<tr>
		<td><a href="${pageContext.request.contextPath}/requests">Requests</a></td>
	</tr>
	<tr>
		<td><a href="${pageContext.request.contextPath}/profileUpdate">Profile update</a></td>
	</tr>
	<tr>
		<td><a href="${pageContext.request.contextPath}/accountTransaction">Access Account/Transaction and create Transactions</a></td>
	</tr>
	<tr>
		<td><a href="${pageContext.request.contextPath}/requestStatus">Check Request Status</a></td>
	</tr>
	<br><br>
<a href="${pageContext.request.contextPath}/userHome">Home</a>
</Table>
</body>
</html>