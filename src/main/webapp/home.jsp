<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.util.List" %>
<%@ page import="com.google.appengine.api.users.User" %>
<%@ page import="com.google.appengine.api.users.UserService" %>
<%@ page import="com.google.appengine.api.users.UserServiceFactory" %>
<%@ page import="com.googlecode.objectify.*" %>
<%@ page import="recipefinder.RecipeMatch" %>
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
    UserService userService = UserServiceFactory.getUserService();
    User user = userService.getCurrentUser();
    if (user != null) {
      pageContext.setAttribute("user", user);
%>
<p>User is null</p>

<%
    } else {
%>

<p><a href="<%= userService.createLoginURL(request.getRequestURI()) %>" class="button">Login</a></p>
<%
    }
	ObjectifyService.register(RecipeMatch.class);
	List<RecipeMatch> recipeMatches = ObjectifyService.ofy().load().type(RecipeMatch.class).list();
	
	if (recipeMatches.isEmpty()) {
		%>
		<p><a href="http://localhost:8080/recipematch.jsp" class="button">Find me a recipe</a></p>
		<p>Website '${fn:escapeXml(websiteTitle)}' has no recipe matches.</p>
		
		<%
	} else {
		
		for (int i = 0; i < 5; i++) {
			RecipeMatch recipeMatch = recipeMatches.get(i);
			pageContext.setAttribute("req_user", recipeMatch.getUser());
			pageContext.setAttribute("req_item", recipeMatch.getItem());
			%>
			<div class="box">
				<p>${fn:escapeXml(req_user)}</p>
				<p>${fn:escapeXml(req_item)}</p>
			</div>
			<br>
			<%
		}
		
	}
%>
  <p><a href="<%= userService.createLogoutURL(request.getRequestURI()) %>" class="button">Log out</a></p>
  
  
  </body>
</html>