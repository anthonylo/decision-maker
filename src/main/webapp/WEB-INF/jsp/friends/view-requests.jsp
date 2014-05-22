<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Decision Maker - Friend Requests</title>
<link rel="stylesheet" href="<c:url value='/lib/css/decision-maker.css'/>"/>
<script src="<c:url value='/lib/js/jquery-1.11.0.min.js'/>"></script>
<script src="<c:url value='/lib/js/decision-maker.js'/>"></script>
<script type="text/javascript">
</script>
</head>
<body>
	<p><a href="/decision-maker">Return to Index</a></p>
	<c:choose>
	<c:when test="${(not empty sessionScope.user.friendRequesters) or (not empty sessionScope.user.friendRequested)}">
		<table class="dm-table">
			<c:if test="${not empty sessionScope.user.friendRequesters}">
				<tr class="dm-tr">
					<td><b>Requester</b></td>
					<td><b>Request Stated</b></td>
					<td><b>Accept</b></td>
				</tr>
				<c:forEach items="${sessionScope.user.friendRequesters}" var="request">
					<tr class="dm-tr">
						<td>${request.userUsername}</td>
						<td>${request.requestStated}</td>
						<td><a href="javascript:void(0);" class="acceptreq">Yes</a> / <a href="javascript:void(0);" class="rejectreq">No</a></td>
					</tr>
				</c:forEach>
			</c:if>
			<tr><td></td></tr>
			<c:if test="${not empty sessionScope.user.friendRequested}">
				<tr class="dm-tr">
					<td><b>Requestee</b></td>
					<td><b>Request Stated</b></td>
					<td><b>Cancel</b></td>
				</tr>	
				<c:forEach items="${sessionScope.user.friendRequested}" var="request">
					<tr class="dm-tr">
						<td>${request.friendUsername}</td>
						<td>${request.requestStated}</td>
						<td><a href="javascript:void(0);" class="cancelreq">Yes</a></td>
					</tr>
				</c:forEach>
			</c:if>
		</table>
	</c:when>
	<c:otherwise>
		You have no friend requests!
	</c:otherwise>
	</c:choose>
</body>
</html>