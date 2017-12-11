
public class Answer implements Comparable<Answer>
{

	public int nr;
	public int result;

	public Answer(int nr, int result)
	{
		this.nr = nr;
		this.result = result;
	}

	public String toString()
	{
		return this.nr + ". " + result;
	}

	public int compareTo(Answer a)
	{
		if (this.nr > a.nr)
			return 1;
		else if(this.nr < a.nr)
			return -1;
		else
			return 0;
	}

}