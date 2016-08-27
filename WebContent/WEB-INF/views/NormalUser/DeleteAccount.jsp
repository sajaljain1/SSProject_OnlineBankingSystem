<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@page session="true"%>
<html>
<body>

<h3>Are you sure you want to delete account?</h3>

<form action="${pageContext.request.contextPath}/normalUser/DeleteAccountYes" method="get"><input type="submit" value="YES"/> </form>
<br>
<a href="${pageContext.request.contextPath}/userHome">Home</a>
</body>
</html>