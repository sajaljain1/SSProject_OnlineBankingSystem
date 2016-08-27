<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@page session="true"%>
<html>
<body>
	<h1>Title : ${title}</h1>
	<h1>Message : ${message}</h1>

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
	<table>
			<tr>
				<td><a href="${pageContext.request.contextPath}/requests">Requests</a></td>
			</tr>
			<tr>
				<td><a href="${pageContext.request.contextPath}/accountTransaction">Access Account/Transaction and create Transactions</a></td>
			</tr>		
			<tr>
				<td><a href="${pageContext.request.contextPath}/sysManager/registeruser">Register a new user</a></td>
			</tr>	
			<tr><td><a href="${pageContext.request.contextPath}/updateProfile">Profile</td></tr>
		</tr>
	</table>
	<a href="${pageContext.request.contextPath}/welcome">Home</a>
</body>
</html>