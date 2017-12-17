package tp.pr2.util;

public class MyMathsUtil {
	
	// Convert from long to int since we will not need large numbers
	public static int nextFib(int previous) {
		double phi = (1 + Math.sqrt(5)) / 2;
		return (int) Math.round(phi * previous);
	}

}