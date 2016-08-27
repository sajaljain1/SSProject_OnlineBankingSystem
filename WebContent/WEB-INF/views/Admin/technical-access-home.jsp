<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<div>
<form:form name="myform" action="technicalAccountAccess" method="post">
       First Name: <input type="text" name="firstName">
       Last Name: <input type="text" name="lastName">
         <button class="btn btn-large btn-success" type="submit" name="userId" value="${user.username}" >Search</button>
    </form:form>
     
</div>