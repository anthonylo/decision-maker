<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Decision Maker - Login Page</title>
<link rel="stylesheet" type="text/css" href="/lib/decision-maker.css">
</head>
<body>

	<form:form modelAttribute="account">
		<table>
			<tr>
				<td>
					<label for="usernameInput">User Name: </label>
		      		<form:input path="username" id="usernameInput" />
	      		</td>
      		</tr>
			<tr>
				<td>
					<label for="passwordInput">Password: </label>
		      		<form:password path="password" id="passwordInput" />
	      		</td>
      		</tr>
			<tr><td><input type="submit" value="Submit" /></td></tr>
		</table>
	</form:form>

	<p class="error">${error}</p>
</body>
</html>