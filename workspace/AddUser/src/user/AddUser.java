package user;

import java.io.*;

public class AddUser {
	
	
	public static void init() throws IOException
	{
		BufferedReader in = new BufferedReader(new FileReader("./src/user/users"));
		String line;
		while((line = in.readLine()) != null)
		{
			System.out.println(line);
		}
	}
	
	public static void adduser()
	{
		BufferedReader c = new BufferedReader(new InputStreamReader(System.in));
	}
	
	public static void main(String args[])
	{
		try {
			init();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
