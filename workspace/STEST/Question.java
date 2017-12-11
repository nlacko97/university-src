import java.util.ArrayList;

public class Question
{
	public String type;
	public int nr;
	public ArrayList<String> Answers;
	public ArrayList<String> CorrectAnswers;

	public Question(String type, int nr, ArrayList<String> Answers, ArrayList<String> CorrectAnswers)
	{
		this.type = type;
		this.nr = nr;
		this.Answers = Answers;
		this.CorrectAnswers = CorrectAnswers;
	}
}