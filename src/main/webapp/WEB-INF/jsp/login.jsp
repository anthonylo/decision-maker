<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<!DOCTYPE html>
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
		<title>Decision Maker - Login</title>
		<link rel="stylesheet" href="<c:url value='/lib/css/decision-maker.css'/>"/>
		<script src="<c:url value='/lib/js/decision-maker.js'/>"></script>
		<script src="<c:url value='/lib/js/jquery-1.11.0.min.js'/>"></script>
	</head>
	<body>
		<form:form id="userLogin" commandName="account" action="${pageContext.request.contextPath}/login" method="post">
			<table>
				<tr>
					<td>Username</td>
					<td><input type="text" name="username" maxlength="30" /></td>
				</tr> 
				<tr>
					<td>Password</td>
					<td><input type="password" name="password" maxlength="30" /></td>
				</tr> 
				<tr>
					<td colspan="2"><input type="submit" value="Login" /> <input type="button" value="Register" onclick="registerRedirect();" /></td>
				</tr>
			</table>
		</form:form>
		<span class="error">${error}</span>
	</body>
</html>