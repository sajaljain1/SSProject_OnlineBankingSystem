<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ page session="false"%>
<title>Request Payment</title>
<script type="text/javascript">

	$('#RequestPaymentForm').validate({ 
	    rules: {
	    	accType:  {
                required: true
            },amount:  {
	            required: true,
	            number: true,
	            min: 0
	        },accID:  {
	            required: true
	        },transType:  {
	            required: true
	        }, 
	    },
	    messages: {
	    	accType: "Please select your account type.",
	    	amount: "Please enter valid amount to be Debited or Credited.",
	    	accID: "Please select Account Number.",
	    	transType: "Please select Transaction Type."
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


</script>

<form:form method="POST" action="${pageContext.request.contextPath}/normalUser/requestPayment" id="RequestPaymentForm">
		<h3>Request Payment</h3>

		<c:if test="${!empty fromlistAccounts}">
		    <div class="row">
		      <div class="col-md-12">
		      <div class="form-group">
		        <label class="control-label" for="accType">My Account:</label>
		          <div class="col-sm-7">
		           <select class="form-control" id="accType" name="accType"required="required" type="text">
		            <c:forEach items="${fromlistAccounts}" var="account">
		              <option
		                value="${account.accID}">${account.acctype}</option>
		            </c:forEach>
		          </select>
		        </div>
		      </div>
		      </div>
		    </div>
		  </c:if>

		  <br/>  


		<c:if test="${!empty listAccounts}">
		 <div class="row">
	      <div class="col-md-12">
	      <div class="form-group">
	        <label class="control-label" for="accNo">Customer Account:</label>
	          <div class="col-sm-7">
				<select class="form-control" name="accID" >
					<c:forEach items="${listAccounts}" var="account">
						<option value="${account.accID}">${account.accID}</option>
					</c:forEach>
				</select>
			  </div>
	      	</div>
	      </div>
	    </div>
	    </c:if>
		<br> 
		<div class="row">
	      <div class="col-md-12">
	      <div class="form-group">
	        <label class="control-label" for="transType">Transaction Type</label>
	          <div class="col-sm-7">
				<select class="form-control" name="transType" >
					<option value="CREDIT">Credit</option>
				</select>
			  </div>
	      	</div>
	      </div>
	    </div>
	    <br> 
		<div class="row">
		  <div class="col-md-12">
			<div class="form-group">
			  <label class="control-label" for="amount">Enter Amount</label>
			    <div class="col-sm-7">
					<input class="form-control" type="text" id="amountVal" name="amount" path="amount" placeholder="0">
				</div>
			</div>
	      </div>
	  	</div>
		<br>
		<div class="row">
	  <div class="col-md-12">
		<div class="form-group">
		  <div class="col-sm-offset-2 col-sm-7">
			<input class="btn btn-success" type="submit" value="Make Request" id="request_submit">
		  </div>
		</div>
	  </div>
	</div>
	</form:form>