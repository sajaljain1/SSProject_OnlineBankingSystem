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
					<h2 class="h2"><b>Generate OTP</b></h2>
					<sf:form class="login-form" id="newPasswordForm" method="post"
						action="${pageContext.request.contextPath}/register" commandName="OTPRequestInfo">
						<fieldset>
							<div class="field-group">
								<label class="failuremsg"><c:out value="${OTPRequestInfo.getFailureMessage()}"></c:out></label>
							</div>
							<div class="field-group">
								<label class="sr" for="username"> Username </label>
								<sf:input path="username" placeholder="username" id="username" />
								<br/>
								<sf:errors path="username" cssErrorClass="validationError"/>
								
							</div>
							<div class="text-left">
								<input type="submit" onclick="this.disabled=true;this.style.background='#555555';alert('Your request is processing');this.form.submit();" id="otpbutton"
									class="button button-primary contact-submit" value="Generate OTP">
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