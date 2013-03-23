package org.lejos.example;

public class KineMath {
	static double R1 = 16;
	static double R2 = 15;
	static double alkukulmaA = Math.PI/4;
	static double alkukulmaB = 0;
	static double[] nolla = {Math.cos(alkukulmaA) * R1 - Math.cos(alkukulmaB) * R2, Math.sin(alkukulmaA) * R1 + Math.sin(alkukulmaB) * R2};
	public static double[] math(double x, double y) {

		x+=nolla[0];
		y+=nolla[1];
		


		double Z = (R2 * R2 - R1 * R1 - x * x - y * y) / (-2 * x);
		double W = y / -x;

		// quadratic part

		double a = 1 + W * W;
		double b = 2 * Z * W;
		double c = Z * Z - R1 * R1;

		double det = b * b - 4 * a * c;
		double sy1 = (-b + Math.sqrt(det)) / (2 * a);
		double sx1 = Math.sqrt(R1 * R1 - sy1 * sy1);

//		sy1 = (-b - Math.sqrt(det)) / (2 * a);
//		sx1 = Math.sqrt(R1 * R1 - sy1 * sy1);
		
		double alpha = Math.asin(sy1/R1);
//		System.out.println(Math.toDegrees(alpha));
		double beta = alpha + Math.asin((y-sy1)/R2);
//		System.out.println(Math.toDegrees(beta));
		
		return new double[] {Math.toDegrees(alkukulmaA - alpha), -Math.toDegrees(beta - alkukulmaA)};


//		System.out.println("Second solution y = " + sy2 + "; x = " + sx2);
	}

	public static void main(String[] args) {
		double[] a = nolla;
		System.out.println(a[0]);System.out.println(a[1]);
		a = math(-12,12);
		System.out.println(a[0]);System.out.println(a[1]);
		a = math(-13,13);
		System.out.println(a[0]);System.out.println(a[1]);
	}
}