<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<link rel="stylesheet"
  href="<c:url value="/resources/css/jquery-ui.min.css" />">
<script src="<c:url value="/resources/js/jquery-1.10.2.min.js" />"></script>
<script src="<c:url value="/resources/js/jquery-ui.min.js" />"></script>
<script src="<c:url value="/resources/js/jquery.validate.min.js" />"></script>
<script src="<c:url value="/resources/js/validate.js" />"></script>
  
<script>
  $(document).ready(function() {

    $('#statementsForm').validate({ 
        rules: {
            accType:  {
                required: true
            },startDate:  {
                required: true
            },endDate:  {
                required: true,
            }
        },
        messages: {
            accType: "Please select your account type.",
            startDate: "Please enter start date.",
            endDate: "Please enter end date."
        }

    });

    $("#startDate").datepicker({
        dateFormat: 'yy-mm-dd',
        maxDate: '0',
        onSelect: function(selected) {
          $("#endDate").datepicker("option","minDate", selected)
        }
    });
    
    $("#endDate").datepicker({ 
        dateFormat: 'yy-mm-dd',
        maxDate: '0',
        onSelect: function(selected) {
            $("#startDate").datepicker("option","maxDate", selected)
        }
    });
  });

</script>

<form:form method="POST" action="${pageContext.request.contextPath}/normalUser/statements" id="statementsForm" >		
  <input type="hidden" id="username" name="username" value="${getUserProfile[0].username}" />

  <c:if test="${!empty listAccounts}">
    <div class="row">
      <div class="col-md-12">
      <div class="form-group">
        <label class="control-label" for="accType">Account</label>
          <div class="col-sm-7">
           <select class="form-control" id="accType" name="accType"required="required" type="text">
            <c:forEach items="${listAccounts}" var="account">
              <option
                value="${account.accID}">${account.acctype}</option>
            </c:forEach>
          </select>
        </div>
      </div>
      </div>
    </div>
  </c:if>

  <br/>  

  <div class="row">
	  <div class="col-md-12">
		<div class="form-group">
		  <label class="control-label" for="startDate">Start Date(yyyy-mm-dd)</label>
		    <div class="col-sm-7">
				<input class="form-control" id="startDate" name="startDate"required="required" type="text" > 
			</div>
		</div>
    </div>
  </div>
  

  <br/>
  
  <div class="row">
    <div class="col-md-12">
		<div class="form-group">
      <label class="control-label" for="endDate">End Date(yyyy-mm-dd)</label>
        <div class="col-sm-7">
          <input class="form-control" id="endDate" name="endDate"required="required" type="text" > 
            </div>
    </div>
    </div>
  </div>
  <br/>

	
  <div class="row">
	  <div class="col-md-12">
		<div class="form-group">
		  <div class="col-sm-offset-2 col-sm-7">
			<input class="btn btn-success" name="submit" type="submit" value=
			"Download">
		  </div>
		</div>
	  </div>
	</div>
	
	<a href="${pageContext.request.contextPath}/welcome">Home</a><br />
	
</form:form>
