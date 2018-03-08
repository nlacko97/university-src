package whatsapp;

import java.io.IOException;
import java.net.*;
//import java.io.*;
import java.util.*;

public class Server implements Runnable {

	private ServerThread client = null;
	private ServerSocket server = null;
	private Thread thread = null;
	private List<String> ContactList = null;
	private List<ServerThread> RunningThreads = null;
	
	private void initContactList()
	{
		ContactList = new ArrayList<String>();
		for(int i = 0; i < 10; i++)
		{
			ContactList.add(Integer.toString(i +  1));
		}
	}
	
	public List<String> getContactList()
	{
		return this.ContactList;
	}
	
	public Server(int portNumber)
	{
		initContactList();
		RunningThreads = new ArrayList<>();
		try
		{
			System.out.println("Server binding to port " + portNumber);
			server = new ServerSocket(portNumber);
			System.out.println("Server running: " + server);
			start();
		}
		catch(Exception e)
		{
			System.out.println("Server error: " + e);
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
	
	@Override
	public void run() {
		
		while(thread != null)
		{
			try
			{
				System.out.println("Waiting client");
				connectClient(server.accept());
			}
			catch(Exception e)
			{
				System.out.println("Error connecting client " + e);
			}
		}

	}
	
	public void connectClient(Socket socket)
	{
		System.out.println("Client accepted: " + socket);
		client = new ServerThread(this, socket);
		RunningThreads.add(client);
		try
		{
			client.open();
			client.start();
		}
		catch(Exception e)
		{
			System.out.println("Error starting thread");
		}
		
	}
	
	public boolean validate(String phoneNumber)
	{
		System.out.println("Validating " + phoneNumber);
		for(String nr : ContactList)
		{
			if (nr.equals(phoneNumber))
				return true;
		}
		return false;
	}

	public ServerThread getClient(String phoneNumber)
	{
		for (ServerThread t : RunningThreads)
			if (t.getPhoneNumber().equals(phoneNumber))
				return t;
		return null;
	}
	
	public synchronized void handleMessage(Message msg)
	{
		System.out.println(msg);
		ServerThread sender = getClient(msg.getSender());
		if (!validate(msg.getTarget()))
		{
			sender.send(new Message("server", msg.getSender(), "Invalid correspondece"));
		}
		else
		{
			ServerThread target = getClient(msg.getTarget());
			if (target == null)
			{
				//add to correspondence history :)
			}
			target.send(msg);
		}
	}
	
	public synchronized void removeClient(String phoneNumber)
	{
		ServerThread t = getClient(phoneNumber);
		System.out.println(RunningThreads);
		RunningThreads.remove(t);
		t.interrupt();
		try {
			t.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		System.out.println(RunningThreads);
	}
	
	public static void main(String[] args) 
	{
		if (args.length != 1)
			System.out.println("Usage: java Server <portNumber>");
		else
		{
			Server server = new Server(Integer.parseInt(args[0]));
		}
	}

}
