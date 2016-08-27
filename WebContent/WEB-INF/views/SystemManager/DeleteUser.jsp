<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>

<script type="text/javascript">

	$('#SysManager').validate({ 
        rules: {
            username:  {
                required: true
            }
        },
        messages: {
            username: "Please enter user name.",
        },submitHandler: function (form) {
        // setup some local variables
        var $form = $(form);
        // let's select and cache all the fields
        var $inputs = $form.find("input, select, button, textarea");
        // serialize the data in the form
        var serializedData = $form.serialize();

        // let's disable the inputs for the duration of the ajax request
        $inputs.prop("disabled", true);

        request = $.ajax({
            url: $(form).attr("action"),
            type: "post",
            data: serializedData
        });

        // callback handler that will be called on success
        request.done(function (response, textStatus, jqXHR) {
            // log a message to the console
            $(".clearfix").html(response);
        });

        // callback handler that will be called on failure
        request.fail(function (jqXHR, textStatus, errorThrown) {
            // log the error to the console
            console.error(
                "The following error occured: " + textStatus);
        });

        // callback handler that will be called regardless
        // if the request failed or succeeded
        request.always(function () {
            // reenable the inputs
            $inputs.prop("disabled", false);
        });

    }

    });
</script>

<form:form method="POST" action="${pageContext.request.contextPath}/sysManager/DeleteUserAccount" modelAttribute="SysManager" >
 <c:if test="${!empty getUserName}">
	<br/>
	<div class="row">
	  	<div class="col-md-12">
			<div class="form-group">
			  <label class="control-label" for="username">UserName</label>
			    <div class="col-sm-7">
					<input class="form-control" id="username" name="username" type="text" value="${getUserName[0].username}"> 
				</div>
			</div>
  		</div>
	</div>
	
	<br/>
	
	<div class="row">
	  <div class="col-md-12">
			<div class="form-group">
			  <div class="col-sm-offset-2 col-sm-7">
				<input class="btn btn-success" name="commit" type="submit" value=
				"Delete User Account">
			  </div>
			</div>
	  </div>
	</div>
		
  </c:if>
</form:form>