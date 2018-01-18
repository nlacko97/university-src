package jsonparser;

import java.util.Map;

public class Main {

	@SuppressWarnings("unchecked")
	public static void main(String[] args) {
		
		try {			
			
			JsonParser json = new JsonParser();
			Map<String, Object> data;
			String raw = json.load("https://query.yahooapis.com/v1/public/yql?q=select%20item.condition%20from%20weather.forecast%20where%20woeid%20in%20(select%20woeid%20from%20geo.places(1)%20where%20text=%22prague%22)&format=json");
			
			data = (Map<String, Object>)json.parse(raw);
			Map<String, Object> query = (Map<String, Object>)data.get("query");
			Map<String, Object> results = (Map<String, Object>)query.get("results");
			Map<String, Object> channel = (Map<String, Object>)results.get("channel");
			Map<String, Object> item = (Map<String, Object>)channel.get("item");
			Map<String, Object> condition = (Map<String, Object>)item.get("condition");
			
			String temperature = (String)condition.get("temp");

			System.out.println(args[0] + ", current temperature: " + temperature);
		}
		catch(Exception e){
			System.out.println(e);
		}
	
	}

}
