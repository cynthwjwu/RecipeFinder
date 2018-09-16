package recipefinder;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

public class RecipeAPIMatcher {
	RecipeMatch recipeMatch;
	
	public RecipeAPIMatcher(RecipeMatch recipeMatch) {
		this.recipeMatch = recipeMatch;
	}
	
	/*
	 * Do the actual matching between ingredients and recipes
	 */
	public List<RecipeResult> getRecipeMatches() {
		List<RecipeResult> results = new ArrayList<>();
		String reqUrl = getURL(recipeMatch.getItemList());
		String jsonResp = getHTTPData(reqUrl); //json response returned by api call
		
		JsonParser parser = new JsonParser();
		try {
			JsonElement jsonTree = parser.parse(jsonResp);
			JsonObject obj = jsonTree.getAsJsonObject();
			JsonArray recipeArray = (JsonArray) obj.get("recipes");
			for (int i = 0; i < 10; i++) {
				JsonElement recipeArrayObj = recipeArray.get(i);
				JsonObject recipes = recipeArrayObj.getAsJsonObject();
				JsonElement recipeTitle = recipes.get("title");
				JsonElement recipeURL = recipes.get("source_url");
				JsonElement imageURL = recipes.get("image_url");
				results.add(new RecipeResult(recipeTitle.getAsString(), recipeURL.getAsString(), imageURL.getAsString()));
			}
		} catch(Exception e) {
			
		}
		
		return results;
	}
	
	
	/*
	 * Create URL based on input ingredients
	 */
	private String getURL(List<String> itemList) {
		StringBuilder reqURL = new StringBuilder();
		String baseURL = "https://www.food2fork.com/api/search?key=bc8f87e7d49f11effe53009893fb319a&q=";
		reqURL.append(baseURL);
		for (String item : itemList) {
			reqURL.append(item);
		}
		for (int i = 0; i < itemList.size(); i++) {
			reqURL.append(itemList.get(i));
			if (i != itemList.size() - 1) {
				reqURL.append(",");
			}
		}
		return reqURL.toString();
	}
	
	/*
	 * Generic method to make HTTP get request
	 */
	private String getHTTPData(String reqURL) {
		StringBuffer json = new StringBuffer();
		try {
			URL url = new URL(reqURL);
			BufferedReader reader = new BufferedReader(new InputStreamReader(url.openStream()));
			String line;

			while ((line = reader.readLine()) != null) {
			  json.append(line);
			}
			reader.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return json.toString();
	}
}

