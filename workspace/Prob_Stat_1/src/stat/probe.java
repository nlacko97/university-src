package stat;

public class probe {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		int nr = 0;
		for(int i = 1; i<=6; i++) {
			for(int j = 1; j <= 6; j++) {
				for(int k = 1; k <= 6; k++) {
					if (i + j + k == 18) nr++;
				}
			}
		}
		System.out.println("Nr = " + nr);
	}

}
