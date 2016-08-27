<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html lang="en" class="no-js">
<head>
  <meta charset="utf-8">
  <meta http-equiv="X-UA-Compatible" content="IE=edge">
  <title>Secure Banking System- Login</title>

  <meta name="description" content="">
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
  <link rel="stylesheet" href="<c:url value="/resources/css/style.css" />">
  <link rel="shortcut icon" href="<c:url value="/resources/img/favicon.png" />">
  <script src='https://www.google.com/recaptcha/api.js'></script>
  <script src="<c:url value="/resources/js/keyboard.js" />"></script>
  <script src="<c:url value="/resources/js/jquery-1.10.2.min.js" />"></script>
  <script src="<c:url value="/resources/js/jquery.validate.min.js" />"></script>
  <script src="<c:url value="/resources/js/validate.js" />"></script>
  <link rel="stylesheet" href="<c:url value="/resources/css/keyboard.css" />">
  <noscript>
    <style type="text/css">
        #login {display:none;}
    </style>
    <div class="noscriptmsg">
    <h3>
    You don't have javascript enabled. This site requires javascript enabled.</h3>
    </div>
</noscript>
</head>

<body onload='document.loginForm.username.focus();'>
<c:if test="${not empty error}">
  <div class="error">${error}</div>
</c:if>
<c:if test="${not empty msg}">
  <div class="msg">${msg}</div>
</c:if>

<div id="login" class="login-block contact-block">
  <div class="contact-block-inner container">
    <div class="clear">
      <div class="login-block-form col-tablet-12">
        <h2 class="h2">Login</h2>
        <form name='loginForm' id='loginForm' class="login-form"
              action="<c:url value='j_spring_security_check' />" method='POST'>
          <fieldset>
            <div class="field-group">
              <label class="sr" for="username">UserName</label>
              <input name="username" class="field" id="username" type="text" placeholder="UserName">
            </div>
            <div class="field-group">
              <label class="sr" for="password">Password</label>
              <input name="password" class="vk-field keyboardInput" id="password" type="password" placeholder="Password">
            </div>
            <div class="field-group g-recaptcha" data-sitekey="6LceNA0TAAAAAJSdos73VYINCt8Kv_3rQNW-Xlw6">
            </div>
            <font id = "errorBox" ></font>
            <div class="text-left">
              <input type="submit" class="button button-primary contact-submit" value="Submit"/>
            </div>
          </fieldset>

          <input type="hidden" name="${_csrf.parameterName}"
                 value="${_csrf.token}" />
        </form>
        <br/>
        <a href="${pageContext.request.contextPath}/forgotpwd">Forgot your password?</a><br/>
        <a href="${pageContext.request.contextPath}/welcome">Home</a><br/>
      </div>
    </div>
  </div>
</div>
</body>
</html>