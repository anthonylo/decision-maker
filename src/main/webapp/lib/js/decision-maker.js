/**
 * 
 */


var script = document.createElement('script');
script.src = '/lib/jquery-1.11.0.min.js';
script.type = 'text/javascript';
document.getElementsByTagName('head')[0].appendChild(script);


function registerRedirect() {
	window.location.href="register";
}

function generateJsonOfUser() {
	var id = $("#id").val();
	var firstName = $("#firstName").val();
	var lastName = $("#lastName").val();
	var age = $("#age").val();
	
	var accountId = $("#account-id").val();
	var active = $("#account-active").val();
	var admin = $("#account-admin").val();
	var username = $("#account-username").val();
	var secretQuestion = $("#account-secretQuestion").val();
	var secretAnswer = $("#account-secretAnswer").val();
	var accountJson = '"account" : { "id" : ' + accountId + ', "username" : "' + username 
		+ '", "secretQuestion" : "' + secretQuestion + '", "secretAnswer" : "' + secretAnswer 
		+ '", "active" : ' + active + ', "admin" : ' + admin + '}';
		
	var contactInfoId = $("#contactInfo-id").val();
	var email = $("#contactInfo-email").val();
	var phoneNumber = $("#contactInfo-phoneNumber").val();
	var contactInfoJson = '"contactInfo" : { "id" : ' + contactInfoId + ', "email" : "' + email 
		+ '", "phoneNumber" : "' + phoneNumber + '" }';
		
//    var second = ('{"id":'+ id +',"firstName":"' + firstName + '","lastName":"'
//                    + lastName + '","age":' + age + ','
//                    + '"contactInfo":{"id":'+contactInfoId+',"email":"' + email
//                    + '","phoneNumber":"' + phoneNumber + '"},'
//                    + '"account":{"id":'+accountId+',"username":"' + username 
//                    + '","secretQuestion":"' + secretQuestion
//                    + '","secretAnswer":"' + secretAnswer + '"}}');

	var userJson = ('{ "id" : ' + id + ', "firstName" : "' + firstName 
		+ '", "lastName" : "' + lastName + '", "age" : ' + age + ', ' 
		+ contactInfoJson + ' , ' + accountJson + ' }');
	
	return userJson;
}

function generateQuickUserTable(users) {
	var userTable = "<table>";
	var tableHeaders = "<tr class='viewall-tr'>";
	tableHeaders += "<td><b>User ID</b></td>";
	tableHeaders += "<td><b>Name</b></td>";
	tableHeaders += "<td><b>Username</b></td>";
	tableHeaders += "<td><b>Email</b></td>";
	tableHeaders += "<td><b>Action</b></td>";
	tableHeaders += "</tr>";
	userTable += tableHeaders;
	
	for (var user in users) {
		var userRow = "<tr class='viewall-tr'>";
		userRow += "<td>" + user.id + "</td>";
		userRow += "<td>" + user.firstName + " " + user.lastName + "</td>";
		userRow += "<td>" + user.account.username + "</td>";
		userRow += "<td>" + user.contactInfo.email + "</td>";
		userRow += "<td>Do something</td>";
		userRow += "</tr>";
		userTable += userRow;
	}
	userTable += "</table>";
	return userTable;
}