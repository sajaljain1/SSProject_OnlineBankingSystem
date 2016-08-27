<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<div>
<form:form name="myform" action="getMoreInfo" method="post" modelAttribute="piiForm">
        <table id="item-list" class="table table-striped table-bordered">
            <thead class="dataTableHeader">
                <tr>
                    <th>User Name</th>
                    <th>First Name</th>
                    <th>Last Name</th>
                    <th></th>
                </tr>
            </thead>
            <tbody>
				<c:forEach items="${piiForm.users}" var="user" varStatus="status">
					<tr>
						<td>${user.username}</td>
						<td>${user.firstname}</td>
						<td>${user.lastname}</td>
						<td>
						<button class="btn btn-large btn-success" type="submit" name="userId" value="${user.username}" >Details</button>
						</td>
					</tr>
				</c:forEach>
			</tbody>
        </table>  
    </form:form>
     
</div>