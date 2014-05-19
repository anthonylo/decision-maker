<table>
	<tr><td colspan="2"><b>Personal Information</b></td></tr>
	<tr>
		<td>First Name</td>
		<td><input type="text" name="firstName" maxlength="50" value="${user.firstName}" /></td>
	</tr> 
	<tr>
		<td>Last Name</td>
		<td><input type="text" name="lastName" maxlength="50" value="${user.lastName}" /></td>
	</tr> 
	<tr>
		<td>Age</td>
		<td><input type="number" name="age" maxlength="4" value="${user.age}" /></td>
	</tr>
</table>
<br/>
<table>
	<tr><td colspan="2"><b>Account Information</b></td></tr>
	<tr>
		<td>Username</td>
		<td><input type="text" name="account.username" maxlength="30" value="${user.account.username}" /></td>
	</tr> 
	<tr>
		<td>Password</td>
		<td><input type="password" name="account.password" maxlength="30" /></td>
	</tr>
	<tr>
		<td>Secret Question</td>
		<td><input type="text" name="account.secretQuestion" maxlength="75" required="required" value="${user.account.secretQuestion}" /></td>
	</tr>
	<tr>
		<td>Secret Answer</td>
		<td><input type="text" name="account.secretAnswer" maxlength="75" required="required" value="${user.account.secretAnswer}" /></td>
	</tr>
</table>
<br/>
<table>
	<tr><td colspan="2"><b>Contact Information</b></td></tr>
	<tr>
		<td>Email Address</td>
		<td><input type="email" name="contactInfo.email" maxlength="50" value="${user.contactInfo.email}" /></td>
	</tr>
	<tr>
		<td>Phone Number</td>
		<td><input type="tel" name="contactInfo.phoneNumber" maxlength="50" value="${user.contactInfo.phoneNumber}" /></td>
	</tr>
</table>