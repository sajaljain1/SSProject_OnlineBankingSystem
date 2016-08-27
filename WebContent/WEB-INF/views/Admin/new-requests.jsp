<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<div>
<form:form name="myform" action="sendInfo" method="post" modelAttribute="piiForm">
        <table id="item-list" class="table table-striped table-bordered">
            <thead class="dataTableHeader">
                <tr>
                    <th width="10%" style="text-align:center">
                                     CheckBox
                    </th>
                    <th>User Name</th>
                    <th>First Name</th>
                    <th>Last Name</th>
                </tr>
            </thead>
            <tbody>
				<c:forEach items="${piiForm.users}" var="user" varStatus="status">
					<tr>
						<td style="text-align: center"><form:checkbox path="checkedUsers[${status.index}].checked" /></td>
						<td>${user.username}<form:hidden path="users[${status.index}].username"/></td>
						<td>${user.firstname}<form:hidden path="users[${status.index}].firstname"/></td>
						<td>${user.lastname}<form:hidden path="users[${status.index}].lastname"/></td>
					</tr>
				</c:forEach>
			</tbody>
        </table>  
	<button class="btn btn-large btn-success" type="submit" value="Send Info" >Send Info</button>
    </form:form>
     
</div>