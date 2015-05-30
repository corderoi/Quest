<%
// Quest
// index.jsp
// 
// Created by Ian Cordero, Paul Grad, Michael Bonifacio, and Tim Taylor on 23 May 2015
// Copyright (C) 2015 Ian Cordero, Paul Grad, Michael Bonifacio, and Tim Taylor. All rights reserved.
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1">
<title>Quest</title>
<% /* jQuery */ %>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.2/jquery.min.js"></script>
<% /* Bootstrap */ %>
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.2/css/bootstrap.min.css">
<script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.2/js/bootstrap.min.js"></script>
<link rel="stylesheet" href="style.css" />
</head>
<%
String uid = (String) session.getAttribute("uid");
%>
<body>
<jsp:include page="nav.jsp" />
<section>
	<jsp:include page="notif.jsp" />
	<header>
		<h1>Quest</h1>
	</header>
	<%
	if (session.getAttribute("uid") == null) { 
	%>
	<!-- Sample Data -->
	<p>Welcome, Ian!</p>
	<form method="post" action="quests">
		Add Quest: <input type="text" placeholder="My Quest" />
		<input type="submit" value="Create" />
	</form>
	<div class="quest clickable">Buy Groceries</div>
	<div class="quest clickable">Do Homework</div>
	<div class="quest clickable">Finish Group Assignment</div>
	<div class="quest clickable">Walk Dog</div>
	<script>
	$(document).ready(function() {
		$('.clickable').click(function() { alert("Registered a click!"); }).mouseenter(function() { $(this).css({ 'background-color': 'grey' }); }).mouseleave(function() { $(this).css({ 'background-color': 'white' }); });
	});
	</script>
	<%
	} else {
	%>
	<p>Hello, <span id="username"></span>. Your first name is <span id="firstName"></span> Your last name is <span id="lastName"></span>.</p>
	<script>
	$(document).ready(function() {
		$.getJSON('users/<%= uid %>', function(user) {
			if (user != null) {
				if (user.firstName != null && user.firstName != "") {
					$('#username').text(user.firstName);
				} else {
					$('#username').text("<%= uid %>");
				}
				$('#firstName').text(user.firstName);
				$('#lastName').text(user.lastName);
			}
		});	
	});
	</script>
	<% 
	}
	%>
</section>
<jsp:include page="footer.jsp" />
</body>
</html>
