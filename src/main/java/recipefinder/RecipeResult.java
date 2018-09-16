package recipefinder;

import java.util.Date;

import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;
import com.googlecode.objectify.annotation.Index;

@Entity
public class RecipeResult implements Comparable<RecipeResult>{
	@Id Long id;
	@Index String title;
	@Index String url;
	@Index String imageURL;
	@Index Date date;
	
	private RecipeResult() {}
	
	public RecipeResult(String title, String url, String imageURL) {
		this.title = title;
		this.url = url;
		this.imageURL = imageURL;
		date = new Date();
	}
	
	public String getTitle()
	{
		return title;
	}
	
	public String getURL() {
		return url;
	}
	
	public String getImageURL() {
		return imageURL;
	}
	
	public Date getDate() {
		return date;
	}
	
	@Override
	public int compareTo(RecipeResult other) {
		if (date.after(other.date)) {
            return 1;
        } 
        else if (date.before(other.date)) {
            return -1;
        }
        return 0;
	}
}

