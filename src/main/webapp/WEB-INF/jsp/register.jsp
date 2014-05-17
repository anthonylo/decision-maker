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
	</head>
	<body>
		<p><b>User Registration</b></p>
		<p><a href="/decision-maker">Return to Index</a></p>
		<form:form id="userRegistration" commandName="user" name="${pageContext.request.contextPath}/register" method="post">
			<c:import url="includes/userform.jsp"/>
			<br/>
			<input type="submit" value="Submit" /> <input type="reset"/>
			<span class="error">${error}</span><span class="successful">${successful}</span>
		</form:form>
	</body>
</html>