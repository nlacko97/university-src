import java.util.*;
import java.io.*;


/*
 * ----------TODO
 * [x] Make class for storing questions
 * [x] Verify errors on input
 * [x] Build error list, print lines and ignore input with error
 * [x] Make class for computed answers implementing Comparable
 * [x] Sort computed answers based on question number
 * [] Sort out special cases: 
 		[x] non-existent question numbers
 		[x] no answer for an existing question
 		[] blank answer
 		[x] duplicates: input possible answers (consider the last one)
 		[x] duplicates: several answers for same question (consider the last one)
 * [] Separate scoring method from initialization
 *
 */

public class STest {
	
	public static ArrayList<Question> questions = new ArrayList<Question>();
	public static ArrayList<String> errors = new ArrayList<String>();

	public static void run() throws IOException
	{
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

		boolean areMoreQuestions = true;
		String type = "";
		type = br.readLine();
		int nr;
		int rowNr = 1;
		while(areMoreQuestions)
		{
			if (!type.equals("multichoice") && !type.equals("singlechoice"))
				areMoreQuestions = false;
			else
			{
				int typeNr = rowNr;
				System.out.println("\t" + typeNr);
				String line = br.readLine();
				rowNr++;
				nr = Integer.parseInt(line.split("\\.")[0]);
				while((line = br.readLine()).charAt(1) != '.')
					rowNr++;
				ArrayList<String> answers = new ArrayList<String>();
				ArrayList<String> correct = new ArrayList<String>();
				boolean valid = true;
				while(line.charAt(1) == '.')
				{
					String a = line.split("\\.")[0];
					if (!answers.contains(a))
						answers.add(a);
					line = br.readLine();
					rowNr++;
				}
				String[] tmp = line.split("\\s+");
				if(type.equals("singlechoice") && tmp.length > 2)
				{
						// @TODO: ERROR TO BE GENERATED
					errors.add("ERROR - " + (typeNr));
					valid = false;
				}
				for(int i = 1; i < tmp.length; i++)
				{
					if (answers.contains(tmp[i]))
					{
						if (valid)
							correct.add(tmp[i]);
					}
					else
					{
						// @TODO: ERROR TO BE GENERATED
						if (valid)
						{
							valid = false;
							errors.add("ERROR - " + rowNr);
							break;
						}
					}
				}
				if (valid)
					questions.add(new Question(type, nr, answers, correct));
				while((type = br.readLine()).length() == 0 && type != null);
			}
		}

		for(String error : errors)
			System.out.println(error);

		String name = type;
		String answer = "";
		String[] psb;
		boolean done = false;
		while(!done)
		{
			System.out.println(name);
			int result = 0;
			ArrayList<Answer> ans = new ArrayList<Answer>();
			while((answer = br.readLine()) != null && answer.length() != 0)
			{
				psb = answer.split("\\s+");
				nr = Integer.parseInt(psb[0].substring(0, psb[0].length() - 1));
				boolean exists = false;
				Question selected = null;
				for(int i = 0; i < questions.size(); i++)
				{
					selected = questions.get(i);
					if (selected.nr == nr)
					{
						exists = true;
						break;
					}
				}
				if (exists)
				{
					int score = 0;
					for(int i = 1; i < psb.length; i++)
					{
						if (!selected.Answers.contains(psb[i]) || (selected.Answers.contains(psb[i]) && !selected.CorrectAnswers.contains(psb[i])))
							score++;
					}
					for(String q : selected.CorrectAnswers)
					{
						int j;
						for(j = 1; j < psb.length; j++)
						{
							if (q.equals(psb[j]))
								break;
						}
						if (j == psb.length)
							score++;
					}
					boolean previous = false;
					int k = 0;
					for(Answer tmp : ans)
					{
						if (tmp.nr == nr)
						{
							previous = true;
							result -= tmp.result;
							ans.set(k, new Answer(nr, score));
						}
						else
							k++;

					}
					if (!previous)
						ans.add(new Answer(nr, score));
					result += score;
				}
			}
			for(Question q : questions)
			{
				boolean found = false;
				for(Answer a : ans)
				{
					if (q.nr == a.nr)
					{
						found = true;
						break;
					}
				}
				if (!found)
					result += q.CorrectAnswers.size();
			}
			int mark;
			if (result < 3)
				mark = 1;
			else if (result < 6)
				mark = 2;
			else if (result < 9)
				mark = 3;
			else
				mark = 4;
			ans.sort(null);
			for(Answer tmp : ans)
				System.out.println(tmp);
			System.out.println("Result: " + mark + " (" + result + ")\n");
			if (answer == null)
				done = true;
			else
			{
				while((name = br.readLine()) != null && name.length() == 0);
				if (name == null)
					done = true;
			}

		}

	}



	public static void main(String[] args) throws IOException{
		try {
			run();
		}
		catch(Exception e) {
			System.out.println(e);
		}
	}

}