<table>
	<tr><td colspan="2"><b>Personal Information</b></td></tr>
	<tr>
		<td>First Name</td>
		<td><input type="text" maxlength="50" value="${user.firstName}" readonly /></td>
	</tr> 
	<tr>
		<td>Last Name</td>
		<td><input type="text" name="lastName" maxlength="50" value="${user.lastName}" readonly /></td>
	</tr> 
	<tr>
		<td>Age</td>
		<td><input type="number" name="age" maxlength="4" value="${user.age}" readonly /></td>
	</tr>
</table>
<br/>
<table>
	<tr><td colspan="2"><b>Account Information</b></td></tr>
	<tr>
		<td>Username</td>
		<td><input type="text" name="account.username" maxlength="30" value="${user.account.username}" readonly /></td>
	</tr>
	<tr>
		<td>Secret Question</td>
		<td><input type="text" name="account.secretQuestion" maxlength="75" value="${user.account.secretQuestion}" readonly /></td>
	</tr>
	<tr>
		<td>Secret Answer</td>
		<td><input type="text" name="account.secretAnswer" maxlength="75" value="${user.account.secretAnswer}" readonly /></td>
</table>
<br/>
<table>
	<tr><td colspan="2"><b>Contact Information</b></td></tr>
	<tr>
		<td>Email Address</td>
		<td><input type="email" name="contactInfo.email" maxlength="50" value="${user.account.username}" readonly /></td>
	</tr>
	<tr>
		<td>Phone Number</td>
		<td><input type="tel" name="contactInfo.phoneNumber" maxlength="50" readonly /></td>
	</tr>
</table>
