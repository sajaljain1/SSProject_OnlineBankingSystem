<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<head>
<link rel="stylesheet"
	href="//code.jquery.com/ui/1.11.4/themes/smoothness/jquery-ui.css">
<script src="//code.jquery.com/jquery-1.10.2.js"></script>
<script src="//code.jquery.com/ui/1.11.4/jquery-ui.js"></script>
<link rel="stylesheet" href="/resources/demos/style.css">
<script
	src="http://ajax.aspnetcdn.com/ajax/jquery.validate/1.11.1/jquery.validate.min.js"></script>
<style type="text/css">
</style>
</head>
<script type="text/javascript">
	
	  
	  function onclickVal(val){
		var formval = "#form"+val;
		  var $form = $(formval);
			var $inputs = $form.find("input, select, button, textarea");
			
			var serializedData =  $form.serialize();
			$inputs.prop("disabled", true);

			request = $.ajax({
				url : $(formval).attr("action"),
				type : "post",
				data : serializedData
			});
			
			request.done(function(response, textStatus, jqXHR) {
				$(".clearfix").html(response);
			});

			request.fail(function(jqXHR, textStatus, errorThrown) {
				console.error("The following error occured: " + textStatus);
			});

			request.always(function() {
				$inputs.prop("disabled", false);
			});
	  
	  }
	 
</script>


<c:if test="${!empty listTrans}">
	<table border="1px">
		<tr>
			<th width="80">Transaction Type</th>
			<th width="80">Amount</th>

			<th width="80">Status</th>
			<th width="80">Date</th>
			<th width="80">Action</th>
		</tr>
		<c:set var="count" value="0" scope="page" />
		<c:forEach items="${listTrans}" var="trans">
			<c:set var="count" value="${count + 1}" scope="page" />
			<tr>
				<c:choose>
					<c:when test="${trans.type=='CREDIT'}">
						<td>Merchant Debit</td>
					</c:when>
					<c:otherwise>
						<td>${trans.type}</td>
					</c:otherwise>
				</c:choose>

				<td>${trans.amount}</td>
				<c:choose>
					<c:when test="${trans.pending==true}">
						<td>Pending</td>
					</c:when>
					<c:otherwise>
						<c:choose>
							<c:when test="${trans.approved==true}">
								<td>Approved</td>
							</c:when>
							<c:otherwise>
								<td>Denied</td>
							</c:otherwise>
						</c:choose>
					</c:otherwise>
				</c:choose>
				<td><fmt:formatDate value="${trans.created_at}"
						pattern="MMM-dd-yyyy hh:mm a" /></td>
				<c:if test="${trans.type !='CREDIT'}">
					<c:choose>
						<c:when test="${trans.pending==true}">

							<td><form:form
									action="${pageContext.request.contextPath}/normalUser/TransactionDetail"
									method="POST" id="form${count}">
									<input type="hidden" name="transactionId"
										value="${trans.transactionId}" />
									<input type="button" value="Modify"
										onclick="onclickVal(${count})" />
								</form:form></td>
						</c:when>
					</c:choose>
				</c:if>
			</tr>
		</c:forEach>
	</table>
</c:if>

<!-- <iframe name="overlay" id="overlay" class='overlay' ></iframe> -->
