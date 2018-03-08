package whatsapp;

import java.net.*;
import java.io.*;

public class ServerThread extends Thread {

	private Socket socket = null;
	private Server server = null;
	private int ID = -1;
	private DataInputStream input = null;
	
	public ServerThread(Server server, Socket socket)
	{
		this.server = server;
		this.socket = socket;
		this.ID = socket.getPort();
	}
	
	public void run()
	{
		try {
			BufferedWriter bf = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		
		System.out.println("Server thread " + ID + " running...");
		while(true)
		{
			try {
				System.out.println(input.readUTF());
			} catch (IOException e) {
				//System.out.println(e);
			}
		}
	}
	
	public void open()
	{
		try {
			input = new DataInputStream(new BufferedInputStream(socket.getInputStream()));
		} catch (IOException e) {
			System.out.println(e);
		}
	}
	
	public void close() throws IOException
	{
		if (socket != null)
			socket.close();
		if (input != null)
			input.close();
	}
	
}
