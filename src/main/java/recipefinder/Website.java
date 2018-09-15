package recipefinder;

import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;

@Entity
public class Website {
	@Id long id;
	String name;
	
	public Website(String name) {
		this.name = name;
	}
}
