<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<script>
  $(function() { // when DOM is ready
     $("#profile_settings").click(function(){ 
      $( ".title" ).val("Profile Settings");
     	$(".clearfix").html("Loading...");
        $(".clearfix").load($("#profile_settings").attr("data")); 
     });
     
     $("#debit").click(function(){ 
     	$(".clearfix").html("Loading...");
        $(".clearfix").load($("#debit").attr("data")); 
     });
     
     $("#credit").click(function(){
     	$(".clearfix").html("Loading..."); 
        $(".clearfix").load($("#credit").attr("data")); 
     });
     
     $("#transfer").click(function(){ 
        $(".clearfix").html("Loading...");
        $(".clearfix").load($("#transfer").attr("data")); 
     });
     
     $("#reqpayment").click(function(){ 
     	$(".clearfix").html("Loading...");
        $(".clearfix").load($("#reqpayment").attr("data")); 
     });
     
     $("#statements").click(function(){ 
     	$(".clearfix").html("Loading...");
        $(".clearfix").load($("#statements").attr("data")); 
     });
     $("#deleteAccount").click(function(){ 
         $(".clearfix").html("Loading...");
         $(".clearfix").load($("#deleteAccount").attr("data")); 
      });
     $("#requests").click(function(){ 
         $(".clearfix").html("Loading...");
         $(".clearfix").load($("#requests").attr("data")); 
      });
 
  });
</script>

<div class="page-sidebar-expanded page-with-sidebar">
<div class="sidebar-wrapper nicescroll" style=
"overflow: hidden; outline: none;" tabindex="3">
  <div class="header-logo">
    <a class="home" data-placement="bottom" data-toggle="tooltip" href="${pageContext.request.contextPath}/welcome" id="js-shortcuts-home" title="Dashboard">
		<div class="sbs-text-container">
		  <h3>Home</h3>
		</div>
	</a>
  </div>
  <ul class="nav nav-sidebar">
    <li class="">
      <a class="back-link" data-placement="right" href="${pageContext.request.contextPath}/normalUser" title="Back to dashboard"><i class="fa fa-caret-square-o-left fa-fw"></i> <span>Back to dashboard</span></a>
    </li>

    <li class="separate-item"></li>

    <li>
      <a id="profile_settings" data-placement="right" data="${pageContext.request.contextPath}/normalUser/profile_settings" title="Profile"><i class="fa fa-fw"></i><span>Profile Settings</span></a>
    </li>
	<li>
      <a id="debit" data-placement="right" data="${pageContext.request.contextPath}/normalUser/Debit" title="Debit"><i class="fa fa-fw"></i><span>Debit Account</span></a>
  </li>
	<li>
      <a id="credit" data-placement="right" data="${pageContext.request.contextPath}/normalUser/Credit" title="Credit"><i class="fa fa-fw"></i><span>Credit Account</span></a>
    </li>
	<li>
      <a id="transfer" data-placement="right" data="${pageContext.request.contextPath}/normalUser/Transfer" title="Transfer"><i class="fa fa-fw"></i><span>Transfer Money</span></a>
    </li>
	  <li>
      <a id="statements" data-placement="right" data="${pageContext.request.contextPath}/normalUser/generateOtpSt" title="Statements"><i class="fa fa-fw"></i><span>Statements</span></a>
    </li>
    <li>
      <a id="requests" data-placement="right" href="${pageContext.request.contextPath}/requests" data="${pageContext.request.contextPath}/normalUser/requests" title="Requests"><i class="fa fa-fw"></i><span>Requests</span></a>
    </li>
     <li>
      <a id="deleteAccount" data-placement="right" data="${pageContext.request.contextPath}/normalUser/DeleteAccount" title="deleteAccount"><i class="fa fa-fw"></i><span>Delete Account</span></a>
    </li>
    <c:if test="${userDetail.merchantid != null}">
      <li>
        <a id="reqpayment" data-placement="right" data="${pageContext.request.contextPath}/normalUser/reqpayment" title="Request Payment"><i class="fa fa-fw"></i><span>Request Payment</span></a>
      </li>
    </c:if>
  </ul>
</div>
</div>