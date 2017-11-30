import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.Reader;

public class Justify {
	
	public static String justifyLine(String line, int n) {
		if (line.length() == n)
			return line;
		boolean done = false;
		
		while(!done) {
			for(int i = 0; i < line.length() && !done; i++) {
				if (Character.isWhitespace(line.charAt(i))) {
					String cpy = line.substring(0, i);
					cpy += " ";
					cpy += line.substring(i, line.length());
					line = cpy;
					i++;
					if (line.length() == n)
						done = true;
				}
			}
		}
		
		return line;
	}
	
	public static void main(String args[])
	{
		System.out.println("Reading input:");
		
		Reader reader = new BufferedReader(new InputStreamReader(System.in));
		
		int r;
		
		try {
			
			String num = "";
			while((r = reader.read()) != 10) {
				num += (char)r;
			}
			
			int n = Integer.parseInt(num);
			
			String line = "";
			System.out.println(line.length());
			int position;
			boolean readEnough = false;
			while(!readEnough) {
				String word = "";
				while(Character.isWhitespace((char)(r = reader.read())));
				while(!Character.isWhitespace((char)r)) {
					//System.out.println((char)r);
					word += (char)r;
					r = reader.read();
				}
				if (line.length() + word.length() <= 50 && word.length() > 0)
				{
					if (line.length() > 0)
						line += " " + word;
					else
						line += word;
					continue;
				}
				readEnough = true;
			}
			line = justifyLine(line, n);
			System.out.println(line);
			
			
			
		}
		catch(Exception e)
		{
			System.out.print("Error");
		}
		
	}
}
