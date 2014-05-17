<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<!DOCTYPE html>
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
		<title>Decision Maker - <c:out value="${sessionScope[username]}"/></title>
		<link rel="stylesheet" href="<c:url value='/lib/css/decision-maker.css'/>"/>
		<script src="<c:url value='/lib/js/decision-maker.js'/>"></script>
		<script src="<c:url value='/lib/js/jquery-1.11.0.min.js'/>"></script>
		<script type="text/javascript">
			$(document).ready(function() {
				$("#refresh").click(function() {
					var username = "${sessionScope.username}";
					$.ajax({
						url : "/decision-maker/refresh",
						method : "post",
						data : "username=" + username,
						success : function(response) {
							location.reload();
						}
					});
				});
			});
		</script>
	</head>
	<body>
		<p><a href="/decision-maker">Return to Index</a></p>
		<input type="button" value="Refresh" id="refresh" />
		<c:import url="includes/user-view.jsp"/>
	</body>
</html>