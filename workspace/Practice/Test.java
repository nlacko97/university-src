import java.util.*;

public class Test
{
	
	public static void argtest(Object... args)
	{
		for(int i = 0; i < args.length; i++)
		{
			System.out.println(args[i]);
		}
	}

	public enum Planet {
		MARS(1, 2),
		EARTH(3, 4);

		private final int mass, radius;
		Planet(int mass, int radius)
		{
			this.mass = mass;
			this.radius = radius;
		}

		double getLadida() {
			return mass + radius;
		}
	}

	public enum Operation {
		PLUS, MINUS;

		double eval(int x, int y) {
			switch(this) {
				case PLUS: return x + y;
				case MINUS: return x - y;
			}
			return 1.0;
		}
	}

	
	public static void main(String[] args) {


		float i = Float.NaN;
		while(i != i + 0)
			System.out.print("hi");






	}
}