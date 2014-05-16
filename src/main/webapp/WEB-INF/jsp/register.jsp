<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<!DOCTYPE html>
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
		<title>Decision Maker - Register</title>
		<link rel="stylesheet" href="<c:url value='/lib/css/decision-maker.css'/>"/>
		<script src="<c:url value='/lib/js/jquery-1.11.0.min.js'/>"></script>
		<script type="text/javascript">
			$(document).ready(function() {
				$("#test").hover(
					function() {
						$(this).append($("<span>jQuery Works!</span>"));
					}, function() {
						$(this).find("span:last").remove();
					}
				);
			})
		</script>
	</head>
	<body>
		<p><b>User Registration</b></p>
		<p><a href="/decision-maker">Return to Index</a></p>
		<form:form id="userRegistration" commandName="user" name="${pageContext.request.contextPath}/register" method="post">
			<table>
				<tr><td colspan="2"><b>Personal Information</b></td></tr>
				<tr>
					<td>First Name</td>
					<td><input type="text" name="firstName" maxlength="50" /></td>
				</tr> 
				<tr>
					<td>Last Name</td>
					<td><input type="text" name="lastName" maxlength="50" /></td>
				</tr> 
				<tr>
					<td>Age</td>
					<td><input type="number" name="age" maxlength="4" /></td>
				</tr>
			</table>
			<br/>
			<table>
				<tr><td colspan="2"><b>Account Information</b></td></tr>
				<tr>
					<td>Username</td>
					<td><input type="text" name="account.username" maxlength="30" /></td>
				</tr> 
				<tr>
					<td>Password</td>
					<td><input type="password" name="account.password" maxlength="30" /></td>
				</tr>
				<tr>
					<td>Secret Question</td>
					<td><input type="text" name="account.secretQuestion" maxlength="75" required="required" /></td>
				</tr>
				<tr>
					<td>Secret Answer</td>
					<td><input type="text" name="account.secretAnswer" maxlength="75" required="required" /></td>
				</tr>
			</table>
			<br/>
			<table>
				<tr><td colspan="2"><b>Contact Information</b></td></tr>
				<tr>
					<td>Email Address</td>
					<td><input type="email" name="contactInfo.email" maxlength="50" /></td>
				</tr>
				<tr>
					<td>Phone Number</td>
					<td><input type="tel" name="contactInfo.phoneNumber" maxlength="50" /></td>
				</tr>
			</table>
			<br/>
			<input type="submit" value="Submit" /> <input type="reset"/>
			<span class="error">${error}</span><span class="successful">${successful}</span>
		</form:form>
	</body>
</html>