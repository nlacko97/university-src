package jsonparser;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.Map;

public interface IJson {

	public String load(String url) throws MalformedURLException, IOException;
	
	public Object parse(String data) throws FormatException;
	
}
