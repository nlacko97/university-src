package printer;

public class Main {

	public static void main(String[] args) {
		Logger P = new Logger();
		P.addPrinter(new LabelPrinter("myLabel"));
		P.setLevel(3);
		P.setLevel(5);
		P.log("This is interesting");
		P.addPrinter(new TimestampPrinter());
		P.log("Hi :)");
	}

}
