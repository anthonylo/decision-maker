<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<!DOCTYPE html>
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
		<title>Decision Maker - Edit User</title>
		<link rel="stylesheet" href="<c:url value='/lib/css/decision-maker.css'/>"/>
		<script src="<c:url value='/lib/js/jquery-1.11.0.min.js'/>"></script>
		<script src="<c:url value='/lib/js/decision-maker.js'/>"></script>
		<script type="text/javascript">
			$(document).ready(function() {
				
				$("#edituser").click(function() {
					var editUrl = $("#userEdit").attr("name");
					console.log(JSON.stringify(second));
					console.log(second);
					$.ajax({
						url : editUrl,
						type : "put",
						data : JSON.stringify(userJson),
						contentType: "application/json; charset=utf-8",
					    sync : false,
					    dataType : "json",
					    success : function(response) {
					    	console.log(test);
					    }
					});
				});

				$("#refresh").click(function() {
					var username = "${user.account.username}";
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
		<p><a href="/decision-maker/admin/viewusers">Return to Users Page</a></p>
		<form:form id="userEdit" commandName="user" name="${pageContext.request.contextPath}/admin/edit" method="post">
			<c:import url="includes/user-view.jsp"/>
			<br/>
			<input type="submit" value="Edit User"/>
			<input type="button" value="Refresh" id="refresh" />
			<input type="reset"/>
		</form:form>
		<span class="error">${error}</span><span class="successful">${successful}</span>
	</body>
</html>