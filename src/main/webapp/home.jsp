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
  <p><a href="http://localhost:8080/recipematch.jsp" class="button">Find me a recipe</a></p>
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
	List<RecipeMatch> recipeMatches = ObjectifyService.ofy().load().type(RecipeMatch.class).list();
	
	if (recipeMatches.isEmpty()) {
		%>
		<p>Website '${fn:escapeXml(websiteTitle)}' has no recipe matches.</p>
		
		<%
	} else {
		
		for (RecipeMatch recipeMatch : recipeMatches) {
			%>
			<div class="box">
				<%
				if (recipeMatch.getItemList() != null) {
					pageContext.setAttribute("num_matches", recipeMatch.getNumMatches());
					pageContext.setAttribute("results", recipeMatch.getResults());
					for (String item : recipeMatch.getItemList()) {
						pageContext.setAttribute("req_item", item);
						%>
						<blockquote>${fn:escapeXml(req_item)}</blockquote>
					<%
					}
					%>
					<blockquote>${fn:escapeXml(num_matches)}</blockquote>
					<blockquote>${fn:escapeXml(results)}</blockquote>
				<%
				} else {
					pageContext.setAttribute("req_item", "None");
					%>
					<blockquote>${fn:escapeXml(req_item)}</blockquote>
					<%
				}
				%>
				
			</div>
			<br>
			<%
		}
		
	}
%>
  <p><a href="<%= userService.createLogoutURL(request.getRequestURI()) %>" class="button">Log out</a></p>
  
  </body>
</html>