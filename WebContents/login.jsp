<%
// Quest
// login.jsp
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
<title>Log In</title>
<% /* jQuery */ %>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.2/jquery.min.js"></script>
<% /* Bootstrap */ %>
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.2/css/bootstrap.min.css">
<script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.2/js/bootstrap.min.js"></script>
<link rel="stylesheet" href="style.css" />
</head>
<body>
<jsp:include page="nav.jsp" />
<section>
	<jsp:include page="notif.jsp" />
	<header>
		<h1>Log In</h1>
	</header>
	<form method="post" action="login">
		<p><input type="text" name="uid" placeholder="Email" /></p>
		<p><input type="password" name="password" placeholder="Password" /></p>
		<input type="submit" value="Log In">
	</form>
</section>
<jsp:include page="footer.jsp" />
</body>
</html>
