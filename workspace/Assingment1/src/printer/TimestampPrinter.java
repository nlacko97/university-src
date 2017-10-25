package printer;
import java.util.Date;
public class TimestampPrinter extends GenericPrinter {
	
	public TimestampPrinter() {
		System.out.println("Constructing .. TimestampPrinter");
	}
	
	
	public void print(String msg) {
		System.out.println(new Date() + "\t" + msg);
	}
	
}
