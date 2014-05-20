<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Decision Maker - Friend Requests</title>
<link rel="stylesheet" href="<c:url value='/lib/css/decision-maker.css'/>"/>
<script src="<c:url value='/lib/js/decision-maker.js'/>"></script>
<script src="<c:url value='/lib/js/jquery-1.11.0.min.js'/>"></script>
</head>
<body>

	<table class="dm-table">
		<tr class="dm-tr">
			<td><b>Requester</b></td>
			<td><b>Request Stated</b></td>
			<td><b>Accept</b></td>
		</tr>
		<c:forEach items="${friendRequests}" var="request">
			<tr class="dm-tr">
				<td>${request.userUsername}</td>
				<td>${request.requestStated}</td>
				<td><a href="#" class="voidlink acceptreq">Yes</a></td>
			</tr>
		</c:forEach>
	</table>
</body>
</html>