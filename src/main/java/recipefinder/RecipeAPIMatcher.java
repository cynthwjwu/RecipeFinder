package recipefinder;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class RecipeAPIMatcher {
	public RecipeAPIMatcher() {}
	
	/*
	 * Do the actual matching between ingredients and recipes
	 */
	public String getRecipeMatches(List<String> itemList) {
		List<String> matches = new ArrayList<>();
		String reqUrl = getURL(itemList);
		String jsonResp = getHTTPData(reqUrl); //json response returned by api call
		
		//parse response
		//add recipe name + links + etc to list
		

		return jsonResp;
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

