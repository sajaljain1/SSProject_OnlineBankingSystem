<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:url value="/j_spring_security_logout" var="logoutUrl" />

  <!-- csrt for log out-->
  <form action="${logoutUrl}" method="post" id="logoutForm">
    <input type="hidden" 
    name="${_csrf.parameterName}"
    value="${_csrf.token}" />
  </form>
  
  <script>
    function formSubmit() {
      document.getElementById("logoutForm").submit();
    }
  </script>

<header class="header-expanded navbar navbar-fixed-top navbar-sbs">
  <div class="container-fluid">
    <div class="header-content">
      <button class="navbar-toggle" type="button"><span class=
      "sr-only">Toggle navigation</span> <i class="fa fa-bars"></i></button>

      <div class="navbar-collapse collapse">
        <ul class="nav navbar-nav pull-right">
          <sec:authorize access="isAuthenticated()">
          	<input class="btn btn-danger" style="width:6em;margin-top:0.5em;" value="Logout" data-method="delete" onclick="javascript:formSubmit()" rel="nofollow" title="" data-original-title="Sign out" type="button"/>
          </sec:authorize>
        </ul>          
      </div>
      <h1 class="title">Secure Banking System</h1>
    </div>
  </div>
</header>
