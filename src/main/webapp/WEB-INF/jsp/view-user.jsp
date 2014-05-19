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
				$("#edit").hide();
				$("input").attr("readonly", true);
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
				$("#allow").click(function() {
					$("input").attr("readonly", false);
					$("#edit").show();
				});
				
				$("#edit").click(function() {
					var userJson = generateJsonOfUser();
					var userId = $("#id").val();
					var editUrl = "/decision-maker/user/id/" + userId;
					$.ajax({
						type : "put",
						url : editUrl,
					    dataType : "json",
						data : userJson,
						contentType: "application/json; charset=utf-8",
					    success : function(response) {
							console.log(userJson);
					    	console.log("success: " + editUrl);
					    	console.log(response);
					    }, error : function(xhr) {
					    	console.log(xhr);
					    	console.log("FAIL");
					    }
					});
// 					$("#edit").hide();
				});
			});
		</script>
	</head>
	<body>
		<p><a href="/decision-maker">Return to Index</a></p>
		<input type="button" value="Refresh" id="refresh" /><br/>
		<input type="button" value="Edit" id="allow" /> <input type="button" value="Submit" id="edit"/>
		<c:import url="includes/user-view.jsp"/>
	</body>
</html>