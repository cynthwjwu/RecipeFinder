package recipefinder;

import java.util.ArrayList;
import java.util.List;

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
	@Index List<String> itemList;
	@Index Integer numMatches;
	
	private RecipeMatch() {}
	
	public RecipeMatch(User user, List<String> itemList, String websiteName) {
		this.user = user;
		this.itemList = itemList;
		this.websiteName = Key.create(Website.class, websiteName);
		this.numMatches = 0;
	}
	
	public User getUser() {
		return user;
	}
	
	public List<String> getItemList() {
		return itemList;
	}
	
	public void calcNumMatches() {
		for (String ingredient : getItemList()) {
			if (ingredient.equalsIgnoreCase("apple")) {
				this.numMatches++;
			}
		}
	}
	
	public Integer getNumMatches() {
		return numMatches;
	}

}


