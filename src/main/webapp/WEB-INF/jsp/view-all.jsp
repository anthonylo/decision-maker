<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<!DOCTYPE html>
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
		<title>Decision Maker - View User</title>
		<link rel="stylesheet" href="<c:url value='/lib/css/decision-maker.css'/>"/>
		<script src="<c:url value='/lib/js/jquery-1.11.0.min.js'/>"></script>
	</head>
	<body>

		<div><a href="/decision-maker">Return to Index</a></div>
		<div>Admin - ${sessionScope.username}</div>
		<div>Page ${page} of ${totalPages}. Total Users: ${count}</div>
		<div><c:if test="${page > 1}">Previous</c:if> <c:if test="${page < totalPages}">Next</c:if></div>
		<br/>
		<table>
			<tr>
				<td align="center">User ID</td>
				<td>Name</td>
				<td>Username</td>
				<td>Email</td>
				<td>Phone Number</td>
				<td>Action</td>			
			</tr>
			<c:forEach items="${users}" var="user">
				<tr class="viewall-tr">
					<td align="center">
						${user.id}<c:if test="${user.account.admin}"><br/><b>ADMIN</b></c:if>
					</td>
					<td>${user.firstName} ${user.lastName}</td>
					<td>${user.account.username}</td>
					<td>${user.contactInfo.email}</td>
					<td>${user.contactInfo.phoneNumber}</td>
					<td align="center">
						<c:if test="${user.account.username != sessionScope.username}">
							<a href="/admin/edit?id=${user.id}">Edit</a> <br /> <a href="/decision-maker/admin/delete?id=${user.id}">Delete</a>
						</c:if>
					</td>
				</tr>
			</c:forEach>
		</table>
	</body>
</html>