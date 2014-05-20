<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<table>
<tr>
	<td><div>Welcome back, ${sessionScope.user.firstName} ${sessionScope.user.lastName}!</div></td>
</tr>
<tr>
	<td>
		<br/>
		<b>Friends</b><br/>
		<a href='/decision-maker/friends/view'>View Friends</a><br/>
		<a href='/decision-maker/friends/view/requests'>View Friend Requests</a><br/>
		<a href='/decision-maker/friends/add'>Add Friends</a><br/>
		<a href='/decision-maker/friends/message/send'>Send Message</a><br/>
	</td>
	
	<c:if test="${username == 'anthony.lo' and not sessionScope.user.account.admin}">
		<td>
			<b>Special</b><br/>
			<a href="admin/become">Become Administrator</a>
		</td>
	</c:if>
	
	<c:if test="${sessionScope.user.account.admin}">
		<td>
			<c:import url="includes/super-nav.jsp"/>
		</td>
	</c:if>
	
</tr>

<tr>
	<td>
	<br/>
	<b>Account Related</b><br/>
	<a href='/decision-maker/view'>Account Information</a><br/>
	<a href='/decision-maker/changepassword'>Change Password</a><br/>
	<div><a href='/decision-maker/logout'>Logout</a></div>
	</td>
</tr>
</table>