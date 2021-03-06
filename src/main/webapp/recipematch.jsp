<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.util.List" %>
<%@ page import="com.google.appengine.api.users.User" %>
<%@ page import="com.google.appengine.api.users.UserService" %>
<%@ page import="com.google.appengine.api.users.UserServiceFactory" %>
<%@ page import="com.googlecode.objectify.*" %>
<%@ page import="java.util.Collections" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<html>
  <header>
  	<link type="text/css" rel="stylesheet" href="/stylesheets/main.css"/>
	<div class="banner">
		<div class="websiteTitle" style="font-family:Helvetica; font-size: 30px"><b>Recipe Finder</b></div>
		<div class="authors" style="font-family:Verdana; font-size:15px">Power Trio</div>
	</div>
  </header>
  
  <body>
<%
    String websiteTitle = request.getParameter("websiteTitle");
	if (websiteTitle == null) {
		websiteTitle = "default";
	}
	
	pageContext.setAttribute("websiteTitle", websiteTitle);
	UserService userService = UserServiceFactory.getUserService();
    User user = userService.getCurrentUser();
    
    if (user != null) {
      pageContext.setAttribute("user", user);
%>

<form action="/website/home" method="post">
	<p>Input items, separate by a comma</p>
	<div><textarea name="item" rows="3" cols="60"></textarea></div>
	<br>
	<div><input type="submit" value="Match Me" class="button"/></div>
	<input type="hidden" name="websiteTitle" value="${fn:escapeXml(websiteTitle)}"/>
</form>

<p><a href="<%= userService.createLogoutURL(request.getRequestURI()) %>" class="button">Log out</a></p>

<%
    } else {
%>

<p><a href="<%= userService.createLoginURL(request.getRequestURI()) %>" class="button">Login</a></p>

<%
    }
%>

  </body>
</html>