
class Main {
	
	public static void main(String[] args) {

		Timi timi = new Timi(19);

		Timi.print();

		System.out.println(Runtime.getRuntime().availableProcessors());

		System.out.println("Timi is " + timi.getAge() + " years old :)");
	}
}