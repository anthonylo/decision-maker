<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
		<title>Decision Maker</title>
	</head>
	<body>
		<h2>Index Page</h2>
		<p>This is the entry point of the Decision Maker application.</p>
		
		<div>
			<span>Links</span>
			<c:choose>
				<c:when test="${not loggedIn}">
					<span><a href="/decision-maker/login">Login Page</a></span>
				</c:when>
				<c:otherwise>
					<div>Welcome, ${username}!</div>
					<div><a href="/decision-maker/logout">Logout</a></div>
				</c:otherwise>
			</c:choose>
			
		</div>
	</body>
</html>