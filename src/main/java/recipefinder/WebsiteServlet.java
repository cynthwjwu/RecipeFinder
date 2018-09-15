package recipefinder;

import static com.googlecode.objectify.ObjectifyService.ofy;

import java.io.IOException;

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
		
		// get content from the request
		String websiteTitle = req.getParameter("websiteTitle");
		String item = req.getParameter("item");
		
		// create a new greeting
		RecipeMatch recipeMatch = new RecipeMatch(user, item, websiteTitle);
		
		// chuck the greeting into Objectify
		ofy().save().entity(recipeMatch).now();
		
		// send response to ofyguestbook.jsp
		//blog.jsp
		resp.sendRedirect("/home.jsp");
	}
	
	static {
		ObjectifyService.register(RecipeMatch.class);
	}

}
