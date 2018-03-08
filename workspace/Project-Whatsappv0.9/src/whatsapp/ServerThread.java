package whatsapp;

import java.net.*;
import java.util.*;
import java.io.*;

public class ServerThread extends Thread {

	private Server server = null;
	private Socket socket = null;
	private ObjectInputStream input = null;
	private ObjectOutputStream output = null;
	
	private String phoneNumber = "-1";
	
	public String getPhoneNumber() { return this.phoneNumber; }
	
	public ServerThread(Server server, Socket socket)
	{
		this.server = server;
		this.socket = socket;
	}
	
	public void open() throws IOException
	{
		input = new ObjectInputStream(socket.getInputStream());
		output = new ObjectOutputStream(socket.getOutputStream());
	}
	
	public void close() throws IOException
	{
		socket.shutdownInput();
		if (socket != null)
			socket.close();
		if (input != null)
			input.close();
		if (output != null)
			output.close();
	}
	
	public void send(Message msg)
	{
		System.out.println(msg);
		try
		{
			output.writeObject(msg);
			output.flush();
		}
		catch (IOException e)
		{
			System.out.println("Error sending message " + e);
			server.removeClient(phoneNumber);
			interrupt();
		}
	}
	
	public void run()
	{
		try {
			phoneNumber = (String) input.readObject();
			System.out.println("Server got phone number: " + phoneNumber);
		} catch (ClassNotFoundException e) {
			System.out.println(e);
		} catch (IOException e) {
			//System.out.println(e);
			e.printStackTrace();
		}
		System.out.println("Server thread for" + phoneNumber +" running at " + socket.getPort());
		
		if (server.validate(phoneNumber))
		{
			Message msg = null;
			try {
				output.writeObject(server.getContactList());
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			while(true)
			{
				try
				{
					msg = (Message) input.readObject();
					System.out.println("Client msg: " + msg);
					server.handleMessage(msg);
				}
				catch(Exception e)
				{
					System.out.println("Error occurred while sending message, shutting down connection. MSG: \n" + msg);
					server.removeClient(phoneNumber);
					this.interrupt();
					break;
				}
			}
		}
		else
		{
			System.out.println("Invalid client login information");
			server.removeClient(phoneNumber);
			this.interrupt();
		}
	}
	
}
