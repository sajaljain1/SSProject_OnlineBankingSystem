<script>
  $(function() { // when DOM is ready
     $("#profile_settings").click(function(){ 
      $( ".title" ).val("Profile Settings");
     	$(".clearfix").html("Loading...");
        $(".clearfix").load($("#profile_settings").attr("data")); 
     });
     
     $("#debit").click(function(){ 
     	$(".clearfix").html("Loading...");
        $(".clearfix").load($("#requests").attr("data")); 
     });
     
     $("#credit").click(function(){
     	$(".clearfix").html("Loading..."); 
        $(".clearfix").load($("#accountTransaction").attr("data")); 
     });
     
     $("#transfer").click(function(){ 
        $(".clearfix").html("Loading...");
        $(".clearfix").load($("#requestStatus").attr("data")); 
     });
      
  });
</script>

<div class="page-sidebar-expanded page-with-sidebar">
<div class="sidebar-wrapper nicescroll" style=
"overflow: hidden; outline: none;" tabindex="3">
  <div class="header-logo">
    <a class="home" data-placement="bottom" data-toggle="tooltip" href="/welcome" id="js-shortcuts-home" title="Dashboard">
		<div class="sbs-text-container">
		  <h3>SBS</h3>
		</div>
	</a>
  </div>
  <ul class="nav nav-sidebar">
    <li class="">
      <a class="back-link" data-placement="right" href="${pageContext.request.contextPath}/regEmployee" title="Back to dashboard"><i class="fa fa-caret-square-o-left fa-fw"></i> <span>Back to dashboard</span></a>
    </li>

    <li class="separate-item"></li>

    <li>
      <a id="profile_settings" data-placement="right" data="${pageContext.request.contextPath}/regEmployee/profile_settings" title="Profile"><i class="fa fa-fw"></i><span>Profile Settings</span></a>
    </li>
	<li>
      <a id="debit" data-placement="right" data="${pageContext.request.contextPath}/regEmployee/requests" title="Requests"><i class="fa fa-fw"></i><span>Requests</span></a>
  </li>
	<li>
      <a id="credit" data-placement="right" data="${pageContext.request.contextPath}/regEmployee/accountTransaction" title="Access Account/Transaction and create Transactions"><i class="fa fa-fw"></i><span>Access Account/Transaction and create Transactions</span></a>
    </li>
	<li>
      <a id="transfer" data-placement="right" data="${pageContext.request.contextPath}/regEmployee/requestStatus" title="Check Request Access"><i class="fa fa-fw"></i><span>Check Request Access</span></a>
    </li>
  </ul>
</div>
</div>