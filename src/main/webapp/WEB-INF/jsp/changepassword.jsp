<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Decision Maker - Change Password</title>
<script src="<c:url value='/lib/js/jquery-1.11.0.min.js'/>"></script>
<link rel="stylesheet" href="<c:url value='/lib/css/decision-maker.css'/>"/>
<script type="text/javascript">
	$(document).ready(function() {
		$("#submitbutton").hide();
		$("#confirm").click(function() {
			var selected = $(this).is(":checked");
			if (selected) {
				$("#submitbutton").show();
			} else {
				$("#submitbutton").hide();
			}
		});
	});
</script>
</head>
<body>

<p><a href="/decision-maker">Return to Index</a></p>

<form:form id="changePassword" commandName="changepass" name="${pageContext.request.contextPath}/changepassword" method="post">
	<table>
		<tr>
			<td>Old Password</td>
			<td><input type="password" name="oldPassword" maxlength="30" /></td>
		</tr> 
		<tr>
			<td>New Password</td>
			<td><input type="password" name="newPassword" maxlength="30" /></td>
		</tr>
		<tr>
			<td><label><input type="checkbox" name="confirm" id="confirm" value="value">Confirm?</label></td> <td><input type="submit" id="submitbutton" value="Change Password"/></td>
		</tr>
	</table>
</form:form>
<span class="error">${error}</span><span class="successful">${successful}</span>

</body>
</html>