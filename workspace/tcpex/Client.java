import java.net.*;
import java.io.*;

public class Client
{

	public static void main(String[] args) {
		
		if (args.length != 2)
			System.out.println("Usage: java Client <hostName> <hostPort>");
		else
		{
			try
			{
				Socket socket = new Socket(args[0], Integer.parseInt(args[1]));
				BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
				BufferedReader inS = new BufferedReader(new InputStreamReader(socket.getInputStream()));
				PrintWriter out = new PrintWriter(new OutputStreamWriter(socket.getOutputStream()), true);
				String line = in.readLine();
				out.println(line);
				String str = inS.readLine();
				socket.close();
				in.close();
				out.close();
			}
			catch(Exception e)
			{
				System.out.println(e);
			}
		}

	}

}