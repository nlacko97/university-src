package whatsapp;

import java.net.*;
import java.io.*;

public class Client {

	private Socket socket = null;
	private BufferedReader console = null;
	private DataOutputStream stream = null;
	
	public Client(String serverName, int serverPort)
	{
		System.out.println("Starting connection...");
		try
		{
			socket = new Socket(serverName, serverPort);
			System.out.println("Connection established: " + socket);
			console = new BufferedReader(new InputStreamReader(System.in));
			stream = new DataOutputStream(socket.getOutputStream());
		}
		catch(UnknownHostException e)
		{
			System.out.println("Host unknown: " + e.getMessage());
		}
		catch(Exception e)
		{
			System.out.println(e);
		}
		String line = "";
		while(!line.equals("exit"))
		{
			try
			{
				line = console.readLine();
				stream.writeUTF(line);
				stream.flush();
			}
			catch(Exception e)
			{
				System.out.println(e);
			}
		}
		try
		{
			if (console != null)
				console.close();
			if (stream != null)
				stream.close();
			if (socket != null)
				socket.close();	
		}
		catch(Exception e)
		{
			System.out.println(e);
		}
	}
	
	
	
	public static void main(String[] args) {
		Client client = null;
		if (args.length != 2)
			System.out.println("Usage: java Client hostName hostPort");
		else
			client = new Client(args[0], Integer.parseInt(args[1]));
	}

}
