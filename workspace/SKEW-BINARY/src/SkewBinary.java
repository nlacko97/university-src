import java.util.Scanner;

public class SkewBinary {

	public static boolean isValid(String k)
	{
		boolean foundTwo = false;
		for(int i = 0; i < k.length(); i++)
		{
			int x = k.charAt(i) - '0';
			if (x > 2 || x < 0)
				return false;
			else
			{
				if (x == 2)
					foundTwo = true;
				else if (foundTwo && x > 0)
					return false;
			}
			x /= 10;
		}
		return true;
	}
	
	public static int convertToDecimal(String k)
	{
		int power = 1;
		int Result = 0;
		for(int i = k.length() - 1; i >= 0; i--)
		{
			int x = k.charAt(i) - '0';
			Result += x * (Math.pow(2, power) - 1);
			//System.out.println("res: " + Result + " pow: " + power);
			power++;
		}
		return Result;
	}
	
	
	
	public static void main(String[] args) {
		
		try
		{
			Scanner scan = new Scanner(System.in);
			
			String x = scan.next();
			
			scan.close();
			
			if (SkewBinary.isValid(x))
			{
				System.out.print(SkewBinary.convertToDecimal(x));
			}
			else
			{
				System.out.print("Error");
			}			
		}
		catch(Exception e)
		{
			System.out.print("Error");
		}
	}

}
