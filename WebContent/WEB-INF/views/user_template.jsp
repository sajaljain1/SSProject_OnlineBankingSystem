<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%@ taglib prefix="sec"
	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<!DOCTYPE html>
<html lang="en" class="no-js">
<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<title>Secure Banking System</title>

<meta name="description" content="">
<meta name="viewport" content="width=device-width, initial-scale=1.0">

<link rel="shortcut icon"
	href="<c:url value="/resources/img/favicon.png" />">
<link rel="apple-touch-icon" sizes="57x57"
	href="<c:url value="/resources/img/apple-touch-icon.png" />">
<link rel="apple-touch-icon" sizes="72x72"
	href="<c:url value="/resources/img/apple-touch-icon-72x72.png" />">
<link rel="apple-touch-icon" sizes="114x114"
	href="<c:url value="/resources/img/apple-touch-icon-114x114.png" />">
<link rel="stylesheet" href="<c:url value="/resources/css/application.css" />">
<script src="<c:url value="/resources/js/jquery-1.10.2.min.js" />"></script>
<script src="<c:url value="/resources/js/jquery.validate.min.js" />"></script>
<script src="<c:url value="/resources/js/validate.js" />"></script>
<script src="<c:url value="/resources/js/additional-methods.min.js" />"></script>
<link rel="stylesheet" href="<c:url value="/resources/css/jquery-ui.min.css" />">
<script src="<c:url value="/resources/js/jquery-ui.min.js" />"></script>

<noscript>
    <style type="text/css">
        #tiles_table {display:none;}
    </style>
    <div class="noscriptmsg">
    	You don't have javascript enabled.  Good luck with that.
    </div>
</noscript>
</head>

<body  class="ui_charcoal" data-page="profiles:show">
<table id="tiles_table" width="100%" style="border: none;">
        <tr>
            <td colspan="1"><tiles:insertAttribute name="header" /></td>
        </tr>
        <tr>
            <td width="20%" nowrap="nowrap"><tiles:insertAttribute name="menu" /></td>
            <td width="80%"><tiles:insertAttribute name="body" /></td>
        </tr>
    </table>
</body>
</html>