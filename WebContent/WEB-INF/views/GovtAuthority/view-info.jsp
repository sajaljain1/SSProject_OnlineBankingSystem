<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<div>

        <table id="item-list" class="table table-striped table-bordered">
            <thead class="dataTableHeader">
                <tr>
                    <th>PII</th>
                    <th>First Name</th>
                    <th>Last Name</th>
                    <th>DOB</th>
                    <th>Address</th>
                    <th>Phone</th>
                    <th>E-mail</th>
                </tr>
            </thead>
            <tbody>
				<c:forEach items="${piiForm.users}" var="user" varStatus="status">
					<tr>
						<td>${user.pii}</td>
						<td>${user.firstname}</td>
						<td>${user.lastname}</td>
						<td>${user.birthdate}</td>
						<td>${user.address}</td>
						<td>${user.contact}</td>
						<td>${user.email}</td>
					</tr>
				</c:forEach>
			</tbody>
        </table>  
</div>