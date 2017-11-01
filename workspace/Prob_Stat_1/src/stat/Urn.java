package stat;

public class Urn {

	private int red;
	private int white;
	private int blue;
	private int n;
	private int total;
	private boolean wReturn;
	
	public Urn(int red, int white, int blue, int n, String wReturn) {
		this.red = red;
		this.white = white;
		this.blue = blue;
		this.n = n;
		this.total = this.red + this.blue + this.white;
		System.out.println("Red   balls: " + red);
		System.out.println("Blue  balls: " + blue);
		System.out.println("White balls: " + white);
		System.out.println("Nr of turns: " + n);
		if (wReturn == "yes") this.wReturn = true;
		else this.wReturn = false;
	}
	
	public int getTotalNrOfSums() {
		int sum;
		if (this.wReturn)
			sum = this.total - this.n;
		else
			sum = this.n;
		int nr = 0;
		if (this.wReturn) {
			for(int i = 0; i <= red; i++) {
				for(int j = 0; j <= white; j++) {
					for(int k = 0; k <= blue; k++) {
						if (i + j + k == sum) {
							nr++;
						}
					}
				}
			}
		}
		else {
			for(int i = 0; i <= this.n; i++) {
				for(int j = 0; j <= this.n; j++) {
					for(int k = 0; k <= this.n; k++) {
						if (i + j + k == sum) {
							nr++;
						}
					}
				}
			}
		}
		return nr;
	}
	
	public void printEmpiricalDistribution(String... color) {
		int colorNr = 0;
		if (color.length == 1)
			switch (color[0]) {
				case "red": colorNr = this.red;break;
				case "blue": colorNr = this.blue;break;
				case "white": colorNr = this.white; break;
				default: {
					System.out.println("Wrong value");
					System.exit(1);
				}
			}
		else {
			for(int i = 0; i < color.length; i++) {
				switch (color[i]) {
					case "red": colorNr += this.red;break;
					case "blue": colorNr += this.blue;break;
					case "white": colorNr += this.white; break;
					default: {
						System.out.println("Wrong value");
						System.exit(1);
					}
				}
				
			}
		}
		int t = this.getTotalNrOfSums();
		int[] distr = getDistr(colorNr);
		System.out.print("Chosen color(s): ");
		for(int i = 0; i < color.length; i++) {
			System.out.print(color[i] + " ");
		}
		System.out.println("");
		this.printPossibilities(t, colorNr, distr);
		
		System.out.println("Graphical representation:");
		for(int i = 0; i <= colorNr; i++) {
			for(int j = 0; j < distr[i]; j++) {
				System.out.print("---");
			}
			System.out.println("|");
		}
		
	}
	
	private int[] getDistr(int colorNr) {
		
		int sum;
		if (this.wReturn)
			sum = this.total - this.n;
		else
			sum = this.n;
		
		int v1, v2;
		int[] distr = new int [this.total + 1];
		for(int i = 0; i <= this.total; i++) {
			distr[i] = 0;
		}
		if (!this.wReturn) {
			colorNr = v1 = v2 = this.n;
		}
		else
			
				if (colorNr == red) {
					v1 = blue; v2 = white;
				} else if (colorNr == white) {
					v1 = red; v2 = blue;
				} else {
					v1 = red; v2 = white;
				}
	
			
			for(int i = 0; i <= colorNr; i++) {
				for(int j = 0; j <= v1; j++) {
					for(int k = 0; k <= v2; k++) {
						if (i + j + k == sum) {
							distr[i]++;
						}
					}
				}
				//System.out.println(distr[i]);
			}
		return distr;
	}
	
	public void printPossibilities(int total, int colorNr, int[] distr) {
		System.out.println("Possibility distribution for this color:");
		for(int i = 0; i <= colorNr; i++) {
			System.out.println(i + " : " + distr[i] + "/" + total);
		}
	}
	
	
}
