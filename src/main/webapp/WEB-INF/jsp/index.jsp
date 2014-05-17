<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<!DOCTYPE html>
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
		<title>Decision Maker</title>
		<link rel="stylesheet" href="<c:url value='/lib/css/decision-maker.css'/>"/>
		<script src="<c:url value='/lib/js/decision-maker.js'/>"></script>
		<script src="<c:url value='/lib/js/jquery-1.11.0.min.js'/>"></script>
		<script type="text/javascript">
			$(document).ready(function() {
				$("#userLogin").hide();
				$("#login").click(function() {
					$("input[text]").val("");
					$("input[password]").val("");
					$("#userLogin").toggle();
				});
			});
		</script>
	</head>
	<body>
		<h2>Index Page</h2>
		<p>This is the entry point of the Decision Maker application.</p>
		<div>
			<span><b>Links</b></span>
			<c:if test="${not loggedIn}">
				<c:import url="includes/loggedOutNav.jsp"/>
			</c:if>
			<c:if test="${loggedIn}">
				<c:import url="includes/loggedInNav.jsp"/>
				<c:if test="${sessionScope.user.account.admin}">
					<br/>
					<c:import url="includes/super-nav.jsp"/>
				</c:if>
			</c:if>
		</div>
	</body>
</html>