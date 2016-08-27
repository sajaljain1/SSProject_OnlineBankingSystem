<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ page session="false"%>
<html>
<head>
<title>User Home Page</title>
<style type="text/css">
#maindiv {
	width: 100%;
	overflow: hidden;
}

#leftdiv {
	width: 20%;
	float: left;
}

#rightdiv {
	width: 70%;
	float: left;
}

li {
	/* border-style: solid;
	border-width: 1px; */
	
}
</style>
<script type="text/javascript">
	
</script>
</head>
<body>
	<div id="maindiv">
		<div id="leftdiv">
			<h3>Perform Operations</h3>
			<ul>
				<li><a href="<c:url value='/Debit' />">Debit</a></li>
				<li><a href="<c:url value='/Credit' />">Credit</a></li>
				<li><a href="#">Transfer funds</a></li>
			</ul>
		</div>
		<div id="rightdiv">
			<div id="accountdetails">
				<h3>Account Details</h3>

				<c:if test="${!empty listAccounts}">
					<table class="tg">
						<tr>
							<th width="90">Account ID</th>
							<th width="120">Account Type</th>
							<th width="80">Balance</th>
						</tr>
						<c:forEach items="${listAccounts}" var="account">
							<tr>
								<td>${account.accID}</td>
								<td>${account.acctype}</td>
								<td>${account.balance}</td>
							</tr>
						</c:forEach>
					</table>
				</c:if>
			</div>
			<h3>Account Transaction Details</h3>

			<c:if test="${!empty listAccounts}">
				<table class="tg">
					<tr>
						<th width="90">Account ID</th>
						<th width="120">Account Type</th>
						<th width="80">Balance</th>
					</tr>
					<c:forEach items="${listAccounts}" var="account">
						<tr>
							<td>${account.accID}</td>
							<td>${account.acctype}</td>
							<td>${account.balance}</td>
						</tr>
					</c:forEach>
				</table>
			</c:if>
			<label id="errorText"></label>

		</div>
	</div>
</body>
</html>
