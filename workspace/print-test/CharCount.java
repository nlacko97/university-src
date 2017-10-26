import java.util.Scanner;

public class CharCount {

	public static void main(String[] args) {
		
		Scanner scan = new Scanner(System.in);
		
		String input = scan.nextLine();
		
		scan.close();
		
		int length = input.length() + 1;
		
		System.out.println(length);

	}

}
