<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

${message.id}
<tr class="dm-tr">
	<td>${message.id}</td>
	<td>${message.sender.account.username}</td>
	<td>${message.message}</td>
	<td>${message.datePosted}</td>
	<td><a href="javascript:void(0);" class="viewReply">Send Reply</a><br/></td>
	<td><a href="javascript:void(0);" class="deleteMessage">[X]</a></td>
	<td>
	</td>
</tr>