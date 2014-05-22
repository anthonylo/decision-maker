<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<!DOCTYPE html>
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
		<title>Decision Maker - View Friends</title>
		<link rel="stylesheet" href="<c:url value='/lib/css/decision-maker.css'/>"/>
		<script src="<c:url value='/lib/js/jquery-1.11.0.min.js'/>"></script>
		<script src="<c:url value='/lib/js/decision-maker.js'/>"></script>
		<script type="text/javascript">
			$(document).ready(function() {
				var friendsCt = ${friends.size()};
				$(".remove").click(function() {
					var friendUsername = $(this).closest("tr").find("td:eq(2)").text().trim();
					$.ajax({
						url : "/decision-maker/friends/remove/" + friendUsername,
						method : "get",
						success : function(response) {
							$(".successful").text(response);
							friendsCt--;
							$(this).closest("tr").remove();	
							console.log("No longer friends with: " + friendUsername);
							if (friendsCt == 0) {
								$("#friendsDiv").empty();
								$("#friendsDiv").append("<p>You have no more friends!</p>");
							}
						}, error : function(xhr) {
							var response = xhr.responseText.match(/.*<body.*>([\s\S]*)<\/body>.*/);
							console.log(response);
						}
					});
				});
			});
		</script>
	</head>
	<body>
	
		<p><a href="/decision-maker">Return to Index</a></p>
		<span class="successful"></span>
		<div id="friendsDiv">
			<c:if test="${not empty friends }">
				<table class="dm-table">
					<tr class="dm-tr tr-header">
						<td>Name</td>
						<td>Age</td>
						<td>Username</td>
						<td>Email</td>
						<td>Phone Number</td>
						<td>Friendship Started</td>
						<td>Action</td>
					</tr>
					<c:forEach var="friend" items="${friends}">
						<tr class="dm-tr">
							<td>${friend.firstName} ${friend.lastName}</td>
							<td>${friend.age}</td>
							<td>${friend.account.username}</td>
							<td>${friend.contactInfo.email}</td>
							<td>${friend.contactInfo.phoneNumber}</td>
							<td>${friend.friendshipStarted}</td>
							<td>
								Send Private Message<br/>
								<a href="javascript:void(0);" class="remove">Remove</a>
							</td>
						</tr>
					</c:forEach>
				</table>
			</c:if>
			<c:if test="${empty friends}">
				You have no friends...
			</c:if>
		</div>
	
	</body>
</html>