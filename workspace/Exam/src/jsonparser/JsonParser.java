package jsonparser;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.net.URL;

public class JsonParser implements IJson {

	@Override
	public String load(String url) throws MalformedURLException, IOException {
		
		URL myUrl = new URL(url);
		String input = "";
		try (BufferedReader con = new BufferedReader(new InputStreamReader(myUrl.openStream())))
		{
			String line;
			while((line = con.readLine()) != null)
				input += line;
		}
		catch(Exception e)
		{
			throw new IOException();
		}
		return input;
	}

	@Override
	public Object parse(String data) throws FormatException {
		
		ArrayList<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		int listIndex = 0;
		Map<String, Object> mainmap = (Map<String, Object>) new HashMap<String, Object>();
		list.add(mainmap);
		listIndex++;
		Map<String, Object> currentMap = mainmap; 
		data = data.substring(1, data.length() - 1);
		
		for(int i = 1; i < data.length(); i++)
		{
			String key = "";
			while(i < data.length() && data.charAt(i) != '"') {
				if (data.charAt(i) == '}')
				{
					listIndex--;
					currentMap = list.get(listIndex);
				}
				else
					key += data.charAt(i);
				i++;
			}
			if (i == data.length()) break;
			i += 2;
			switch(data.charAt(i))
			{
			case '"':
				i++;
				String val = "";
				while(data.charAt(i) != '"') {
					val += data.charAt(i);
					i++;
				}
				currentMap.put(key, val);
				i+=2;
				break;
			case '{':
				Map<String, Object> newMap = (Map<String, Object>) new HashMap<String, Object>();
				currentMap.put(key, newMap);
				currentMap = newMap;
				list.add(newMap);
				listIndex++;
				i += 1;
				break;
			default:
				if (data.charAt(i) < '0' || data.charAt(i) > '9')
					throw new FormatException();
				try {
					String valueS = "";
					while(data.charAt(i) != ',' && data.charAt(i) != '}') {
						valueS += data.charAt(i);
						i++;
					}
					i++;
					double value = Double.parseDouble(valueS);
					currentMap.put(key,  value);
				}
				catch(Exception e) {
					System.out.println(e);
					throw new FormatException();
				}
			}
		}
		
		return mainmap;
		
	}
}
