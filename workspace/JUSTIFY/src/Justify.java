import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.Reader;

public class Justify {
	
	public static String justifyLine(String line, int n) {
		if (line.length() == n)
			return line;
		boolean done = false;
		boolean addedSpace = false;
		while(!done) {
			for(int i = 0; i < line.length() && !done; i++) {
				if (Character.isWhitespace(line.charAt(i))) {
					String cpy = line.substring(0, i);
					cpy += " ";
					cpy += line.substring(i, line.length());
					line = cpy;
					addedSpace = true;
					while(Character.isWhitespace(line.charAt(i))) i++;
					if (line.length() == n)
						done = true;
				}
			}
			if (!addedSpace)
				return line;
		}
		
		return line;
	}
	
	public static void main(String args[])
	{	
		Reader reader = new BufferedReader(new InputStreamReader(System.in));
		
		int r;
		
		try {
			
			String num = "";
			while((r = reader.read()) != 10 && r != -1) {
				num += (char)r;
			}
			
			int n = Integer.parseInt(num);
			
			boolean readEnough = false;
			boolean newLine = false;
			int newL = 0;
			String line = "";
			String word = "";
			while(!readEnough)
			{
				line = word;
				if (line.length() > n)
				{
					word = "";
					continue;
				}
 				boolean readEnoughLine = false;
				while(!readEnoughLine) {
					word = "";
					while(Character.isWhitespace((char)(r = reader.read())))
					{
						if (r == 10 || r == -1)
						{
							newLine = true;
							break;
						}
					}
					while(!Character.isWhitespace((char)r) && r != -1) {
						word += (char)r;
						r = reader.read();
					}
					if (r == -1)
						{
							newLine = true;
							readEnough = true;
						}
					if (line.length() + word.length() < n && word.length() > 0)
					{
						if (line.length() > 0)
							line += " " + word;
						else
							line += word;
						continue;
					}
					readEnoughLine = true;
				}
				if (word.length() > n)
				{
					line = justifyLine(line, n);
					System.out.println(line);
					System.out.println(word);
					word = "";
					continue;
				}
				if (newLine)
				{
					newLine = false;
					if (line.length() > 0)
						System.out.println(line);
					if (r != -1 && newL == 0)
					{
						newL++;
						System.out.println("");
					}
					continue;
				}
				if (newL > 0)
					newL = 0;
				line = justifyLine(line, n);
				System.out.println(line);
			}
			
			
			
		}
		catch(Exception e)
		{
			System.out.print("Error");
		}
		
	}
}
