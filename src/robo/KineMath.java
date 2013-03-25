package robo;

public class KineMath {

	public static void main(String[] args) {
		KineMathTest kt = new KineMathTest();
		kt.run();
	}
	public static double bestAngleInDeg(double[] xy, double dist, double target) {
		return Math.toDegrees(bestAngle(xy, dist, target));
	}
	public static double bestAngle(double[] xy, double dfromxy, double target) {
		/**
		 * Searches the best angle to go a distance <b>dist</b> from (x,y) so that distance from 
		 * origo is as close to target as possible
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
					.abs(distToOrigo(kärki(xy[0], xy[1], dfromxy, (search + min) / 2)) - target);
			double k2 = Math
					.abs(distToOrigo(kärki(xy[0], xy[1], dfromxy, (search + max) / 2)) - target);
			if (k1 < k2) {
				max = search;
			} else {
				min = search;
			}
		}

		return positivifyAngle(search);
	}
	public static double[] binSearchPointWithDist(double[] xy, double dfromxy, double target) {
		double search = bestAngle(xy, dfromxy, target);
		return new double[] {xy[0] + Math.cos(search)*dfromxy, xy[1] + Math.sin(search)*dfromxy};
	}
	
	public static double angleBetween(double[] xy1, double[] xy2) {
		return Math.atan2(xy2[1] - xy1[1], xy2[0] - xy1[0]);
	}

	public static double[] etsikulmat(double[] xy, double armFromSwiwel, double armFromOrigo, double[] nearRange, double[] farRange) {
		double[] a = etsikulmat(xy, armFromSwiwel, armFromOrigo);
		a = new double[] {constrainAngle(a[0], nearRange),
				constrainAngle(a[1], farRange)};
		if(Double.isNaN(a[0]) || Double.isNaN(a[1]))
			return null;
		return a;
	}
	public static double[] etsikulmat(double[] xy, double armFromSwiwel, double armFromOrigo) {
		
		double[] betweenPoint = binSearchPointWithDist(xy, armFromSwiwel, armFromOrigo);
		double k1 = angleBetween(new double[] {0,0}, betweenPoint);
		double k2 = angleBetween(betweenPoint, xy);
		k2 -= k1;
		return new double[] { Math.toDegrees(positivifyAngle(k1)), Math.toDegrees(positivifyAngle(k2)) };
	}

	public static double positivifyAngle(double a) {
		while (a+0.1 < 0)
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
	
	public static double constrainAngle(double angle, double[] constraint){
		/**
		 * constraints an angle to the given range, and return NaN if not possible;
		 */
		double min = constraint[0];
		double max = constraint[1];
		angle = positivifyAngle(angle);
		if (angle > max)
			angle -= 360;
		if (angle < min)
			return Double.NaN;
		return angle;
	}
}