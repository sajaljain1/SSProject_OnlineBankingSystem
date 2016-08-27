<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@page session="true"%>

<script type="text/javascript">
function getTable(acctype){
request = $.ajax({
    url: "normalUser/viewTrans/"+acctype,
    type: "get"
});
request.done(function (response, textStatus, jqXHR) {
    $("#transList").html(response);
});

request.fail(function (jqXHR, textStatus, errorThrown) {
    console.error(
        "The following error occured: " + textStatus);
});

request.always(function () {
});
}

</script>

<div class="content-wrapper">
	<div class="flash-container"></div>
	<div class="container-fluid container-limited">
		<div class="content">
			<div class="clearfix">

				<c:if test="${userDetail != null}">
					<h2>Welcome : ${userDetail.firstname}</h2>
				</c:if>

				<div id="accountdetails">

					<h3>Account Details</h3>

					<c:if test="${!empty listAccounts}">
						<table class="tg">
							<tr>
								<th width="120">Account Type</th>
								<th width="80">Balance</th>
							</tr>
							<c:forEach items="${listAccounts}" var="account">
								<tr>
									<td><a onclick="getTable('${account.acctype}')" >${account.acctype} </a></td>
									<td>${account.balance}</td>
								</tr>
							</c:forEach>
						</table>
					</c:if>
				</div>
				<br> <br>
				 <div id="transList"></div> 
			<!-- 	<iframe name="TransListframe" style="width: 100%; height: 500px"></iframe>  -->

			</div>
		</div>
	</div>
</div>

