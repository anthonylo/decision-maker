<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<!DOCTYPE html>
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
		<title>Decision Maker - View User</title>
		<link rel="stylesheet" href="<c:url value='/lib/css/decision-maker.css'/>"/>
		<script src="<c:url value='/lib/js/jquery-1.11.0.min.js'/>"></script>
		<script type="text/javascript">
			$(document).ready(function() {
				$("#refresh").click(function() {
					location.reload();
				});
				
				$(".edit").click(function() {
					$(".successful").text("");
					var id = $(this).closest('tr').find('td:first').text().trim();
					console.log("admin-edit url: /decision-maker/admin/edit?id="+id);
					location.replace("/decision-maker/admin/edit?id="+id);
				});
				
				$(".delete").click(function() {
					$(".successful").text("");
					$(".error").text("");
					var deletedusername = $(this).closest("tr").find("td:eq(2)").text().trim();
					var username = "${sessionScope.username}";
					var deleteUrl = "/decision-maker/admin/"+username+"/delete/"+deletedusername;
					var trToDelete = $(this).closest("tr");
					$.ajax({
						url : deleteUrl,
						method : "delete",
						success : function(response) {
							location.reload();
							$(".successful").text(response);
						}, 
						error : function(xhr) {
							$(".error").text("The user '" + deletedusername + "' couldn't be deleted");
						}
					});
				});
			});
		</script>
	</head>
	<body>

		<div><a href="/decision-maker">Return to Index</a></div>
		<div>Admin - ${sessionScope.username}</div>
		<div>Page ${page} of ${totalPages}. Total Users: ${count}</div>
		<div>
			<c:if test="${page > 1}"><a href="/decision-maker/admin/viewusers?page=${page-1}">Previous</a></c:if> 
			<c:if test="${page < totalPages}"><a href="/decision-maker/admin/viewusers?page=${page+1}">Next</a></c:if>
		</div>
		<div><input type="button" id="refresh" value="Refresh"/> <span class="successful"></span> <span class="error"></span></div>
		<br/>
		<table class="dm-table">
			<tr class="dm-tr" >
				<td><b>User ID</b></td>
				<td><b>Name</b></td>
				<td><b>Username</b></td>
				<td><b>Email</b></td>
				<td><b>Phone Number</b></td>
				<td><b>Action</b></td>			
			</tr>
			<c:forEach items="${users}" var="user">
				<tr class="dm-tr">
					<td>
						${user.id}
						<c:if test="${user.account.admin}">
							<br/><b>ADMIN</b>
						</c:if>
					</td>
					<td>${user.firstName} ${user.lastName}</td>
					<td>${user.account.username}</td>
					<td>${user.contactInfo.email}</td>
					<td>${user.contactInfo.phoneNumber}</td>
					<td>
						<c:if test="${user.account.username != sessionScope.username}">
							<input type="button" class="edit" value="Edit"/>
							<br /> 
							<input type="button" class="delete"  value="Delete"/>
						</c:if>
					</td>
				</tr>
			</c:forEach>
		</table>
	</body>
</html>