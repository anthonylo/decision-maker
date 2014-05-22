<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Decision Maker - Send Messages to Friends</title>
	<link rel="stylesheet" href="<c:url value='/lib/css/decision-maker.css'/>"/>
	<script src="<c:url value='/lib/js/jquery-1.11.0.min.js'/>"></script>
	<script src="<c:url value='/lib/js/decision-maker.js'/>"></script>
	<script type="text/javascript">
		$(document).ready(function() {
			$("#submit").click(function() {
				$(".error").text("");
				$(".successful").text("");
				var message = $("#message").val();
				if (!isStringEmpty(message) && message.length <= 140) {
					var recipients = [];
					$.each($("input[name='check']:checked").closest("td").siblings("td"),
						function () {
							var recipient = $(this).text();
							recipients.push(recipient);
						});
					if (recipients.length > 0) {
						var requestBodyJson = ( '{ "message" : "' + message + '", "recipients" : ' + JSON.stringify(recipients) + ' }' );
						console.log(requestBodyJson);
						$.ajax({
							url : "/decision-maker/message/send",
							method : "post",
							dataType : "json",
							data : requestBodyJson,
							contentType: "application/json; charset=utf-8",
							success : function(response) {
								$(".successful").text(response);
								$(".checkName").prop("checked", false);
								$("#message").val('');
							}
						});
					} else {
						$(".error").text("You didn't select any recipients!");
					}
				} else {
					$(".error").text("Your message must be within 1-140 characters.");
				}
			});
		});
	</script>
</head>
<body>

	<div>
		<a href="/decision-maker">Return to Index</a>
		<br/><br/>
		<span class="error"></span><span class="successful"></span>
	</div>

	<textarea id="message" rows="4" cols="30"></textarea>

	<c:if test="${not empty friends}">
		<input type="button" value="Submit" id="submit"/>
		
		<table>
			<tr><td>Send to:</td></tr>
			<c:forEach var="friend" items="${friends}">
				<tr>
					<td><input name="check" class="checkName" type="checkbox"/></td>
					<td>${friend.account.username}</td>
				</tr>
			</c:forEach>
		</table>
	</c:if>
</body>
</html>