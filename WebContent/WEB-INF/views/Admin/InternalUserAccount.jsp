<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>

<style>
.validationError {
	color: #ff0000;
}
</style>
<form:form method="post"
	action="${pageContext.request.contextPath}/admin/createUserAccount"
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
				*<label class="control-label" for="userType">User Type</label>
				<div class="col-sm-7">
					<form:radiobutton path="userType" value="ROLE_REGULAR_EMP" />
					Regular Employee <br>
					<form:radiobutton path="userType" value="ROLE_SYS_MANAGER" />
					System Manager <br>
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
</form:form>