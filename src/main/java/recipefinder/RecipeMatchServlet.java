package recipefinder;

import java.io.IOException;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.googlecode.objectify.ObjectifyService;

public class RecipeMatchServlet extends HttpServlet {
	public void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
		
		// send response to ofyguestbook.jsp
		resp.sendRedirect("/recipematch.jsp");
	}
	
	static {
		ObjectifyService.register(RecipeMatch.class);
	}
}
