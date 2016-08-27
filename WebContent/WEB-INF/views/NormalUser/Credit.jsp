<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ page session="false"%>
<html>
<head>
<title>Credit</title>
<script type="text/javascript">
$('#DebitCreditModel').validate({ 
    rules: {
       amount:  {
            required: true
        },
		privateKey :{
			required : true
		}
    },
    messages: {
    	amount: "Please enter amount to be credited.",
		privateKey : "Please enter your private key."
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
	function creditAmount() {
		var amnt = document.getElementById('credit_amount').value;
		var balance = parseFloat(document.getElementById('accBalText').innerText);
		if (isNaN(amnt) || amnt == "") {
			document.getElementById('errorText').innerHTML = "Please enter valid amount";
			return false;
		} else if (parseFloat(amnt) >= balance) {
			document.getElementById('errorText').innerHTML = "Amount is more than the balance";
			return false;
		} else {
			document.getElementById('amntTocredit').value = amnt;
			//document.getElementById('credit_form').action = "UserHomecredit";
			return true;
		}
	}
	function changeAccountDetails(val) {
		var str = val;
		var res = str.split(",");
		document.getElementById('accBalText').innerHTML = res[1];
		document.getElementById('hbalance').value = res[1];
		document.getElementById('accTypeText').innerHTML = res[0];
		document.getElementById('type').value = res[0];
	}
	function amountEntered() {
		var decimalOnly = /^\s*-?[1-9]\d*(\.\d{1,2})?\s*$/;
		var amnt = document.getElementById('amountVal').value;
		if (isNaN(amnt) || amnt == "") {
			document.getElementById('errorText').innerHTML = "Please enter valid amount";
			document.getElementById("credit_submit").disabled = true;
			return false;
		}else if(!decimalOnly.test(amnt)){
			document.getElementById('errorText').innerHTML = "Enter amount upto 2 decimal places";
			document.getElementById("credit_submit").disabled = true;
			return false;
		} else if (parseFloat(amnt) > 5000) {
			document.getElementById('errorText').innerHTML = "You cannot credit more than 5000 at a time";
			document.getElementById("credit_submit").disabled = true;
			return false;
		} else {
			document.getElementById("credit_submit").disabled = false;
			return true;
		}
	}
</script>
</head>
<body>

	<form:form method="POST" action="${pageContext.request.contextPath}/normalUser/PostCredit"
		modelAttribute="DebitCreditModel">
		<h3>Credit</h3>

		<c:if test="${!empty listAccountsCreditPage}">
			 <div class="row">
	      <div class="col-md-12">
	      <div class="form-group">
	        <label class="control-label" for="accType">Account</label>
	          <div class="col-sm-7">

			<select class="form-control" name="item" id="account_dropdown"
				onchange="changeAccountDetails(this.options[this.selectedIndex].value)">
				<c:forEach items="${listAccountsCreditPage}" var="account">
					<option
						value="${account.acctype},${account.balance}">
						Type:${account.acctype}</option>
				</c:forEach>
			</select>  </div>
	      </div>
	      </div>
	    </div>
	    <div class="row">
	      <div class="col-md-12">
	      <div class="form-group">
			<br>
			<label class="control-label"><b>Account Type: </b></label>
			<label class="control-label" id="accTypeText">${listAccountsCreditPage[0].acctype}</label>
			<input type="hidden" name="acctype" id="type" value="${listAccountsCreditPage[0].acctype}" />
			<label class="control-label"><b>Balance: </b></label>
			<label class="control-label" id="accBalText">${listAccountsCreditPage[0].balance}</label>
			<input type="hidden" name="balance" id="hbalance"
				value="${listAccountsCreditPage[0].balance}" />
				</div>
		  </div>
		</div>
		</c:if>
		<br><div class="row">
		  <div class="col-md-12">
			<div class="form-group">
			  <label class="control-label" for="amount">Enter Amount</label>
			    <div class="col-sm-7">
		 		<input class="form-control" type="text" id="amountVal" width="80"
			min="0" value="0" name="amount" path="amount"
			onchange="amountEntered()"></input>
			</div>
			</div>
	      </div>
	  	</div>
		<br>
		<div class="row">
			<div class="col-md-12">
				<div class="form-group">
					<label class="control-label" for="amount">Enter Private Key</label>
					<div class="col-sm-7">
						<textarea class="form-control" id="privateKey" name="privateKey" rows="8"></textarea>						
					</div>
				</div>
			</div>
		</div>
		<br>
		<div class="row">
	  <div class="col-md-12">
		<div class="form-group">
		  <div class="col-sm-offset-2 col-sm-7">
			<input class="btn btn-success" type="submit" value="Credit" id="credit_submit"></input>
		  </div>
		</div>
	  </div>
	</div>
	</form:form>
	<label id="errorText"></label>
	<br>

</body>
</html>