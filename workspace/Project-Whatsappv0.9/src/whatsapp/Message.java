package whatsapp;

import java.io.Serializable;

public class Message implements Serializable {

	private String sender = null;
	private String target = null;
	private String message = null;
	private boolean displayedForSender = false;
	private boolean displayedForTarget = true;
	
	public Message(String sender, String target, String message)
	{
		this.sender = sender;
		this.target = target;
		this.message = message;
	}
	
	public String toString()
	{
		return "[" + sender + "] -> " + message;
	}
	
	public String getSender() { return this.sender; }
	public String getTarget() { return this.target; }
	public String getMessage() { return this.message; }
	public void setDisplayedForSender() { this.displayedForSender = true; }
	public void setDisplayedForTarget() { this.displayedForTarget = true; }
}
