<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="sforms" uri="http://www.springframework.org/tags/form" %>
<div>
<a href="${pageContext.request.contextPath}/userHome">Home</a>
<br><br>
<h1 style="color: blue; position: center">Requests approval page</h1>
	<c:if test="${ empty requestTransactions &&  empty requestUserDataRegEmployee &&  empty merchantRequests
					&&  empty requestUserData &&  empty profilePermissionRequests &&  empty transactionPermissionRequests &&  
					empty criticalTansactionRequests}">
		<h2>There are no requests assigned to you  as of now </h2>
	</c:if>
	<c:if test="${not empty assignedTo }">
		The request was assigned to<c:out value="${assignedTo }"></c:out>
	</c:if>
	<c:if test="${not empty successMessage }">
		<h3><c:out value="${successMessage }"></c:out></h3>
	</c:if>
	<c:if test="${not empty requestTransactions }">
	<c:forEach var="requestTransaction" items="${requestTransactions}">
		<h4>Requests related to Transaction</h4>
		<table border="1">
			<tr>
				<td>Request Id</td><td>Transaction Id</td><td>Raised by</td><td>type</td><td>amount</td>
			<tr>
			<tr>
				<td>
					${requestTransaction.request.requetId}
				</td>
				<td>
					${requestTransaction.transaction.transactionId}
				</td>
				<td>
					${requestTransaction.request.username}
				</td>
				<td>
					${requestTransaction.request.type}
				</td>
				<td>
					${requestTransaction.transaction.amount}
				</td>
				<td>
					<sforms:form action="${pageContext.request.contextPath}/regEmployee/approveTransaction" method="POST">
						<input type="hidden" value="${requestTransaction.transaction.transactionId}" name="transactionId">
						<input type="hidden" value="${requestTransaction.request.type}" name="requestType">
						<input type="hidden" value="${requestTransaction.transaction.type}" name="transactionType">
						<input type="hidden" value="${requestTransaction.transaction.amount}" name="amount">
						<input type="hidden" value="${requestTransaction.request.requetId}" name="requestId">
						<input type="hidden" value="${requestTransaction.request.username}" name="userName">
						<input type="hidden" value="${requestTransaction.transaction.account_to}" name="accountTo">
						<input type="hidden" value="${requestTransaction.transaction.account_from}" name="accountFrom">
						<input type="submit" name="submit" value="approve" onclick="fnConfirmApprove(this)">
					</sforms:form>
				</td>
				<td>
					<sforms:form action="${pageContext.request.contextPath}/regEmployee/denyTransaction" method="POST">
						<input type="hidden" value="${requestTransaction.transaction.transactionId}" name="transactionId">
						<input type="hidden" value="${requestTransaction.request.type}" name="requestType">
						<input type="hidden" value="${requestTransaction.transaction.type}" name="transactionType">
						<input type="hidden" value="${requestTransaction.transaction.amount}" name="amount">
						<input type="hidden" value="${requestTransaction.request.requetId}" name="requestId">
						<input type="hidden" value="${requestTransaction.request.username}" name="userName">
						<input type="hidden" value="${requestTransaction.transaction.account_to}" name="accountTo">
						<input type="hidden" value="${requestTransaction.transaction.account_from}" name="accountFrom">
						<input type="submit" name="submit" value="deny" onclick="fnConfirmApprove(this)">
					</sforms:form>
				</td>
			</tr>
		</table>
	</c:forEach>
	</c:if>
	<c:if test="${not empty requestUserDataRegEmployee }">
		<br><br>
		<h4>Profile update Request</h4>
		<table border = "1">
				<tr>
					<td>request Id</td><td>User Name</td><td>First Name</td><td>Last Name</td>
					<td>Remarks</td>
				</tr>
					<c:forEach items="${requestUserDataRegEmployee }" var="requestUser">
						<tr>
							<td>
							<c:out value="${requestUser.request.requetId }"></c:out>
							</td><td>
							<c:out value="${requestUser.request.username }"></c:out>
							</td><td>
							<c:out value="${requestUser.userdata.firstname }"></c:out>
							</td><td>
							<c:out value="${requestUser.userdata.lastname }"></c:out>
							</td>
							<td>
							<c:out value="${requestUser.request.remarks }"></c:out>
							</td>
							<td>
								<c:if test="${roleOfUser == 'ROLE_REGULAR_EMP'}">
									<sforms:form action="${pageContext.request.contextPath}/regularEmployee/askManager" method="POST">
										<input type="hidden" value="${requestUser.request.requetId }" name="requestId">
										<input type="submit" name="submit" value="Ask Manager" onclick="fnConfirmApprove(this)">
									</sforms:form>
								</c:if>
							</td>
							<td>
								<c:choose>
									<c:when test="${permitted == 'yes' }">
										<sforms:form action="${pageContext.request.contextPath}/workOnRequest" method="POST">
											<input type="hidden" value="${requestUser.request.requetId }" name="requestId">
											<input type="submit" name="submit" value="Work On request" onclick="fnConfirmApprove(this)">
										</sforms:form>
									</c:when>
									<c:otherwise>
										<sforms:form action="${pageContext.request.contextPath}/regularEmployee/askPermission" method="POST">
											<input type="hidden" value="${requestUser.request.requetId }" name="requestId">
											<input type="hidden" value="${requestUser.userdata.username }" name="username">
											<input type="submit" name="submit" value="Ask For Permission" onclick="fnConfirmApprove(this)">
										</sforms:form>
									</c:otherwise>
								</c:choose>
							</td>
						</tr>
					</c:forEach>
				
			</table>
	
	</c:if>
	
	<c:if test="${not empty merchantRequests }">
		<h4></h4>
		<c:forEach items="${merchantRequests }" var="requestTrans">
			<table border="1">
				<tr>
					<td>request Id</td><td>Type</td><td>Amount</td><td>Time</td>
				</tr>
				<tr>
					<td><c:out value="${requestTrans.request.requetId }"></c:out></td>
					<td><c:out value="${requestTrans.request.type }"></c:out></td>
					<td><c:out value="${requestTrans.transaction.amount }"></c:out></td>
					<td><c:out value="${requestTrans.transaction.created_at }"></c:out></td>
					<td>
						<c:if test="${roleOfUser == 'ROLE_REGULAR_EMP'}">
							<sforms:form action="${pageContext.request.contextPath}/regularEmployee/approveMerchantTransaction" method="POST">
								<input type="hidden" value="${requestTrans.request.requetId}" name="requestId">
								<input type="hidden" value="${requestTrans.request.transactionId}" name="transactionId">
								<input type="submit" name="submit" value="approve">
							</sforms:form>
						</c:if>
						<c:if test="${roleOfUser == 'ROLE_NORMAL_USER'}">
							<sforms:form action="${pageContext.request.contextPath}/normalUser/approveMerchantTransaction" method="POST">
								<input type="hidden" value="${requestTrans.request.requetId}" name="requestId">
								<input type="hidden" value="${requestTrans.request.transactionId}" name="transactionId">
								<input type="submit" name="submit" value="approve">
							</sforms:form>
						</c:if>
					</td>
					<td>
						
						<c:if test="${roleOfUser == 'ROLE_NORMAL_USER'}">
							<sforms:form action="${pageContext.request.contextPath}/normalUser/denyMerchantTransaction" method="POST">
								<input type="hidden" value="${requestTrans.request.requetId}" name="requestId">
								<input type="hidden" value="${requestTrans.request.transactionId}" name="transactionId">
								<input type="submit" name="submit" value="deny" onclick="fnConfirmApprove(this)">
							</sforms:form>
						</c:if>
						
					</td>
				</tr>
				
			</table>
		</c:forEach>
	</c:if>
	
	<c:if test="${not empty requestUserData }">
		
			<table border = "1">
				<tr>
					<td>request Id</td><td>User Name</td><td>First Name</td><td>Last Name</td>
					<td>Updated First Name</td><td>Updated Last Name</td><td>Remarks</td>
				</tr>
				
					<c:forEach items="${requestUserData }" var="requestUser">
						<tr>
							<td>
							<c:out value="${requestUser.request.requetId }"></c:out>
							</td><td>
							<c:out value="${requestUser.request.username }"></c:out>
							</td><td>
							<c:out value="${requestUser.userdata.firstname }"></c:out>
							</td><td>
							<c:out value="${requestUser.userdata.lastname }"></c:out>
							</td><td>
							<c:out value="${requestUser.request.updatedFirstName }"></c:out>
							</td>
							<td>
							<c:out value="${requestUser.request.updatedLastName }"></c:out>
							</td>
							<td>
							<c:out value="${requestUser.request.remarks }"></c:out>
							</td>
							<td>
								<sforms:form action="${pageContext.request.contextPath}/systemManager/delegateTask" method="POST">
									<input type="hidden" value="${requestUser.request.requetId }" name="requestId">
									<input type="submit" name="submit" value="delegate" onclick="fnConfirmApprove(this)">
								</sforms:form>
							</td>
							<td>
								<sforms:form action="${pageContext.request.contextPath}/workOnRequest" method="POST">
									<input type="hidden" value="${requestUser.request.requetId }" name="requestId">
									<input type="submit" name="submit" value="Work On request" onclick="fnConfirmApprove(this)">
								</sforms:form>
							</td>
						</tr>
					</c:forEach>
				
			</table>
		
	
	</c:if>
	<c:if test="${not empty profilePermissionRequests}">
		<h4>Profile permission requests</h4>
		<table border="1">
			<tr>
				<td>requestId</td><td>Permission asked by</td><td>Permission asked on account</td>
				<td>desc of request</td>
			</tr>
			<c:forEach items="${profilePermissionRequests }" var="reqs">
			<tr>
				<td>
					<c:out value="${reqs.requetId }"></c:out>
				</td>
				<td>
					<c:out value="${reqs.permissionto}"></c:out>
				</td>
				<td>
					<c:out value="${reqs.username }"></c:out>
				</td>
				<td>
					<c:out value="${reqs.remarks }"></c:out>
				</td>
				<td>
					<sforms:form action="${pageContext.request.contextPath}/approveAccountPermission" method="POST">
						<input type="hidden" value="${reqs.requetId }" name="requestId">
						<input type="hidden" value="${reqs.permissionto }" name="permissionto">
						<input type="hidden" value="${reqs.username }" name="username">
						<input type="submit" value="approve">
					</sforms:form>
				</td>
				<td>
					<sforms:form action="${pageContext.request.contextPath}/denyAccountPermission" method="POST">
						<input type="hidden" value="${reqs.requetId }" name="requestId">
						<input type="submit" value="deny">
					</sforms:form>
				</td>
			</tr>
			</c:forEach>
		</table>	
	</c:if>
	
	<c:if test="${not empty transactionPermissionRequests }">
		<h2>Requests for viewing transactions </h2>
		<table border=1>
			<tr>
				<td>Request Id</td><td>Permission on account</td><td>Permission given to</td>
				<td>type</td><td>remarks</td>
			</tr>	
			<c:forEach items="${transactionPermissionRequests}" var="request">
				<tr>
					<td><c:out value="${request.requetId }"></c:out></td>
					<td><c:out value="${request.username }"></c:out></td>
					<td><c:out value="${request.permissionto }"></c:out></td>
					<td><c:out value="${request.type }"></c:out></td>
					<td><c:out value="${request.remarks }"></c:out></td>
					<td>
						<sforms:form action="${pageContext.request.contextPath}/transactionRequest">
							<input type="hidden" value="${request.requetId }" name="requestId">
							<input type="hidden" value="approved" name="action">
							<input type="submit" value="approve">
						</sforms:form>
					</td>
					<td>
						<sforms:form action="${pageContext.request.contextPath}/transactionRequest">
							<input type="hidden" value="${request.requetId }" name="requestId">
							<input type="hidden" value="denied" name="acton">
							<input type="submit" value="deny">
						</sforms:form>
					</td>
				</tr>
			</c:forEach>
		</table>	
	</c:if>
	
	<c:if test="${not empty criticalTansactionRequests }">
			<table border="1">
				<tr>
					<td>Request Id</td><td>Transaction Id</td><td>Raised by</td><td>type</td><td>amount</td>
				<tr>
				<c:forEach var="requestTransaction" items="${criticalTansactionRequests}">
					<tr>
						<td>
							${requestTransaction.request.requetId}
						</td>
						<td>
							${requestTransaction.transaction.transactionId}
						</td>
						<td>
							${requestTransaction.request.username}
						</td>
						<td>
							${requestTransaction.request.type}
						</td>
						<td>
							${requestTransaction.transaction.amount}
						</td>
						<td>
							<sforms:form action="${pageContext.request.contextPath}/systemManager/approveCriticalTransaction" method="POST">
								<input type="hidden" value="${requestTransaction.transaction.transactionId}" name="transactionId">
								<input type="hidden" value="${requestTransaction.request.type}" name="type">
								<input type="hidden" value="${requestTransaction.transaction.amount}" name="amount">
								<input type="hidden" value="${requestTransaction.request.requetId}" name="requestId">
								<input type="hidden" value="${requestTransaction.request.username}" name="userName">
								<input type="hidden" value="${requestTransaction.transaction.account_to}" name="accountTo">
								<input type="hidden" value="${requestTransaction.transaction.account_from}" name="accountFrom">
								<input type="hidden" value="${requestTransaction.transaction.type}" name="transactionType">
								<input type="submit" name="submit" value="approve" onclick="fnConfirmApprove(this)">
							</sforms:form>
						</td>
						<td>
							<sforms:form action="${pageContext.request.contextPath}/systemManager/denyCriticalTransaction" method="POST">
								<input type="hidden" value="${requestTransaction.transaction.transactionId}" name="transactionId">
								<input type="hidden" value="${requestTransaction.request.type}" name="type">
								<input type="hidden" value="${requestTransaction.transaction.amount}" name="amount">
								<input type="hidden" value="${requestTransaction.request.requetId}" name="requestId">
								<input type="hidden" value="${requestTransaction.request.username}" name="userName">
								<input type="hidden" value="${requestTransaction.transaction.account_to}" name="accountTo">
								<input type="hidden" value="${requestTransaction.transaction.account_from}" name="accountFrom">
								<input type="hidden" value="${requestTransaction.transaction.type}" name="transactionType">
								<input type="submit" name="submit" value="deny" onclick="fnConfirmApprove(this)">
								
							</sforms:form>
						</td>
					</tr>
				</c:forEach>
			</table>
</c:if>
<c:if test="${not empty deletionRequests }">
	Requests for Account deletion
	<table border="1">
		<tr>
			<td>RequestId</td><td>type</td><td>username</td>
		</tr>
		<c:forEach items="${deletionRequests }" var="request">
			<tr>
				<td>
					<c:out value="${request.requetId }"></c:out>
				</td>	
				<td>
					<c:out value="${request.type }"></c:out>
				</td>
				<td>
					<c:out value="${request.username }"></c:out>
				</td>
				<td>
					<sforms:form action="${pageContext.request.contextPath}/systemManager/accountDelete" method="POST">
						<input type="hidden" name="username" value="${request.username }">
						<input type="hidden" name="requestId" value="${request.requetId }">
						<input type="hidden" name="action" value="approve">
						<input type="submit" value="approve">					
					</sforms:form>
				</td>
				<td>
					<sforms:form action="${pageContext.request.contextPath}/systemManager/accountDelete" method="POST">
						<input type="hidden" name="username" value="${request.username }">
						<input type="hidden" name="requestId" value="${request.requetId }">
						<input type="hidden" name="action" value="deny">
						<input type="submit" value="deny">					
					</sforms:form>
				</td>
			</tr>
		</c:forEach>
	</table>

</c:if>
</div>