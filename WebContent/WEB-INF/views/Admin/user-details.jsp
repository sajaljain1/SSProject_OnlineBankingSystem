<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<div>
<form:form name="myform" action="deleteUser" method="post">
		<table class="table table-striped table-bordered">
				<tr><td>User name: ${user.username}</td></tr>
				<tr><td>First name: ${user.firstname}</td></tr>
				<tr><td>Last name: ${user.lastname}</td></tr>
				<tr><td>Phone: ${user.contact}</td></tr>
				<tr><td>Email: ${user.email}</td></tr>
				<tr><td>DOB: ${user.birthdate}</td></tr>
			</tbody>
		</table>
		<button class="btn btn-large btn-success" type="submit" name="userId" value="${user.username}" >Delete User</button>
    </form:form>
</div>