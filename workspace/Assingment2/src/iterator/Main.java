package iterator;

import java.util.Iterator;

public class Main {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		MyCollectionImp list = new MyCollectionImp();
		
		list.add(new Car("BMW"));
		list.add(new Car("VW"));
		list.add(new Car("Lada"));
		Iterator it = list.iterator();
//		list.remove(4);
		while(it.hasNext())
		{
			System.out.println(it.next());
		}
	}

}
