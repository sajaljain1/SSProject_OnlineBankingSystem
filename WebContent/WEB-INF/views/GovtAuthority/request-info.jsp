<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<div>
<form:form name="myform" action="requestInfo" method="post">
       PII: <input type="text" name="pii">
         <button class="btn btn-large btn-success" type="submit"  >Request</button>
    </form:form>
     
</div>