<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<!DOCTYPE html>
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
		<title>Decision Maker - Add Friends</title>
		<link rel="stylesheet" href="<c:url value='/lib/css/decision-maker.css'/>"/>
		<script src="<c:url value='/lib/js/jquery-1.11.0.min.js'/>"></script>
		<script src="<c:url value='/lib/js/decision-maker.js'/>"></script>
		<script type="text/javascript">
			function send_friend_request(friendId, friendUsername, origQuery) {
				var userId =  ${sessionScope.user.id};
				var userUsername =  '${sessionScope.user.account.username}';
				var requestJson = ( ' { "id" : { "userId" : ' + userId
					+ ', "friendId" : ' + friendId + ' }, "userUsername" : "' + userUsername
					+ '", "friendUsername" : "' + friendUsername + '" } ' );
				$(this).closest("tr").remove();
				$.ajax({
					type : "post",
					url : "/decision-maker/friends/request/" + userId,
					dataType : "json",
					data : requestJson,
					contentType: "application/json; charset=utf-8",
					success : function(response) {
						retrieve_possible_friends(origQuery);
					}, error : function(xhr) {
						console.log(xhr);
						console.log("FAIL");
					}
				});
			}
			
			function generate_quick_user_table(response, origQuery) {
				$(function() {
					var table = $("<table></table>").addClass("dm-table");
					var row = $("<tr class='tr-header'></tr>").addClass("dm-tr");
	                var idCell = $("<td>ID</td>");
	                var nameCell = $("<td>Full name</td>");
	                var usernameCell = $("<td>Username</td>");
	                var emailCell = $("<td>Email</td>");
	                var action = $("<td>Action</td>");
	
	                table.append(row);
	                row.append(idCell);
	                row.append(nameCell);
	                row.append(usernameCell);
	                row.append(emailCell);
	                row.append(action);
	                
					for (var i = 0; i < response.length; i++) {
						var row = $("<tr></tr>").addClass("dm-tr");
	                    var idCell = $("<td>" + response[i].id + "</td>");
	                    var nameCell = $("<td>" + response[i].firstName + " " + response[i].lastName + "</td>");
	                    var usernameCell = $("<td>" + response[i].account.username + "</td>");
	                    var emailCell = $("<td>" + response[i].contactInfo.email + "</td>");
	
	                    var id = response[i].id;
	                    var username = response[i].account.username;
	                    
	                    var action = $("<td><a href='javascript:void(0);' class='addfriend' onclick='send_friend_request(" + id +", \"" + username + "\", \"" + origQuery + "\")'>Send Request</a></td>");
	                    
	                    table.append(row);
	                    row.append(idCell);
	                    row.append(nameCell);
	                    row.append(usernameCell);
	                    row.append(emailCell);
	                    row.append(action);
					}
					$("#possible-friends").append(table);
				});
			}

			function retrieve_possible_friends(friendName) {
				$.ajax({
					url : "/decision-maker/friends/name/"+friendName,
					type : "get",
				    success : function(response) {
						$("#possible-friends").empty();
				    	if (response.length == 0) {
				    		$(".error").text("There is no one with that has a name related to '" + friendName + "'");
				    	} else {
							generate_quick_user_table(response, friendName);
				    	}
				    }
				});
			}
			
			$(document).ready(function() {
				$("#friendQuery").click(function() {
					var friendName = $("#friendQueryText").val();
					if (isStringEmpty(friendName)) {
						$(".error").text("The text box is empty");
					} else {
						$(".error").text("");
						if (friendName.length < 3) {
							$(".error").text("Your query is too short");
						} else {
							retrieve_possible_friends(friendName);
						}
					}
				});
				$("#friendQueryText").keyup(function(e) {
					var len = $(this).val().length;
					var friendName = $(this).val();
					if (isStringEmpty(friendName)) {
						$(".error").text("");
					} else if (len < 3) {
						$(".error").text("Your query is too short");
					} else {
						$(".error").text("");
						var friendName = $(this).val();
						retrieve_possible_friends(friendName);
					}
				});
				$("#empty").click(function() {
					$("#possible-friends").empty();
				});
			});
		</script>
	</head>
	<body>
		
		<p><a href="/decision-maker">Return to Index</a></p>
		Query for a name (doesn't have to be perfect)<br/>
		<input type="text" id="friendQueryText"/><input type="button" id="friendQuery" value="Submit"/>
		<input type="button" id="empty" value="Empty Div"/>
		<span class="error"></span>
		<div id="possible-friends"></div>
	</body>
</html>