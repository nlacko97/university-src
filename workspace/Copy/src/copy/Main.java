package copy;

import java.io.IOException;

public class Main {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Copy copy = new Copy("src/copy/input", "out1");
		Copy copy2 = new Copy("src/copy/input", "out2");
		Copy copy3 = new Copy("src/copy/input", "out3");
		
		try {
			copy.mv();
			copy2.mv1();
			copy3.mv3();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("Success");
	}

}
