import java.net.*;
import java.io.*;

public class Client {

	public static void main(String[] args)
	{
		InetAddress addr;
		try {
			addr = InetAddress.getByName(null);
		} catch (UnknownHostException e) {
			e.printStackTrace();
			return;
		}
		try (Socket socket = new Socket(addr, 6666)) {
			BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			PrintWriter out = new PrintWriter(new OutputStreamWriter(socket.getOutputStream()), true);
			out.println(65);
			System.out.println(in.readLine());
			Thread.sleep(200);
			out.println("END");
		}
		catch(Exception e) {
			System.out.println(e);
		}
	}
	
}
