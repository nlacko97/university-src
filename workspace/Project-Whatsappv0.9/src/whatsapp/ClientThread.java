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
	
	public void doSleep(int sec) throws InterruptedException
	{
		Thread.sleep(sec);
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
				if (!client.closed())
				{
					Object rec = input.readObject();
//					if (rec instanceof Correspondence)
//					{
//						Correspondence c = (Correspondence)rec;
//						System.out.println("Setting correspondence to " + c.participants + " wit history: " + c.history);
//						client.setCorrespondence((Correspondence) rec);
//					}
//					else if (rec instanceof Message)
					{
						client.handleMessage((Message)rec);
					}
					
				}
			}
			catch(Exception e)
			{
				//System.out.println("Error with message: " + e);
				//client.stop();
			}
		}
	}
	
}
