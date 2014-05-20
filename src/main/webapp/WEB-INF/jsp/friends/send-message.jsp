<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Decision Maker - Send Messages to Friends</title>
	<link rel="stylesheet" href="<c:url value='../lib/css/decision-maker.css'/>"/>
	<script src="<c:url value='/lib/js/decision-maker.js'/>"></script>
	<script src="<c:url value='/lib/js/jquery-1.11.0.min.js'/>"></script>
	<script type="text/javascript">
		$(document).ready(function() {
			$("#submit").click(function() {
				$.each($("input[name='check']:checked").closest("td").siblings("td"),
				       function () {
						console.log($(this).text());
				       });

			});
		});
	</script>
</head>
<body>

	<c:set var="count" value="0" scope="page" />

	<textarea id="message" rows="4" cols="30"></textarea>

	<c:if test="${not empty friends}">
		<input type="button" value="Submit" id="submit"/>
		
		<table>
			<tr><td>Send to:</td></tr>
			<c:forEach var="friend" items="${friends}">
				<tr>
					<td><input name="check" type="checkbox"/></td>
					<td>${friend.account.username}</td>
					<td class="fid"><input type="hidden" class="friend" value="${friend.id}"/></td>
				</tr>
			</c:forEach>
		</table>
	</c:if>
</body>
</html>