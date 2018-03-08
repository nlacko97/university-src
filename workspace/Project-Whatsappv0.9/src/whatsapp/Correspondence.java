package whatsapp;

import java.util.*;

public class Correspondence {

	private String[] participants;
	private List<Message> history = null;
	
	public Correspondence(String ... participants)
	{
		int i = 0;
		for(String s : participants)
			this.participants[i++] = s;
		history = new ArrayList<>();
	}
	
	public void addToHistory(Message msg)
	{
		history.add(msg);
	}
	
}
