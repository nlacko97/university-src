package whatsapp;

import java.io.Serializable;

public class Message implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String sender = null;
	private String target = null;
	private String message = null;
	
	public Message(String sender, String target, String message)
	{
		this.sender = sender;
		this.target = target;
		this.message = message;
	}
	
	public String toString()
	{
//		return "[message][sender: " + sender + "][target: " + target + "]: " + message;
		return "<" + sender + ">: " + message;
	}
	
	public String getSender() { return this.sender; }
	public String getTarget() { return this.target; }
	public String getMessage() { return this.message; }
}
