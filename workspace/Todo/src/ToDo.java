import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;

public class ToDo {

	private Scanner scan;
	private ArrayList<Pair> list;
	
	public ToDo() throws FileNotFoundException
	{
		scan = new Scanner(new File("TODO.txt"));
		list = new ArrayList<Pair>();
		while(scan.hasNext())
		{
			Pair p = new Pair(Integer.parseInt(scan.next()), scan.next());
			list.add(p);
		}
	}
	
	public void print(int order) // 1 - decreasing , 2 - increasing
	{
		list.sort(null);
		list.toArray();
		if (order == 1)
			list.stream().sorted(Collections.reverseOrder()).forEachOrdered(System.out::println);
		else
			list.stream().sorted().forEachOrdered(System.out::println);
	}
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		try {
			ToDo myTodo = new ToDo();
			myTodo.print(1);
			
			
			
			
			
			
		} catch (Exception e) {
			System.out.println(e);
		}
		

	}

}
