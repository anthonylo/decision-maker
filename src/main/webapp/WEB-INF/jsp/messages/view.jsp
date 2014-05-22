<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<!DOCTYPE html>
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
		<title>Decision Maker - Messages</title>
		<link rel="stylesheet" href="<c:url value='/lib/css/decision-maker.css'/>"/>
		<script src="<c:url value='/lib/js/jquery-1.11.0.min.js'/>"></script>
		<script src="<c:url value='/lib/js/decision-maker.js'/>"></script>
		<script type="text/javascript">
			$(document).ready(function() {
				$(".deleteMessage").click(function() {
					console.log("do something");
				});
			});
		</script>
	</head>
	<body>
		<spring:eval expression="type == T(com.decisionmaker.domain.message.MessageType).RECEIVED" var="received" />
		<spring:eval expression="type == T(com.decisionmaker.domain.message.MessageType).SENT" var="sent" />
		<p><a href="/decision-maker">Return to Index</a></p>
		<span class="error"></span>
		<div id="urgent-messages"></div>
		<div id="related-messages">
			<c:choose>
				<c:when test="${not empty messages}">
					<table class="dm-table">
						<tr class="dm-tr tr-header">
							<td>ID</td>
							<td>
								<c:if test="${received}">
									Sender
								</c:if>
								<c:if test="${sent}">
									Receiver
								</c:if>
							</td>
							<td>Message</td>
							<td>Date</td>
							<td>Action</td>
							<td>Delete</td>
						</tr>
						<c:forEach items="${messages }" var="message">
							<tr class="dm-tr">
								<td>${message.id}</td>
								<td>
									<c:if test="${received}">
										${message.sender.account.username}
									</c:if>
									<c:if test="${sent}">  
						                <c:forEach items="${message.recipients}" var="recipient" varStatus="loop">
											${recipient.account.username}<br/>
										</c:forEach>
									</c:if>
								</td>
								<td>${message.message}</td>
								<td>${message.datePosted}</td>
								<td>
									<c:if test="${received}">
										Send Reply
									</c:if>
									<c:if test="${sent}">
										<c:forEach items="${message.recipients}" var="recipient">
											${recipient.account.username} - View Reply<br/>
										</c:forEach>
									</c:if>
								</td>
								<td>
									<a href="javascript:void(0);" class="deleteMessage">[X]</a>
								</td>
							</tr>
						</c:forEach>
					</table>
				</c:when>
				<c:otherwise>
					You have no messages
				</c:otherwise>
			</c:choose>
		</div>
	</body>
</html>