import java.net.*;
import java.io.*;

public class Server
{

	public static void main(String[] args) {
		
		if (args.length != 1)
			System.out.println("Usage: java Server <portNr>");
		else
		{
			ServerSocket socket = null;
			try
			{
				socket = new ServerSocket(Integer.parseInt(args[0]));
				System.out.println("Server running: " + socket + "\nWaiting for client");
			}
			catch(IOException e)
			{
				System.out.println(e);
			}
			try(Socket client = socket.accept())
			{
				BufferedReader input = new BufferedReader(new InputStreamReader(client.getInputStream()));
				PrintWriter output = new PrintWriter(new OutputStreamWriter(client.getOutputStream()), true);
				System.out.println("Client accepted: " + client);
				String line = input.readLine();
				System.out.println("Read: " + line);
				output.println(line);
				input.close();
				output.close();
				socket.close();
			}
			catch(IOException e)
			{
				System.out.println(e);
			}
			
		}

	}

}