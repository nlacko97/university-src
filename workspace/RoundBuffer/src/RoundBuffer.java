
public class RoundBuffer {
	
	private static final int BUFSIZE = 12;
	private String[] s;
	private int tail;
	private int head;
	
	public RoundBuffer()
	{
		tail = 0;
		head = 0;
		s = new String [BUFSIZE];
	}
	
	public void put(String msg)
	{
		if (tail < BUFSIZE - 1) {
			s[tail] = msg;
			tail++;
		}
	}
	
	public String get()
}
