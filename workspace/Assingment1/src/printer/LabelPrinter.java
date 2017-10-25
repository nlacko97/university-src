package printer;

public class LabelPrinter extends GenericPrinter {
	
	private String label; 
	
	public LabelPrinter(String s) {
		System.out.println("Constructing .. LabelPrinter");
		this.label = s;
	}
	
	public void print(String msg) {
		System.out.println(this.label + ":\t" + msg);
	}
}
