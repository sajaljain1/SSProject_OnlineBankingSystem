<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<div>
<form:form name="myform" action="approve" method="post" modelAttribute="piiForm">
        <table id="item-list" class="table table-striped table-bordered">
            <thead class="dataTableHeader">
                <tr>
                    <th width="10%" style="text-align:center">
                                     CheckBox
                    </th>
                    <th>PII</th>
                    <th>First Name</th>
                    <th>Last Name</th>
                    <th>DOB</th>
                    <th>Address</th>
                    <th style="display:none">hidden</th>
                </tr>
            </thead>
            <tbody>
				<c:forEach items="${piiForm.users}" var="user" varStatus="status">
					<tr>
						<td style="text-align: center"><form:checkbox path="checkedUsers[${status.index}].checked" /></td>
						<td>${user.pii}<form:hidden path="users[${status.index}].pii"/></td>
						<td>${user.firstname}<form:hidden path="users[${status.index}].firstname"/></td>
						<td>${user.lastname}<form:hidden path="users[${status.index}].lastname"/></td>
						<td>${user.birthdate}<form:hidden path="users[${status.index}].birthdate"/></td>
						<td>${user.address}<form:hidden path="users[${status.index}].lastname"/></td>
						<td style="display:none">${user.username}<form:hidden path="users[${status.index}].username"/></td>
					</tr>
				</c:forEach>
			</tbody>
        </table>
        <c:if test="${!empty piiForm.users}">  
	         <button class="btn btn-large btn-success" type="submit" name="action" value="approve" >Approve</button>
	         <button class="btn btn-large btn-success" type="submit" name="action" value="reject" >Reject</button>
	    </c:if>
    </form:form>
     
</div>