package string;

import java.util.Iterator;

public class Main {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		try {
			MyString s = new MyString("Eclipse");
			System.out.println(s);
			s.append(" Editor");
			System.out.println(s);
			s.append('s');
			System.out.println(s);
			s.insert(7, "Jav");
			System.out.println(s);
			s.insert(11, 'a');
			System.out.println(s);
			s.delete(11,  1);
//			System.out.println(s);
			Iterator<Character> it = s.iterator();
			while(it.hasNext()) {
				System.out.print(it.next());
			}
		}
		catch(Exception e) {
			System.out.println(e);
		}
		
	}

}
