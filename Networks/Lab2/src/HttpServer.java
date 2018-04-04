// Nagy László, 523, nlim1659

import java.net.*;
import java.util.Date;
import java.io.*;

public class HttpServer {

	public static void main(String[] args) {
		
		try {
			ServerSocket server = new ServerSocket(8080);
			System.out.println("Server running " + server);
			System.out.println("Waiting for clients");
			while(true)
			{
				ServerThread newClient = new ServerThread(server.accept());
				newClient.start();
			}
		}
		catch(IOException ex)
		{
			System.out.println("Error while running web server - " + ex);
		}
		
	}

}
