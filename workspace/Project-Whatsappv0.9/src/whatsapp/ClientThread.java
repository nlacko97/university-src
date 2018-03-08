package whatsapp;

import java.io.*;
import java.net.*;
import java.util.ArrayList;
import java.util.List;

public class ClientThread extends Thread {

	private Socket socket = null;
	private Client client = null;
	private ObjectInputStream input = null;
	
	private String phoneNumber = "-1";
	
	public ClientThread(Socket socket, Client client, String phoneNumber)
	{
		this.socket = socket;
		this.client = client;
		this.phoneNumber = phoneNumber;
		open();
		start();
	}
	
	public void open()
	{
		try
		{
			input = new ObjectInputStream(socket.getInputStream());
		}
		catch(Exception e)
		{
			System.out.println("Error opening input channel: " + e);
			client.stop();
		}
	}
	
	public void close()
	{
		try
		{
			if (input != null)
				input.close();
		}
		catch(Exception e)
		{
			System.out.println("Error closing channel: " + e);
		}
	}
	
	public void run()
	{
		try
		{
			client.setContacts( (List<String>) input.readObject());
			System.out.println(client.contacts);
		}
		catch(Exception e)
		{
			System.out.println("Error getting conttacts");
		}
		while(true)
		{
			try
			{
				client.handleMessage((Message) input.readObject());
			}
			catch(Exception e)
			{
				System.out.println("Error with message: " + e);
				client.stop();
			}
		}
	}
	
}
