package printer;

public class Logger extends GenericCollection{
	
	private GenericPrinter p;
	
	private int level;
	
	public Logger() {
		System.out.println("Constructing .. Logger");
	}
	
	public void addPrinter(GenericPrinter p) {
		this.p = get(0);
		add(p);
	}
	
	public void log(String msg) {
		if (this.level > 3) {
			this.p.print(msg);
		}
	}
	
	public void setLevel(int newLevel) {
		this.level = newLevel;
	}
}
