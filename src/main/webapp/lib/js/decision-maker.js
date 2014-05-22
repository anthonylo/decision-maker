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

function isStringEmpty(str) {
	return (!str || 0 === str.length);
}

function generateJsonOfUser() {
	// User
	var id = $("#id").val();
	var firstName = $("#firstName").val();
	var lastName = $("#lastName").val();
	var age = $("#age").val();
	
	// Account Information
	var accountId = $("#account-id").val();
	var active = $("#account-active").val();
	var admin = $("#account-admin").val();
	var username = $("#account-username").val();
	var secretQuestion = $("#account-secretQuestion").val();
	var secretAnswer = $("#account-secretAnswer").val();
	var accountJson = '"account" : { "id" : ' + accountId + ', "username" : "' + username 
		+ '", "secretQuestion" : "' + secretQuestion + '", "secretAnswer" : "' + secretAnswer 
		+ '", "active" : ' + active + ', "admin" : ' + admin + '}';
		
	// Contact Information
	var contactInfoId = $("#contactInfo-id").val();
	var email = $("#contactInfo-email").val();
	var phoneNumber = $("#contactInfo-phoneNumber").val();
	var contactInfoJson = '"contactInfo" : { "id" : ' + contactInfoId + ', "email" : "' + email 
		+ '", "phoneNumber" : "' + phoneNumber + '" }';

	// Full JSON
	var userJson = ('{ "id" : ' + id + ', "firstName" : "' + firstName 
		+ '", "lastName" : "' + lastName + '", "age" : ' + age + ', ' 
		+ contactInfoJson + ' , ' + accountJson + ' }');
	
	return userJson;
}

function generateQuickUserTable(users) {
	var userTable = "<table class='dm-table' id='fillMe'>";
	var tableHeaders = "<tr class='dm-tr'>";
	tableHeaders += "<td><b>User ID</b></td>";
	tableHeaders += "<td><b>Name</b></td>";
	tableHeaders += "<td><b>Username</b></td>";
	tableHeaders += "<td><b>Email</b></td>";
	tableHeaders += "<td><b>Action</b></td>";
	tableHeaders += "</tr>";
	userTable += tableHeaders;
	
	for (var k in users) {
		
		var username = users[k].account.username;
		var id = users[k].id;
		
		var userRow = "<tr class='dm-tr'>";
		userRow += "<td>" + users[k].id + "</td>";
		userRow += "<td>" + users[k].firstName + " " + users[k].lastName + "</td>";
		userRow += "<td>" + users[k].account.username + "</td>";
		userRow += "<td>" + users[k].contactInfo.email + "</td>";
		userRow += ("<td><a href='javascript:void(0);' class='testbuttonthree' onclick='request_friend(" + id + ", \"" + username + "\")'>Send Request</a></td>");
		userRow += "</tr>";
		userTable += userRow;
	}
	userTable += "</table>";
	return userTable;
}

$(document).ready(function() {
	// Friend Request Events
	$(".acceptreq").click(function() {
		var friendUsername = $(this).closest("tr").find("td:nth-child(1)").text().trim();
		console.log("here");
		
		$.ajax({
			type : "post",
			url : "/decision-maker/friends/request/accept",
			data : "friendUsername="+friendUsername,
			success : function(response) {
				location.reload();
			}, error : function(xhr) {
				console.log(xhr);
				console.log("FAIL");
			}
		});
	});
	$(".rejectreq").click(function() {
		var friendUsername = $(this).closest("tr").find("td:nth-child(1)").text().trim();
		$.ajax({
			type : "post",
			url : "/decision-maker/friends/request/reject",
			data : "friendUsername="+friendUsername,
			success : function(response) {
				location.reload();
			}, error : function(xhr) {
				console.log(xhr);
				console.log("FAIL");
			}
		});
	});
	$(".cancelreq").click(function() {
		var friendUsername = $(this).closest("tr").find("td:nth-child(1)").text().trim();
		$.ajax({
			type : "post",
			url : "/decision-maker/friends/request/cancel",
			data : "friendUsername="+friendUsername,
			success : function(response) {
				location.reload();
			}, error : function(xhr) {
				console.log(xhr);
				console.log("FAIL");
			}
		});
	});
});