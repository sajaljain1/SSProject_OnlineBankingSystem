<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ page session="false"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Transfer</title>
<style type="text/css">
.tg {
	border-collapse: collapse;
	border-spacing: 0;
	border-color: #ccc;
}

.tg td {
	font-family: Arial, sans-serif;
	font-size: 14px;
	padding: 10px 5px;
	border-style: solid;
	border-width: 1px;
	overflow: hidden;
	word-break: normal;
	border-color: #ccc;
	color: #333;
	background-color: #fff;
}

.tg th {
	font-family: Arial, sans-serif;
	font-size: 14px;
	font-weight: normal;
	padding: 10px 5px;
	border-style: solid;
	border-width: 1px;
	overflow: hidden;
	word-break: normal;
	border-color: #ccc;
	color: #333;
	background-color: #f0f0f0;
}

.tg .tg-4eph {
	background-color: #f9f9f9
}
</style>
<script type="text/javascript">
	
	function amountEntered() {
		var decimalOnly = /^\s*-?[1-9]\d*(\.\d{1,2})?\s*$/;
		var amnt = document.getElementById('amountVal').value;
		if (isNaN(amnt) || amnt == "") {
			document.getElementById('errorText').innerHTML = "Please enter valid amount";
			document.getElementById("transfer_submit").disabled = true;
			return false;
		} else if(!decimalOnly.test(amnt)){
			document.getElementById('errorText').innerHTML = "Enter amount upto 2 decimal places";
			document.getElementById("transfer_submit").disabled = true;
			return false;
		} else {
			document.getElementById("transfer_submit").disabled = false;
			return true;
		}
	}
	
	function accountEntered(){
		var acc1=document.getElementById('account_dropdown').value;
		var acc2=document.getElementById('receiverAcc').value;
		if(acc2=="" || acc2 == 0){
			document.getElementById('errorText').innerHTML = "Please enter valid account number";
			document.getElementById("transfer_submit").disabled=true;
			return false;
		} else if(acc1 == acc2){
			document.getElementById('errorText').innerHTML="Receiver account cannot be same as sender account";
			document.getElementById("transfer_submit").disabled=true;
			return false;
		}else{
			document.getElementById("transfer_submit").disabled=false;
			return true;
		}
	}
	
	
	$('#TransferModel').validate({
    rules: {
      privateKey :{
			required : true
	  }
    },
    messages: {
    	privateKey : "Please enter your private key."
    }, submitHandler: function (form) {
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
		
</script>
</head>
<body>

	<form:form method="POST" 
		action="${pageContext.request.contextPath}/normalUser/PostTransfer"
		modelAttribute="TransferModel">
		<h3>Transfer</h3>
				
		<c:if test="${!empty listSenderAccounts}">
			<div class="row">
				<div class="col-md-12">
					<div class="form-group">
						<label class="control-label" for="account_dropdown">Select</label>
						<div class="col-sm-7">
							<select class="form-control" name="senderAccID" id="account_dropdown" path="senderAccID">
								<c:forEach items="${listSenderAccounts}" var="account">
									<option	value="${account.accID}">
									ID:${account.accID} (Balance:${account.balance})</option>
								</c:forEach>
							</select>	
						</div>
					</div>
				</div>
			</div>
				
			
		</c:if>
		<br>
		<br>
		<div class="row">
			<div class="col-md-12">
				<div class="form-group">
					<label class="control-label" for="receiverAcc">Receiver's Account Number </label>
					<div class="col-sm-7">
						<input class="form-control" type="text" id="receiverAcc" width="80" name="receiverAccID" 
							path="receiverAccID" onchange="accountEntered()"></input>				
					</div>
				</div>
			</div>
		</div>
		  
		<br>
		<br>
		
		<div class="row">
			<div class="col-md-12">
				<div class="form-group">
					<label class="control-label" for="amountVal"> Enter Amount </label>
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
		
		<br>
		<div class="row">
			<div class="col-md-12">
				<div class="form-group">
					<div class="col-sm-offset-2 col-sm-7">
						<input class="btn btn-success" type="submit" 
						    value="Transfer" id="transfer_submit">
					</div>
				</div>
			</div>
		</div>
		
	</form:form>
	<label id="errorText"></label>
	<br>
</body>
</html>