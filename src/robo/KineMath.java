package robo;

public class KineMath {

	public static void main(String[] args) {
		KineMathTest kt = new KineMathTest();
		kt.run();
	}
	public static double bestAngleInDeg(double[] xy, double dist) {
		return Math.toDegrees(bestAngle(xy, dist));
	}
	public static double bestAngle(double[] xy, double dist) {
		/**
		 * Searches the best angle to go a distance dist from (x,y) so that we get as close as possible to (0,0)
		 */
		xy = new double[] {xy[0] , -xy[1] };
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

		return positivifyAngle(search);
	}
	public static double[] binSearchPointWithDist(double[] xy, double dist) {
		double search = bestAngle(xy, dist);
		return new double[] {xy[0] + Math.cos(search)*dist, xy[1] + Math.sin(search)*dist};
	}
	
	public static double angleBetween(double[] xy1, double[] xy2) {
		return Math.atan2(xy2[1] - xy1[1], xy2[0] - xy1[0]);
	}

	public static double[] etsikulmat(double[] xy, double r2) {
		
		double[] betweenPoint = binSearchPointWithDist(xy, r2);
		double k1 = angleBetween(new double[] {0,0}, betweenPoint);
		double k2 = angleBetween(betweenPoint, xy);
		k2 -= k1;
		return new double[] { Math.toDegrees(k1), Math.toDegrees(k2) };
	}

	public static double positivifyAngle(double a) {
		while (a < 0)
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