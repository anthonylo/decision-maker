<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<!DOCTYPE html>
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
		<title>Decision Maker - View Friends</title>
		<link rel="stylesheet" href="<c:url value='../lib/css/decision-maker.css'/>"/>
		<script src="<c:url value='/lib/js/decision-maker.js'/>"></script>
		<script src="<c:url value='/lib/js/jquery-1.11.0.min.js'/>"></script>
	</head>
	<body>
	
		<table class="dm-table">
			<tr class="dm-tr">
				<td>ID</td>
				<td>Name</td>
				<td>Age</td>
				<td>Username</td>
				<td>Email</td>
				<td>Phone Number</td>
				<td>Friendship Started</td>
				
			</tr>
			<c:forEach var="friend" items="${friends}">
				<tr class="dm-tr">
					<td>${friend.id}</td>
					<td>${friend.firstName} ${friend.lastName}</td>
					<td>${friend.age}</td>
					<td>${friend.account.username}</td>
					<td>${friend.contactInfo.email}</td>
					<td>${friend.contactInfo.phoneNumber}</td>
					<td>${friend.friendshipStarted}</td>
				</tr>
			</c:forEach>
		</table>
	
	</body>
</html>