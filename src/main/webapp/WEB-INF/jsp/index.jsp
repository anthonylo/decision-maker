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
				$("#create").click(function() {
					$.ajax({
						url : "/decision-maker/create/anthony",
						success : function(response) { 
							$(".successful").text(response);
						}, error : function(response) {
							$(".error").text(response);
						}
					});
				});
				$("#createrandom").click(function() {
					$.ajax({
						url : "/decision-maker/admin/create/random",
						success : function(response) { 
							$(".successful").text(response);
						}, error : function(response) {
							$(".error").text(response);
						}
					});
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
				<c:import url="includes/logged-out-nav.jsp"/>
			</c:if>
			<c:if test="${loggedIn}">
				<c:import url="includes/logged-in-nav.jsp"/>
			</c:if>
		</div>
		<span class='error'>${error}</span>
		<span class='successful'>${successful}</span>
	</body>
</html>