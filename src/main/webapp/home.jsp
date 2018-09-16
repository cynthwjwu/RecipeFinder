<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.util.List" %>
<%@ page import="com.google.appengine.api.users.User" %>
<%@ page import="com.google.appengine.api.users.UserService" %>
<%@ page import="com.google.appengine.api.users.UserServiceFactory" %>
<%@ page import="com.googlecode.objectify.*" %>
<%@ page import="recipefinder.RecipeMatch" %>
<%@ page import="recipefinder.RecipeResult" %>
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
  <p><a href="http://recipefinder-hackmit.appspot.com/recipematch.jsp" class="button">Find me a recipe</a></p>
<%
    UserService userService = UserServiceFactory.getUserService();
    User user = userService.getCurrentUser();
    if (user != null) {
      pageContext.setAttribute("user", user);
%>
<p>User is ${fn:escapeXml(user)}</p>

<%
    } else {
%>
<p>Please login to continue:</p>
<p><a href="<%= userService.createLoginURL(request.getRequestURI()) %>" class="button">Login</a></p>
<%
    }
	ObjectifyService.register(RecipeMatch.class);
	ObjectifyService.register(RecipeResult.class);
	List<RecipeMatch> recipeMatches = ObjectifyService.ofy().load().type(RecipeMatch.class).list();
	List<RecipeResult> recipeResults = ObjectifyService.ofy().load().type(RecipeResult.class).list();
	
	Collections.sort(recipeResults, Collections.reverseOrder());
	
	if (recipeMatches.isEmpty()) {
		%>
		<p>Website '${fn:escapeXml(websiteTitle)}' has no recipe matches.</p>
		
		<%
	} else {
		
		for (int i = 0; i < 10; i++) {
			RecipeResult recipeResult = recipeResults.get(i);
			%>
			<div class="box">
				<%
				pageContext.setAttribute("recipe_title", recipeResult.getTitle());
				pageContext.setAttribute("recipe_url", recipeResult.getURL());
				pageContext.setAttribute("image_url", recipeResult.getImageURL());
			%>
				<blockquote><a href="${fn:escapeXml(recipe_url)}">${fn:escapeXml(recipe_title)}</a></blockquote>
				<img src="${fn:escapeXml(image_url)}"/>
				
			</div>
			<br>
			<%
		}
		
	}
%>
  <p><a href="<%= userService.createLogoutURL(request.getRequestURI()) %>" class="button">Log out</a></p>
  
  </body>
</html>