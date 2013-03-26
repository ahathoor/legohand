package robo;

public class KineMath {

	public static void main(String[] args) {
		KineMathTest kt = new KineMathTest();
		kt.run();
	}

	public static double bestAngleInDeg(double[] xy, double dist, double target) {
		return Math.toDegrees(bestAngleCW(xy, dist, target));
	}

	private static double binSearchAngle(double[] xy, double dfromxy,
			double target, double min, double max) {

		double search = 0;
		for (int i = 0; i < 70; i++) {
			if (Math.abs(min - max) == 0)
				break;
			search = (min + max) / 2;
			double k1 = Math.abs(distToOrigo(kärki(xy[0], xy[1], dfromxy,
					(search + min) / 2))
					- target);
			double k2 = Math.abs(distToOrigo(kärki(xy[0], xy[1], dfromxy,
					(search + max) / 2))
					- target);
			if (k1 < k2) {
				max = search;
			} else {
				min = search;
			}
		}

		return positivifyAngle(search);
	}

	public static double bestAngleCW(double[] xy, double dfromxy, double target) {
		double min = angleBetween(xy, new double[] { 0, 0 }) - Math.PI / 2;
		double max = min + Math.PI / 2;
		return binSearchAngle(xy, dfromxy, target, min, max);
	}

	public static double bestAngleCCW(double[] xy, double dfromxy, double target) {
		double min = angleBetween(xy, new double[] { 0, 0 }) + Math.PI / 2;
		double max = min - Math.PI / 2;
		return binSearchAngle(xy, dfromxy, target, min, max);
	}

	public static double[] bestPointCW(double[] xy, double dfromxy,
			double target) {
		double search = bestAngleCW(xy, dfromxy, target);
		return kärki(xy, dfromxy, search);
	}

	public static double[] bestPointCCW(double[] xy, double dfromxy,
			double target) {
		double search = bestAngleCCW(xy, dfromxy, target);
		return kärki(xy, dfromxy, search);
	}

	public static double angleBetween(double[] xy1, double[] xy2) {
		return Math.atan2(xy2[1] - xy1[1], xy2[0] - xy1[0]);
	}

	public static double[] etsikulmat(double[] xy, double armFromSwiwel, double armFromOrigo, double[] nearRange, double[] farRange) {
		double[] betweenPoint = bestPointCW(xy, armFromSwiwel, armFromOrigo);
		double k1 = Math.toDegrees(angleBetween(new double[] {0,0}, betweenPoint));
		double k2 = Math.toDegrees(angleBetween(betweenPoint, xy));
		k2-=k1;
		
		if(!fitsInRange(k1, nearRange) || !fitsInRange(k2, farRange)){
			betweenPoint = bestPointCCW(xy, armFromSwiwel, armFromOrigo);
			k1 = Math.toDegrees(angleBetween(new double[] {0,0}, betweenPoint));
			k2 = Math.toDegrees(angleBetween(betweenPoint, xy));
			k2-=k1;
		if(!fitsInRange(k1, nearRange) || !fitsInRange(k2, farRange))
			return new double[] {Double.NaN, Double.NaN};
		}
		return new double[] {k1,k2};
	}

	public static double[] etsikulmat(double[] xy, double armFromSwiwel,
			double armFromOrigo) {

		double[] betweenPoint = bestPointCW(xy, armFromSwiwel, armFromOrigo);
		double k1 = angleBetween(new double[] { 0, 0 }, betweenPoint);
		double k2 = angleBetween(betweenPoint, xy);
		k2 -= k1;
		return new double[] { Math.toDegrees(k1), Math.toDegrees(k2) };
	}

	public static double positivifyAngle(double a) {
		while (a + 0.1 < 0)
			a += Math.PI * 2;
		if (a > Math.PI * 2)
			a -= Math.PI * 2;
		return a;
	}

	public static double distToOrigo(double[] piste) {
		return Math.sqrt(piste[0] * piste[0] + piste[1] * piste[1]);
	}

	public static double[] kärki(double[] xy, double r, double angle) {
		return kärki(xy[0], xy[1], r, angle);
	}

	public static double[] kärki(double x, double y, double r, double angle) {
		return new double[] { x + Math.cos(angle) * r, y + Math.sin(angle) * r };
	}

	public static boolean fitsInRange(double angle, double[] constraint) {
		double min = constraint[0];
		double max = constraint[1];
		if (angle > max)
			angle -= 360;
		if (angle < min)
			return false;
		return true;
	}
}