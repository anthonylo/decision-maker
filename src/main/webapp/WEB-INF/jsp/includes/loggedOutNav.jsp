<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<div><a href='/decision-maker/register'>Register</a></div>
<div><a id='login'>Login</a></div>
<form:form id='userLogin' action='${pageContext.request.contextPath}/login' method='post'>
	<table>
		<tr>
			<td>Username</td>
			<td><input type='text' name='username' maxlength='30' /></td>
		</tr> 
		<tr>
			<td>Password</td>
			<td><input type='password' name='password' maxlength='30' /></td>
		</tr> 
		<tr>
			<td colspan='2' align="center">
				<input type='submit' value='Login' />
				<input type='reset' />
			</td>
		</tr>
	</table>
</form:form>
<span class='error'>${error}</span>