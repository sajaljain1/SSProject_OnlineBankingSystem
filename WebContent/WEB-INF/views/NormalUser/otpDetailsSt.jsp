<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="sf" uri="http://www.springframework.org/tags/form"%>
<!DOCTYPE html>
<html lang="en" class="no-js">
<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<title>OTP Details</title>

<meta name="description" content="">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<link rel="shortcut icon"
	href="<c:url value="/resources/img/favicon.png" />">
<link rel="stylesheet" href="<c:url value="/resources/css/style.css" />">
<script src="<c:url value="/resources/js/keyboard.js" />"></script>
<script src="<c:url value="/resources/js/jquery-1.10.2.min.js" />"></script>
<script src="<c:url value="/resources/js/jquery.validate.min.js" />"></script>
<script src="<c:url value="/resources/js/validate.js" />"></script>
<link rel="stylesheet"
	href="<c:url value="/resources/css/keyboard.css" />">
</head>
<body>
	<sf:form class="login-form" id="otpdetails" method="post"
		action="${pageContext.request.contextPath}/normalUser/otpdetail"
		commandName="oTPInput">
		<fieldset>
			<div class="field-group">
				<label class="failuremsg"><c:out
						value="${changePassword.getFailureMessage()}"></c:out></label>
			</div>
			<div class="field-group">
				<label class="sr" for="OTP"> OTP </label>
				<sf:input path="OTP" class="vk-field keyboardInput" id="OTP" placeholder="OTP" />
				<br />
				<sf:errors path="OTP" cssErrorClass="validationError" />
			</div>
			<div class="text-left">
				<input type="submit" class="button button-primary contact-submit"
					value="Get Statement">
			</div>

		</fieldset>
	</sf:form>
</body>
</html>