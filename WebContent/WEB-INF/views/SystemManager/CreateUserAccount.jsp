<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>

<script type="text/javascript">
	$('#SysManager').validate({
		rules : {
			firstname : {
				required : true
			},
			lastname : {
				required : true
			},
			email : {
				required : true,
				email : true,
				maxlength : 50
			},
			phonenum : {
				required : true,
				phoneUS : true
			},
			address : {
				required : true
			},
			DateofBirth : {
				required : true
			}
		},
		messages : {
			firstname : "Please enter your first name.",
			lastname : "Please enter your last name.",
			email : "Please enter valid email.",
			contact : "Please enter valid contact number.",
			address : "Please provide your address",
			DateofBirth : "Please provide Date of Birth.",
		},
		submitHandler : function(form) {
			// setup some local variables
			var $form = $(form);
			// let's select and cache all the fields
			var $inputs = $form.find("input, select, button, textarea");
			// serialize the data in the form
			var serializedData = $form.serialize();

			// let's disable the inputs for the duration of the ajax request
			$inputs.prop("disabled", true);

			request = $.ajax({
				url : $(form).attr("action"),
				type : "post",
				data : serializedData
			});

			// callback handler that will be called on success
			request.done(function(response, textStatus, jqXHR) {
				// log a message to the console
				$(".clearfix").html(response);
			});

			// callback handler that will be called on failure
			request.fail(function(jqXHR, textStatus, errorThrown) {
				// log the error to the console
				console.error("The following error occured: " + textStatus);
			});

			// callback handler that will be called regardless
			// if the request failed or succeeded
			request.always(function() {
				// reenable the inputs
				$inputs.prop("disabled", false);
			});

		}

	});
</script>
<style>
.validationError {
	color: #ff0000;
}
</style>
<form:form method="post"
	action="${pageContext.request.contextPath}/createUserAccount"
	commandName="userRegistration">
	<br />
	<h2>Register a new User</h2>
	<div class="row">
		<div class="col-md-12">
			<div class="form-group">
				<label class="successmsg"><c:out
						value="${userRegistration.getSuccessMessage()}"></c:out></label> <label
					class="failuremsg"><c:out
						value="${userRegistration.getFailureMessage()}"></c:out></label>
			</div>
		</div>
	</div>
		<div class="row">
		<div class="col-md-12">
			<div class="form-group">
				<div class="col-sm-7">
					Fields with (*) are mandatory <br></br>
				</div>
			</div>
		</div>
	</div>
	<div class="row">
		<div class="col-md-12">
			<div class="form-group">
				<div class="col-sm-7">
					*<form:input class="form-control" id="firstname" path="firstname" placeholder="First Name" /> <br>
					<form:errors cssErrorClass="validationError" path="firstname" element="div"/>
				</div>
			</div>
		</div>
	</div>

	<br />

	<div class="row">
		<div class="col-md-12">
			<div class="form-group">
				<div class="col-sm-7">
					*<form:input class="form-control" id="lastname" path="lastname" placeholder="Last Name"/> <br>
					<form:errors cssErrorClass="validationError" path="lastname"/>
				</div>
			</div>
		</div>
	</div>

	<br />

	<div class="row">
		<div class="col-md-12">
			<div class="form-group">
				<div class="col-sm-7">
					*<form:input class="form-control" id="email" path="email" placeholder="Email"/> <br>
					<form:errors cssErrorClass="validationError" path="email"/>
				</div>
			</div>
		</div>
	</div>

	<br />

	<div class="row">
		<div class="col-md-12">
			<div class="form-group">
				<div class="col-sm-7">
					*<form:input class="form-control" id="contact" path="contact" placeholder="Phone Number" /> <br>
					<form:errors cssErrorClass="validationError" path="contact"/>
				</div>
			</div>
		</div>
	</div>

	<br />

	<div class="row">
		<div class="col-md-12">
			<div class="form-group">
				<div class="col-sm-7">
					*<form:input class="form-control" id="address" path="address" placeholder="Address"/> <br>
					<form:errors cssErrorClass="validationError" path="address" />
				</div>
			</div>
		</div>
	</div>
	<br />

	<div class="row">
		<div class="col-md-12">
			<div class="form-group">
				<div class="col-sm-7">
					*<form:input class="form-control" id="DOB" path="dob" placeholder="Date of Birth(MM-DD-YYYY)"/> <br>
					<form:errors cssErrorClass="validationError" path="dob"/>
				</div>
			</div>
		</div>
	</div>

	<br />
	<div class="row">
		<div class="col-md-12">
			<div class="form-group">
				<label class="control-label" for="userType">User Type</label>
				<div class="col-sm-7">
					<form:radiobutton path="userType" value="indiuser" />
					Individual User <br>
					<form:radiobutton path="userType" value="merchant" />
					Merchant <br>
					<form:errors cssErrorClass="validationError" path="userType"/>
				</div>
			</div>
		</div>
	</div>

	<div class="row">
		<div class="col-md-12">
			<div class="form-group">
				<div class="col-sm-offset-2 col-sm-7">
					<input class="btn btn-success" name="commit" type="submit"
						value="Create User Account">
				</div>
			</div>
		</div>
	</div>
	<a href="${pageContext.request.contextPath}/welcome">Home</a>
</form:form>