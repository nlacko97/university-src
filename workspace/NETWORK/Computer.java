public class Computer {
		public int x;
		public int y;
		
		public Computer(int x, int y) throws NumberFormatException 
		{
			if (x < 0 || x > 200 || y < 0 || y > 200)
				throw new NumberFormatException();
			this.x = x;
			this.y = y;
		}

		public double getDistanceFrom(Computer a)
		{
			double result = 0.0;
			result = (a.x - this.x) * (a.x - this.x) + (a.y - this.y) * (a.y - this.y);
			return Math.sqrt(result);
		}

		public String toString()
		{
			return "(" + this.x + ", " + this.y + ")";
		}
	}