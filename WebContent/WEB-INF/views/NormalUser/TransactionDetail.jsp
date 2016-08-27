<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>

<html>
<head>


<script>
$('#formupdate').validate({ 
    rules: {
       amount:  {
            required: true
        }
    },
    messages: {
    	amount: "Please enter amount to be credited."
    },submitHandler: function (form) {
    var $form = $(form);
    var $inputs = $form.find("input, select, button, textarea");
    var serializedData = $form.serialize();

    $inputs.prop("disabled", true);

    request = $.ajax({
        url: $(form).attr("action"),
        type: "post",
        data: serializedData
    });

    request.done(function (response, textStatus, jqXHR) {
    	alert(response);
        $(".clearfix").html(response);
    });

    request.fail(function (jqXHR, textStatus, errorThrown) {
        console.error(
            "The following error occured: " + textStatus);
    });

    request.always(function () {
        $inputs.prop("disabled", false);
    });

}

}); 
	function amountEntered() {
	var decimalOnly = /^\s*-?[1-9]\d*(\.\d{1,2})?\s*$/;
		var amnt = document.getElementById('amountVal').value;
		if (isNaN(amnt) || amnt == "") {
			document.getElementById('errorText').innerHTML = "Please enter valid amount";
			document.getElementById("updateSubmit").disabled = true;
			return false;
		} else if (parseFloat(amnt) > 5000) {
			var transType = document.getElementById('transtype').innerHTML;
			document.getElementById('errorText').innerHTML = "You cannot "
					+ transType.toLowerCase() + "  more than 5000 at a time";
			document.getElementById("updateSubmit").disabled = true;
			return false;
		} else if(!decimalOnly.test(amnt)){
			document.getElementById('errorText').innerHTML = "Enter amount upto 2 decimal places";
			document.getElementById("updateSubmit").disabled = true;
			return false;}
		else {
			document.getElementById("updateSubmit").disabled = false;
			document.getElementById('errorText').innerHTML = "";
			return true;
		}
	}
</script>
</head>
<body>

	<form:form modelAttribute="TransactionUpdateModel" method="POST" id="formupdate"
		action="${pageContext.request.contextPath}/normalUser/TransactionUpdate">
		<c:if test="${!empty transDetail}">
			<input type="hidden" name="created_at"
				value="${transDetail.created_at}">
			<input type="hidden" name="transactionId"
				value="${transDetail.transactionId}">

			<div class="row">
				<div class="col-md-12">
					<div class="form-group">
						<label class="control-label">Account Number</label>
						<div class="col-sm-7">
							<input type="hidden" name="account_from"
								value="${transDetail.account_from}"> <label
								class="control-label">${transDetail.account_from}</label>
						</div>
					</div>
				</div>
			</div>
			<br />
			<div class="row">
				<div class="col-md-12">
					<div class="form-group">
						<label class="control-label">Transaction type</label>
						<div class="col-sm-7">
							<input type="hidden" name="type" value="${transDetail.type}">
							<label class="control-label" id="transtype">${transDetail.type}</label>
						</div>
					</div>
				</div>
			</div>
			<br />
			<div class="row">
				<div class="col-md-12">
					<div class="form-group">
						<label class="control-label">Previous Amount</label>
						<div class="col-sm-7">
							<input type="hidden" name="prevAmount"
								value="${transDetail.amount}"> <label
								class="control-label">${transDetail.amount}</label>
						</div>
					</div>
				</div>
			</div>
			<br />
			<div class="row">
				<div class="col-md-12">
					<div class="form-group">
						<label class="control-label">Enter new Amount</label>
						<div class="col-sm-7">
							<input class="form-control" id="amountVal" name="amount"
								type="text" onchange="amountEntered()"
								value="${transDetail.amount}">
						</div>
					</div>
				</div>
			</div>
			<br>

			<div class="row">
				<div class="col-md-12">
					<div class="form-group">

						<div class="col-sm-7">

							<input class="form-control" id="updateSubmit"
								type="submit" value="Update Transaction">
							
						</div>
					</div>
				</div>
			</div>



		</c:if>

	</form:form>
	<form:form modelAttribute="TransactionUpdateModel" method="POST" id="formDelete"
								action="${pageContext.request.contextPath}/normalUser/deleteTransaction">
							<input type="hidden" name="transactionId"
								value="${transDetail.transactionId}"> 
									<input class="form-control" id="deleteSubmit"
								type="submit" value="Delete Transaction">
								</form:form>
	<br />
	<label id="errorText"></label>
</body>




</html>