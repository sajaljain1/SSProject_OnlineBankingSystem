<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="sf" uri="http://www.springframework.org/tags/form"%>
<!DOCTYPE html>
<html lang="en" class="no-js">
<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<title>Secure Banking System- Reset Password</title>

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
<noscript>
    <style type="text/css">
        #login {display:none;}
    </style>
    <div class="noscriptmsg">
    	<h2>
    	You don't have javascript enabled. Please enable javascript to use this site.
	</h2>
    </div>
</noscript>
</head>
<body>
	<div id="login" class="login-block contact-block">
		<div class="contact-block-inner container">
			<div class="clear">
				<div class="login-block-form col-tablet-12">
					<h2 class="h2">Step-2 : Reset-Password</h2>
					<sf:form class="login-form" id="newPasswordForm" method="post"
						action="${pageContext.request.contextPath}/reset_pwd"
						commandName="changePassword">
						<fieldset>
							<div class="field-group">
								<label class="successmsg"><c:out
										value="${changePassword.getSuccessMessage()}"></c:out></label> <label
									class="failuremsg"><c:out
										value="${changePassword.getFailureMessage()}"></c:out></label>
							</div>
							<div class="field-group">
							<label><c:out
										value="Username: ${changePassword.getUsername()}"></c:out></label>
							</div>
							<div class="field-group">
								<label class="sr" for="newPassword"> New Password </label>
								<sf:password path="newPassword" id="password"
									placeholder="Password" />
								<br />
								<sf:errors path="newPassword" cssErrorClass="validationError" />
							</div>
							<div class="field-group">
								<label class="sr" for="confirmPassword"> Re-enter
									Password </label>
								<sf:password path="confirmPassword" id="confirmPassword"
									placeholder="Confirm Password" />
								<br />
								<sf:errors path="confirmPassword"
									cssErrorClass="validationError" />
							</div>
							<div class="field-group">
								<label class="sr" for="OTP"> OTP </label>
								<sf:input path="OTP" class="vk-field keyboardInput" id="OTP" placeholder="OTP" />
								<br />
								<sf:errors path="OTP" cssErrorClass="validationError" />
							</div>
							<div class="field-group">
							<label><c:out
										value="Security Question: ${changePassword.getChosenSecurityQuestion()}"></c:out></label>
								<div class="field-group">
									<sf:label class="sr" path="securityAnswer" />
									<sf:input path="securityAnswer" class="field" id="answer"
										placeholder="Security Answer" />
									<sf:errors path="securityAnswer"
										cssErrorClass="validationError" />
								</div>
							</div>
							<div class="text-left">
								<input type="submit" onclick="this.disabled=true;this.style.background='#555555';alert('Your request is processing');this.form.submit();" id="otpbutton"
									class="button button-primary contact-submit"
									value="Reset Password">
							</div>
						</fieldset>
					</sf:form>
					<br /> <a href="${pageContext.request.contextPath}/login">Sign in</a><br /> <a
						href="${pageContext.request.contextPath}/">Home</a><br />
				</div>
			</div>
		</div>
	</div>
</body>
</html>