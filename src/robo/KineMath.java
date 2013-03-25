package robo;

public class KineMath {

	static double r2 = 11.85 * 0.8;
	static double r1 = 16;

	public static double getR1() {
		return r1;
	}

	public static double getR2() {
		return r2;
	}

	public static void main(String[] args) {
	}
	
	public static double[] binSearchPointWithDist(double[] xy, double dist) {
		/**
		 * Searches for  a point distance dist from (x,y) that is as close as possible to (0,0)
		 */
		double min = Math.PI - Math.atan2(xy[1], xy[0]);
		double max = min - 2 * Math.PI;
		double search = 0;
		for (int i = 0; i < 70; i++) {
			if (Math.abs(min - max) == 0)
				break;
			search = (min + max) / 2;
			double k1 = Math
					.abs(distToOrigo(kärki(xy[0], xy[1], dist, (search + min) / 2)));
			double k2 = Math
					.abs(distToOrigo(kärki(xy[0], xy[1], dist, (search + max) / 2)));
			if (k1 < k2) {
				max = search;
			} else {
				min = search;
			}
		}
		return new double[] {xy[0] + Math.cos(search)*dist, xy[1] + Math.sin(search)*dist};
	}
	
	public static double angleBetween(double[] xy1, double[] xy2) {
		return Math.atan2(xy2[1] - xy1[1], xy2[0] - xy1[0]);
	}

	public static double[] etsikulmat(double[] xy) {

		System.out.println("x : " + xy[0] + "   y : " + xy[1]);
		
		double[] betweenPoint = binSearchPointWithDist(xy, r2);
		double k1 = Math.atan2(betweenPoint[1], betweenPoint[0]);

		double k2 = Math.atan2(xy[1] - betweenPoint[1], xy[0] - betweenPoint[0]) - k1;
		return new double[] { Math.toDegrees(k1), Math.toDegrees(k2) };
	}

	public static double cleanAngle(double a) {
		while (a < -Math.PI / 4)
			a += Math.PI * 2;
		return a;
	}

	public static double distToOrigo(double[] piste) {
		return Math.sqrt(piste[0] * piste[0] + piste[1] * piste[1]);
	}

	public static double[] kärki(double x, double y, double r, double angle) {
		double rx = x + Math.cos(angle) * r;
		double ry = y - Math.sin(angle) * r;
		return new double[] { rx, ry };
	}
}