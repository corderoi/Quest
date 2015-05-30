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
<% 
final String errorTitle = (String) request.getAttribute("errorTitle"); 
final String errorMessage = (String) request.getAttribute("errorMessage");
%>
<title><% if (errorTitle != null) { %><%= errorTitle %><% } else { %>Error Page<% } %></title>
<% /* jQuery */ %>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.2/jquery.min.js"></script>
<% /* Bootstrap */ %>
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.2/css/bootstrap.min.css">
<script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.2/js/bootstrap.min.js"></script>
</head>
<body>
<jsp:include page="nav.jsp" />
<section>
	<jsp:include page="notif.jsp" />
	<header>
		<h1>Error</h1>
	</header>
	<p><% if (errorMessage != null) { %><%= errorMessage %><% } else { %>An unknown error occurred.<% } %></p>
</section>
<jsp:include page="footer.jsp" />
</body>
</html>
