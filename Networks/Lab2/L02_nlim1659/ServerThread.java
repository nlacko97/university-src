// Nagy László, 523, nlim1659


import java.net.*;
import java.io.*;

public class ServerThread extends Thread {

	private Socket client = null;
	private BufferedReader input = null;
	private DataOutputStream output = null;
	public boolean keepAlive = false;
	
	public ServerThread(Socket client_) throws IOException
	{
		client = client_;
		client.setKeepAlive(true);
		input = new BufferedReader(new InputStreamReader(client.getInputStream()));
		output = new DataOutputStream(client.getOutputStream());
	}
	
	private void close()
	{
		try
		{
			input.close();
			output.close();
			client.close();
		}
		catch(IOException e)
		{
			System.out.println("Error closing resource: " + e);
		}
	}
	
	public void run()
	{
		System.out.println("Client accepted: " + client);
//		while(true)
//		{
			
		
		String line="";
		String request="";
		String response;
		boolean readError = true;
		boolean gotFile = false;
		boolean unSupportedFileType = false;
		try
		{
//			System.out.print("Request: ");
			request = line = input.readLine();
//			System.out.println(request);
			readError = false;
			String splitted[] = line.split(" ");
			String fileType = "";
			File data;
			while(input.ready())
			{
//				System.out.println(line);
				line = input.readLine();
				String split[] = line.split(":");
				if (split[0].equals("Connection"))
				{
					if (split[1].equals(" keep-alive"))
					{
						keepAlive = true;
					}
				}
			}
			System.out.println(request);
			if (splitted[1].equals("/"))
			{
				data = new File("index.html");
				if (data.exists())
					gotFile = true;
				else
					throw new IOException();
				fileType = "text/html";
			}
			else
			{
//				System.out.println("hello");
				data = new File("." + splitted[1]);
				String requestedType = splitted[1].split("\\.")[1];
//				System.out.println(requestedType);
				switch(requestedType)
				{
				case "txt":
					fileType = "text/plain";
					break;
				case "html":
					fileType = "text/html";
					break;
				case "jpeg":
				case "jpg":
					fileType = "image/jpeg";
					break;
				case "png":
					fileType = "image/png";
					break;
				case "gif":
					fileType = "image/gif";
					break;
				case "swf":
					fileType = "application/x-shockwave-flash";
					break;
				case "avi":
					fileType = "video/avi";
					break;
				case "css":
					fileType = "text/css";
					break;
				default:
					System.out.println("favicon");
					unSupportedFileType = true;
					throw new IOException();
				}
			}
			if (data.exists())
				gotFile = true;
			else
				throw new IOException();
			response = "HTTP/1.1 200 OK\n";
			response += "Content-type: " + fileType + "\n";
			response += "\n";
			
			System.out.println(response);
			output.writeBytes(response);
			FileInputStream dataReader = new FileInputStream(data);
			int c;
			while((c = dataReader.read()) != -1)
			{
				output.write(c);
			}
			System.out.println("File sent " + client);
			dataReader.close();
			
			close();
		}
		catch(IOException e)
		{
			String errorCode = "", errorMessage = "";
			if(readError || unSupportedFileType)
			{
				errorCode = "400"; errorMessage = "Bad request: invalid file type";
			}
			else if (!gotFile)
			{
				errorCode = "404"; errorMessage = "File not found";
			}
			response = "HTTP/1.1 " + errorCode + " " + errorMessage;
			try {
				response += "\n\n" + errorMessage;
				output.writeBytes(response);
				System.out.println(response);
			} catch (IOException e1) {
				e1.printStackTrace();
			}
			close();
			
		}
			
//		}	
	}
	
	
}
