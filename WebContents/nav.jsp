<%
// Quest
// nav.jsp
// 
// Created by Ian Cordero, Paul Grad, Michael Bonifacio, and Tim Taylor on 23 May 2015
// Copyright (C) 2015 Ian Cordero, Paul Grad, Michael Bonifacio, and Tim Taylor. All rights reserved.
%>
<%!
private class NavLink 
{
	public final String name;
	public final String href;
	public final String persistence;
	
	public NavLink(String name, String href, String persistence) 
	{
		this.name = name;
		this.href = href;
		this.persistence = persistence;	
	}
}
%>
<%
final NavLink[] links = new NavLink[]{
	new NavLink("Home", "", "always"),
	new NavLink("Log In", "login", "loggedOut"),
	new NavLink("Log Out", "logout", "loggedIn"),
	new NavLink("Sign Up", "signup", "loggedOut"),
	new NavLink("Test", "test", "always")
};
%>
<nav>
	<ul>
	<%
	for (int i = 0; i < links.length; i++) { 
		if ((session.getAttribute("uid") != null && links[i].persistence.equals("loggedOut")) || (session.getAttribute("uid") == null && links[i].persistence.equals("loggedIn"))) {
			continue;
		}
	%>
		<li><a href="/Quest/<%= links[i].href %>"><%= links[i].name %></a></li>
	<%
	}
	%>
	</ul>
</nav>