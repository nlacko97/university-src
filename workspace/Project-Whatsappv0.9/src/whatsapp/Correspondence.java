package whatsapp;

import java.io.Serializable;
import java.util.*;

public class Correspondence implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6024279751597062224L;
	public List<String> participants = null;
	public List<Message> history = null;
	
	public Correspondence(ArrayList<String> p)
	{
		this.participants = p;
		history = new ArrayList<>();
	}
	
	public String toString()
	{
		return participants + " - " + history.size();
	}
	
}
