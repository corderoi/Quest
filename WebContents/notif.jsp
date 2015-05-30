<%
// Quest
// notif.jsp
// 
// Created by Ian Cordero, Paul Grad, Michael Bonifacio, and Tim Taylor on 23 May 2015
// Copyright (C) 2015 Ian Cordero, Paul Grad, Michael Bonifacio, and Tim Taylor. All rights reserved.
%>
<noscript>
<div class="notif">
	<p>Please enable JavaScript to get the best experience out of Quest.</p>
</div>
</noscript>
<%
final String notifText = (String) session.getAttribute("notifText"); 
final String notifColor = (String) session.getAttribute("notifColor");

if (notifText != null) {
	String notifColorClass = "notifRed";
	if (notifColor != null) {
		if (notifColor.equals("green")) {
			notifColorClass = "notifGreen";
		} else if (notifColor.equals("yellow")) {
			notifColorClass = "notifYellow";
		} else if (notifColor.equals("silver")) {
			notifColorClass = "notifSilver";
		}
		session.removeAttribute("notifColor");
	}
%>
	<div class="notif <%= notifColorClass %>">
		<p><%= notifText %></p>
	</div>
<%
	session.removeAttribute("notifText");
}
%>
