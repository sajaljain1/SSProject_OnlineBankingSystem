<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ page session="false" %>
<html>
<head>
    <title>Success</title>
    
</head>
<body>
<h3>
   ${Message}
</h3>
 <h4>Please do not press the back button or refresh.</h4>
    <br><br>
<a href="${pageContext.request.contextPath}/userHome">Home</a>

</body>
</html>