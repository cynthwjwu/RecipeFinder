package recipefinder;

import com.google.appengine.api.users.User;
import com.googlecode.objectify.Key;
import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;
import com.googlecode.objectify.annotation.Index;
import com.googlecode.objectify.annotation.Parent;

@Entity
public class RecipeMatch {
	@Parent Key<Website> websiteName;
	@Id Long id;
	@Index User user;
	@Index String item;
	
	private RecipeMatch() {}
	
	public RecipeMatch(User user, String item, String websiteName) {
		this.user = user;
		this.item = item;
		this.websiteName = Key.create(Website.class, websiteName);
	}
	
	public User getUser() {
		return user;
	}
	
	public String getItem() {
		return item;
	}
}
