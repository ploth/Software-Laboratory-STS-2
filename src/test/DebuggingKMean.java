package test;

public class DebuggingKMean {

	public static void main(String[] args) {
		double adjustment = 0;
		double sum = 0;
		double subtraction = 0;
		double prototypes[][] = { { 1, 5 }, { 5, 1 } };
		double prototypesNew[][] = { { 2, 5 }, { 4, 1 } };
		for (int i = 0; i < prototypesNew.length; i++) {
			for (int j = 0; j < prototypesNew[i].length; j++) {
				// subtract the new prototypes from the old
				subtraction = prototypes[i][j] - prototypesNew[i][j];
				// (1) calculate the norm of each distance
				sum = sum + subtraction * subtraction;
			}
			// (2) calculate the norm of each distance
			// (1) calculate the arithmetic mean of the norms
			adjustment = adjustment + Math.sqrt(sum);
			sum = 0;
		}
		// (2) calculate the arithmetic mean of the norms
		adjustment = adjustment / 2;
		System.out.println(adjustment);
	}
}
