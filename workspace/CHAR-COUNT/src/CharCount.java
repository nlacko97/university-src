import java.util.Scanner;

public class CharCount {

	public static void main(String[] args) {
		
		Scanner scan = new Scanner(System.in);
		
		System.out.println("Please read a string");
		
		String input = scan.nextLine();
		
		scan.close();
		
		System.out.println("Read string: " + input);
		
		int length = input.length() + 1;
		
		System.out.println("Length of string is " + length + " characters");
			
	}

}
