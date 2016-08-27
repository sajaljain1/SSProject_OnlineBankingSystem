<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="sf" uri="http://www.springframework.org/tags/form"%>
<!DOCTYPE html>
<html lang="en" class="no-js">
<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<title>Secure Banking System- Sign Up</title>

<meta name="description" content="">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<link rel="shortcut icon"
	href="<c:url value="/resources/img/favicon.png" />">
<link rel="stylesheet" href="<c:url value="/resources/css/style.css" />">
<script src="<c:url value="/resources/js/keyboard.js" />"></script>
<link rel="stylesheet"
	href="<c:url value="/resources/css/keyboard.css" />">
<link rel="stylesheet"
	href="<c:url value="/resources/css/jquery-ui.min.css" />">
<script src="<c:url value="/resources/js/jquery-1.10.2.min.js" />"></script>
<script src="<c:url value="/resources/js/jquery-ui.min.js" />"></script>
<script src="<c:url value="/resources/js/jquery.validate.min.js" />"></script>
<script src="<c:url value="/resources/js/validate.js" />"></script>
<script src="<c:url value="/resources/js/additional-methods.min.js" />"></script>
</head>
<body>
	<div id="login" class="register-block contact-block">
		<div class="contact-block-inner container">
			<div class="clear">
				<div class="login-block-form col-tablet-12">
					<h2 class="h2">Sign Up</h2>
					<sf:form class="login-form" id="registerForm" method="post"
						commandName="userSignUp"
						action="${pageContext.request.contextPath}/usersignup">
						<fieldset>
							<div class="field-group">
								<label class="successmsg"><c:out
										value="${userSignUp.getSuccessMessage()}"></c:out></label> <label
									class="failuremsg"><c:out
										value="${userSignUp.getFailureMessage()}"></c:out></label>
							</div>
							<div class="field-group">
								<label><c:out
										value="Username: ${userSignUp.getUsername()}"></c:out></label>
							</div>
							<div class="field-group">
								<sf:label class="sr" path="password">Password</sf:label>
								<sf:password class="vk-field keyboardInput" id="password"
									path="password" placeholder="New Password" />
								<br />
								<sf:errors path="password" cssErrorClass="validationError" />
							</div>
							<div class="field-group">
								<sf:label class="sr" path="repassword">Confirm Password</sf:label>
								<sf:password class="vk-field keyboardInput" id="confirmpassword"
									path="repassword" placeholder="Confirm New Password" />
								<br />
								<sf:errors path="repassword" cssErrorClass="validationError" />
							</div>
							<div class="field-group">
								<sf:label class="sr" path="PII">PII</sf:label>
								<sf:input name="pii" class="field" id="pii" path="PII"
									placeholder="Personally identifiable information" />
								<br />
								<sf:errors path="PII" cssErrorClass="validationError" />
							</div>
							<div class="field-group">
								<sf:input path="OTP" class="vk-field keyboardInput" id="OTP" placeholder="OTP" />
								<br />
								<sf:errors path="OTP" cssErrorClass="validationError" />
							</div>
							<div class="field-group">
								<sf:label path="chosenSecurityQuestion">Security Question</sf:label>
								<sf:select class="field" path="chosenSecurityQuestion">
									<sf:option value="NONE" label="--- Select ---" />
									<sf:options items="${securityQuestions}" />
								</sf:select> <br/>
								<sf:errors path="chosenSecurityQuestion" cssErrorClass="validationError" />
								<div class="field-group">
									<sf:label class="sr" path="securityAnswer" />
									<sf:input path="securityAnswer" class="field" id="answer"
										type="text" placeholder="Security Answer" />
									<br />
									<sf:errors path="securityAnswer"
										cssErrorClass="validationError" />
								</div>
							</div>
							<!-- By clicking on submit, you are accepting our <a href="#">terms
								and conditions</a> -->
							<div class="text-left">
								<input type="submit"
									class="button button-primary contact-submit" value="Submit">
							</div>
						</fieldset>
					</sf:form>
					<br /> <a href="${pageContext.request.contextPath}/login">Sign in</a><br /> <br />
					<a href="${pageContext.request.contextPath}/welcome">Home</a><br />
				</div>
			</div>
		</div>
	</div>
</body>
</html>