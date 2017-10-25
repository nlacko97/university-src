package printer;

public class GenericPrinter implements Printer {

	
	public GenericPrinter() {
		System.out.println("Constructing .. GenericPrinter");
	}

	@Override	
	public void print(String msg) {
		
		System.out.println(msg);

	}

}
