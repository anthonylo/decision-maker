<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<!DOCTYPE html>
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
		<title>Decision Maker - Add Friends</title>
		<link rel="stylesheet" href="<c:url value='../lib/css/decision-maker.css'/>"/>
		<script src="<c:url value='/lib/js/decision-maker.js'/>"></script>
		<script src="<c:url value='/lib/js/jquery-1.11.0.min.js'/>"></script>
		<script type="text/javascript">
			$(document).ready(function() {
				$("#friendQuery").click(function() {
					var friendName = $("#friendQueryText").val();
					if (isStringEmpty(friendName)) {
						$(".error").text("The text box is empty");
					} else {
						$(".error").text("");
						$.ajax({
							url : "/decision-maker/friends/name/"+friendName,
							type : "get",
						    success : function(response) {
						    	if (response.length == 0) {
						    		$(".error").text("There is no one with that has a name related to '" + friendName + "'");
						    	} else {
							    	var userTable = generateQuickUserTable(response);
									$("#fillMe").empty();
							    	$("#fillMe").replaceWith(userTable);
						    	}
						    }
						});
					}
				});
				$("#empty").click(function() {
					$("#fillMe").empty();
				});
			});
			function request_friend(friendId, friendUsername) {
				var userId =  ${sessionScope.user.id};
				var userUsername =  '${sessionScope.user.account.username}';
				var requestJson = ( ' { "id" : { "userId" : ' + userId 
					+ ', "friendId" : ' + friendId + ' }, "userUsername" : "' + userUsername 
					+ '", "friendUsername" : "' + friendUsername + '" } ' );
				$.ajax({
					type : "post",
					url : "/decision-maker/friends/request/" + userId,
				    dataType : "json",
					data : requestJson,
					contentType: "application/json; charset=utf-8",
				    success : function(response) {
						console.log(requestJson);
				    	console.log(response);
				    }, error : function(xhr) {
				    	console.log(xhr);
				    	console.log("FAIL");
				    }
				});
				
			}
		</script>
	</head>
	<body>
		
		Query for a name (doesn't have to be perfect)<br/>
		<input type="text" id="friendQueryText"/><input type="button" id="friendQuery" value="Submit"/>
		<input type="button" id="empty" value="Empty Div"/>
		<span class="error"></span>
		<table id="fillMe"></table>
	</body>
</html>