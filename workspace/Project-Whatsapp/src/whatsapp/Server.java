package whatsapp;

import java.net.*;

public class Server implements Runnable {
	
	private ServerSocket server = null;
	private Thread thread = null;
	private ServerThread client = null;
	
	public Server(int portNumber)
	{
		try {
			System.out.println("Binding server to port " + portNumber + "...");
			server = new ServerSocket(portNumber);
			System.out.println("Server started: " + server);
			start();
			
		}
		catch(Exception e)
		{
			System.out.println("Exception: " + e);
		}
	}
	
	public void start()
	{
		if (thread == null)
		{
			thread = new Thread(this);
			thread.start();
		}
	}
	
	public void run()
	{
		while(thread != null)
		{
			try
			{
				System.out.println("Waiting client...");
				addThread(server.accept());
			}
			catch(Exception e)
			{
				System.out.println(e);
			}
		}
	}
	
	public void addThread(Socket socket)
	{
		System.out.println("Client accepted: " + socket);
		client = new ServerThread(this, socket);
		try
		{
			client.open();
			client.start();
		}
		catch(Exception e)
		{
			System.out.println(e);
		}
	}
	
	public void stop()
	{
		try 
		{			
			if (thread != null)
			{
				thread.interrupt();
				thread = null;
			}
		}
		catch(Exception e)
		{
			System.out.println("Error closing: " + e);
		}
	}
	
	public static void main(String[] args)
	{
		Server server = null;
		int portNumber = 2222;
		server = new Server(portNumber);
	}
	
}
