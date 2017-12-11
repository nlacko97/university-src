import java.io.*;
import java.net.*;

public class Server extends Thread {

	private Socket socket;
	private OutputStream out;
	
	public Server(Socket s)
	{
		socket = s;
		
		start();
	}
	
	public void run()
	{
		while(true)
		{
			
			
		}
	}
	
	public static void main(String[] args)
	{
		try (ServerSocket s = new ServerSocket(6666))
		{
			System.out.println("server starting..");
			try (Socket socket = s.accept())
			{
				System.out.println("Accepting connections");
				BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
				PrintWriter out = new PrintWriter(new OutputStreamWriter(socket.getOutputStream()), true);
				
				while(true)
				{
					String str = in.readLine();
					System.out.print("Received: " + str);
					if (str.equals("END")) break;
					out.println(str);
				}
				System.out.println("Ending connection");
			}
		}
		catch(Exception e)
		{
			System.out.println(e);
		}
	}
	
}
