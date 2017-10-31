package stat;

public class Main {
	
	public static void main(String[] args) {
		System.out.println("---------(1)--------");
		Urn myUrn = new Urn(6, 8, 6, 10, "yes"); // we return the ball to the urn
		System.out.println("");
		myUrn.printEmpiricalDistribution("red");
		
		System.out.println("\n---------(2)--------");
		Urn myUrn2 = new Urn(6, 8, 6, 10, "no"); // without returning the ball to the urn
		System.out.println("");
		myUrn2.printEmpiricalDistribution("red");
		
	}

}
