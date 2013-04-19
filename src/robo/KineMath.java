package robo;

public class KineMath {

	public static void main(String[] args) {
		KineMathTest kt = new KineMathTest();
		kt.run();

		double armFromSwiwel = 11.3;
		double armFromOrigo = 13.4;

		double[] nearRange = { 0, 180 };
		double[] farRange = { -160, 150 };

		double[] xy = { -10, 14 };

		double ang[] = etsikulmat(xy, armFromSwiwel, armFromOrigo, nearRange,
				farRange);
		System.out.println(ang[0] + " m " + ang[1]);
	}

	public static double bestAngleInDeg(double[] xy, double dist, double target) {
		return Math.toDegrees(bestAngleCW(xy, dist, target));
	}

	/**
	 * Koettaa palauttaa kulman, jota pitkin kulkemalla pisteestä xy matkan dfromxy päästään pisteeseen,
	 * jonka etäisyys origosta on mahdollisimman lähellä targetia. Min ja max kertovat välin, jolta pistettä etsitään
	 * @param xy
	 * @param dfromxy
	 * @param target
	 * @param min
	 * @param max
	 * @return
	 */
	private static double binSearchAngle(double[] xy, double dfromxy,
			double target, double min, double max) {

		double search = 0;
		for (int i = 0; i < 70; i++) { //enint. 70 kierrosta
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

	/**
	 * suorittaa binäärihaun kulmalle käyttäen puoliympyrän muotoista kulmahaarukkaa jonka sivu osoittaa origoon
	 * ja joka kiertää siitä myötäpäivään
	 * @param xy
	 * @param dfromxy
	 * @param target
	 * @return
	 */
	private static double bestAngleCW(double[] xy, double dfromxy, double target) {
		double min = angleBetween(xy, new double[] { 0, 0 }) - Math.PI / 2;
		double max = min + Math.PI / 2;
		return binSearchAngle(xy, dfromxy, target, min, max);
	}
	/**
	 * suorittaa binäärihaun kulmalle käyttäen puoliympyrän muotoista kulmahaarukkaa jonka sivu osoittaa origoon
	 * ja joka kiertää siitä vastapäivään
	 * @param xy
	 * @param dfromxy
	 * @param target
	 * @return
	 */
	private static double bestAngleCCW(double[] xy, double dfromxy, double target) {
		double min = angleBetween(xy, new double[] { 0, 0 }) + Math.PI / 2;
		double max = min - Math.PI / 2;
		return binSearchAngle(xy, dfromxy, target, min, max);
	}

	/**
	 * ratkaisee myötäpäiväisen kulmanratkaisun tuloksesta pisteen
	 * @param xy
	 * @param dfromxy
	 * @param target
	 * @return
	 */
	protected static double[] bestPointCW(double[] xy, double dfromxy,
			double target) {
		double search = bestAngleCW(xy, dfromxy, target);
		return kärki(xy, dfromxy, search);
	}

	/**
	 * ratkaisee vastapäiväisen kulmanratkaisun tuloksesta pisteen
	 * @param xy
	 * @param dfromxy
	 * @param target
	 * @return
	 */
	private static double[] bestPointCCW(double[] xy, double dfromxy,
			double target) {
		double search = bestAngleCCW(xy, dfromxy, target);
		return kärki(xy, dfromxy, search);
	}

	public static double angleBetween(double[] xy1, double[] xy2) {
		return Math.atan2(xy2[1] - xy1[1], xy2[0] - xy1[0]);
	}

	/**
	 * Palauttaa kaksi kulmaa asteina double[] taulukossa. Ensimmäinen arvo kertoo origosta lähtevän kulman, toinen arvo 
	 * origosta armFromOrigo päässä olevan nivelen kulman.
	 * @param xy Mihin pisteeseen halutaan päästä
	 * @param armFromSwiwel kauemman varren pituus
	 * @param armFromOrigo lähemmän varren pituus
	 * @param nearRange lähemmän varren kulmarajoitukset
	 * @param farRange kauemman varren kulmarajoituksen
	 * @return
	 */
	public static double[] etsikulmat(double[] xy, double armFromSwiwel,
			double armFromOrigo, double[] nearRange, double[] farRange) {
		double[] betweenPoint = bestPointCW(xy, armFromSwiwel, armFromOrigo);
		double k1 = Math.toDegrees(angleBetween(new double[] { 0, 0 },
				betweenPoint));
		double k2 = Math.toDegrees(angleBetween(betweenPoint, xy));
		k2 -= k1;

		if (!fitsInRange(k1, nearRange) || !fitsInRange(k2, farRange)) {
			betweenPoint = bestPointCCW(xy, armFromSwiwel, armFromOrigo);
			k1 = Math.toDegrees(angleBetween(new double[] { 0, 0 },
					betweenPoint));
			k2 = Math.toDegrees(angleBetween(betweenPoint, xy));
			k2 -= k1;
			if (!fitsInRange(k1, nearRange) || !fitsInRange(k2, farRange))
				return new double[] { Double.NaN, Double.NaN };
		}
		return new double[] { k1, k2 };
	}

	/**
	 * Palauttaa kaksi kulmaa asteina double[] taulukossa. Ensimmäinen arvo kertoo origosta lähtevän kulman, toinen arvo 
	 * origosta armFromOrigo päässä olevan nivelen kulman.
	 * @param xy Mihin pisteeseen halutaan päästä
	 * @param armFromSwiwel kauemman varren pituus
	 * @param armFromOrigo lähemmän varren pituus
	 * @return
	 */
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

	protected static double distToOrigo(double[] piste) {
		return Math.sqrt(piste[0] * piste[0] + piste[1] * piste[1]);
	}

	protected static double[] kärki(double[] xy, double r, double angle) {
		return kärki(xy[0], xy[1], r, angle);
	}

	private static double[] kärki(double x, double y, double r, double angle) {
		return new double[] { x + Math.cos(angle) * r, y + Math.sin(angle) * r };
	}

	/**
	 * tarkistaa mahtuuko kulma annettuun haarukkaan
	 * @param angle
	 * @param constraint
	 * @return
	 */
	public static boolean fitsInRange(double angle, double[] constraint) {
		double min = constraint[0];
		double max = constraint[1];
		if (angle > max) {
			angle -= 360;
			if (angle < min)
				return false;
		}
		if (angle < min) {
			angle += 360;
			if (angle > max)
				return false;
		}
		return true;
	}
}