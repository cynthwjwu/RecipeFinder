package recipefinder;

import static com.googlecode.objectify.ObjectifyService.ofy;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.appengine.api.users.User;
import com.google.appengine.api.users.UserService;
import com.google.appengine.api.users.UserServiceFactory;
import com.googlecode.objectify.ObjectifyService;

public class WebsiteServlet extends HttpServlet {
	public void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
		
		// get user from UserService
		UserService userService = UserServiceFactory.getUserService();
		User user = userService.getCurrentUser();
		
		// get item list from the request
		String websiteTitle = req.getParameter("websiteTitle");
		String item = req.getParameter("item");
		List<String> itemList = Arrays.asList(item.split(","));
		
		// get recipe match
		RecipeMatch recipeMatch = new RecipeMatch(user, itemList, websiteTitle);
		recipeMatch.calcNumMatches();
		recipeMatch.determineMatches();
		
		// chuck the recipe request into Objectify
		ofy().save().entity(recipeMatch).now();
		
		// send response to ofyguestbook.jsp
		//blog.jsp
		resp.sendRedirect("/home.jsp");
	}
	
	static {
		ObjectifyService.register(RecipeMatch.class);
	}

}
