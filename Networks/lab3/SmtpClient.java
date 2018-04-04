import java.net.*;
import java.io.*;

class SmtpClient {

	private Socket socket = null;


	SmtpClient()
	{
		try {
			socket = new Socket("smtp.gmail.com", 465);
		}
		catch(Exception e) {
			System.out.println("Connection error: " + e);
		}
	}



	public static void main(String[] args) {
		
		SmtpClient smtp = new SmtpClient();

	}

}