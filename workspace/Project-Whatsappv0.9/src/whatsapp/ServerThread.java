package whatsapp;

import java.net.*;
import java.util.*;
import java.io.*;

public class ServerThread extends Thread {

	private Server server = null;
	private Socket socket = null;
	private ObjectInputStream input = null;
	private ObjectInputStream input2 = null;
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
		output = new ObjectOutputStream(socket.getOutputStream());
		input = new ObjectInputStream(socket.getInputStream());
//		input2 = new ObjectInputStream(socket.getInputStream());
	}
	
	public void close() throws IOException
	{
		if (socket != null)
		{
			socket.shutdownInput();			
			socket.close();
		}
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
			try {
				output.flush();
			} catch (IOException e1) {
				e1.printStackTrace();
			}
			int errors = 0;
//			Correspondence corr = null;
			while(true)
			{
				try
				{
//					System.out.println("\t" + msg);
//					input.reset();
//					if (socket != null)
					msg = (Message) input.readObject();
					System.out.println("Client msg: " + msg);
					if (msg.getMessage().equals(".exit"))
					{
						System.out.println("Client " + phoneNumber + " exiting...");
						server.removeClient(phoneNumber);
						this.interrupt();
						break;
					}
					else
//					if (msg.getSender().equals("-1") && msg.getTarget().equals("-1"))
//					{
//						List<Correspondence> history = server.getHistory();
//						ArrayList<String> targets = (ArrayList<String>) input.readObject();
//						
//						System.out.println("targets: " + targets + "\n" + history);
//						boolean found = false;
//						for(Correspondence c : history)
//						{
//							found = true;
//							System.out.println("\tLooking at " + c);
//							for(String s : c.participants)
//							{
//								if (!targets.contains(s))
//								{
//									found = false;
//									break;
//								}
//							}
//							if (found)
//							{
//								output.writeObject((Correspondence)c);
//								corr = c;
//								System.out.println("Sent known corr -- " + c);
//							}
//						}
//						if (!found)
//						{
//							history.add(new Correspondence(targets));
//							server.setHistory(history);
//							System.out.println("\t" + history);
//							Correspondence c = history.get(history.size() - 1);
//							output.writeObject((Correspondence)c);
//							corr = c;
//							System.out.println("sent new correspondence -- " + c.participants);
//						}
//						System.out.println(found);
////						targets.clear();
//					}
//					else
						server.handleMessage(msg);
				}
				catch(Exception e)
				{
					if (errors != 0)
					{
						e.printStackTrace();
						System.out.println("Error occurred while sending message, shutting down connection." + e + "\t MSG: \n" + msg);
						server.removeClient(phoneNumber);
						this.interrupt();
						break;
					}
					errors++;
						
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
