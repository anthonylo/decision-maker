<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<c:forEach items="${message.recipients}" var="recipient">
	<tr class="dm-tr">
		<td>${message.id}</td>
		<td>${recipient.account.username}<br/></td>
		<td>${message.message}</td>
		<td>${message.datePosted}</td>
		<td><a href="javascript:void(0);" class="viewReply">View Reply</a></td>
		<td><a href="javascript:void(0);" class="deleteMessage">[X]</a></td>
		<td>
		</td>
	</tr>
</c:forEach>