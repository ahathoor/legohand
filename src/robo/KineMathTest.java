package robo;

public class KineMathTest {

	// KineMath k = new KineMath();

	public void run() {
		testAngleBetweenxx00();
		testAngleBetween00xx();
		testAngleBetween0x00();
		testBinSearchStraightUp();
		testBinSearchStraightDown();
		testBinSearchStraightLeft();
		testBinSearchStraightRight();
		testEtsiKulmat();
		testBestAngle();

		karkiTest();
	}

	public void karkiTest() {
		double[] p = KineMath.kärki(new double[] { 0, 0 }, 50, Math.PI / 4);
		assertEqual("kärkitest", Math.sqrt(50 * 50 / 2), p[0], 0.1);

		p = KineMath.kärki(new double[] { 0, 0 }, 50, 0);
		assertEqual("kärkitest2", 50, p[0], 0.1);

	}

	public void testBestAngle() {
		double[] p = { 0, 100 };
		double angle = KineMath.bestAngleInDeg(p, 10, 90);
		assertEqual("Best angle straight up", 270, angle, 0.1);

		p = new double[] { 0, -100 };
		angle = KineMath.bestAngleInDeg(p, 10, 90);
		assertEqual("Best angle straight down", 90, angle, 0.1);

		p = new double[] { -100, 0 };
		angle = KineMath.bestAngleInDeg(p, 10, 90);
		assertEqual("Best angle straight left", 0, angle, 0.1);

		p = new double[] { 100, 0 };
		angle = KineMath.bestAngleInDeg(p, 10, 90);
		assertEqual("Best angle straight right", 180, angle, 0.1);

		p = new double[] { 25, 25 };
		angle = KineMath.bestAngleInDeg(p, 5, 5);
		assertEqual("Best angle up right", 225, angle, 0.1);

		p = new double[] { -100, 100 };
		angle = KineMath.bestAngleInDeg(p, 7, 3);
		assertEqual("Best angle up left", 315, angle, 0.1);

		p = new double[] { -100, -100 };
		angle = KineMath.bestAngleInDeg(p, 10, 1);
		assertEqual("Best angle down left", 45, angle, 0.1);

		p = new double[] { 100, -100 };
		angle = KineMath.bestAngleInDeg(p, 10, 1);
		assertEqual("Best angle down right", 135, angle, 0.1);
		

		double x = Math.sqrt(5*5 + 6*6);
		p = new double[] { x, x };
		angle = KineMath.bestAngleInDeg(p, 5, 6);
		assertEqual("Best angle just fits", 225, angle, 0.1);

		angle = KineMath.bestAngleInDeg(p, 5, 7);
		assertEqual("Best angle just doesnt fit", 197.5, angle, 0.1);
		

		double swa = 15.5;
		double ora = 555;

		x = Math.sqrt(ora*ora + swa*swa);
		p = new double[] { x, x };
		angle = KineMath.bestAngleInDeg(p, swa, ora);
		assertEqual("Best angle another just fit", 225, angle, 1);
	}

	public void testEtsiKulmat() {
		double[] p = { 0, 10 };
		double[] ang = KineMath.etsikulmat(p, 5, 0);
		assertEqual("etsiKulmat straight up", 90, ang[0], 0.1);
		assertEqual("etsiKulmat straight up", 0, ang[1], 0.1);

		p = new double[] { 0, -10 };
		ang = KineMath.etsikulmat(p, 5, 0);
		assertEqual("etsiKulmat straight down", -90, ang[0], 0.1);
		assertEqual("etsiKulmat straight down", 0, ang[1], 0.1);
		p = new double[] { -10, 0 };
		ang = KineMath.etsikulmat(p, 5, 0);
		assertEqual("etsiKulmat straight left", -180, ang[0], 0.1);
		assertEqual("etsiKulmat straight left", 360, ang[1], 0.1);
		p = new double[] { 10, 0 };
		ang = KineMath.etsikulmat(p, 5, 0);
		assertEqual("etsiKulmat straight right", 0, ang[0], 0.1);
		assertEqual("etsiKulmat straight right", 0, ang[1], 0.1);

		p = new double[] { 5, 5 };
		ang = KineMath.etsikulmat(p, 5, 5);
		assertEqual("etsiKulmat L up right", 90, ang[0], 0.1);
		assertEqual("etsiKulmat L up right'", -90, ang[1], 0.1);
		
		p = new double[] { 5, 5 };
		ang = KineMath.etsikulmat(p, 5, 5, new double[] {-1,1}, new double[] {89,91});
		assertEqual("etsiKulmat constrained L up right", 0, ang[0], 0.1);
		assertEqual("etsiKulmat constrained L up right'", 90, ang[1], 0.1);

		double armFromSwiwel = 4.2;
		double armFromOrigo = 6;

		double[] nearRange = { 0, 180 };
		double[] farRange = { -160, 90 };

		p = new double[] { 0, 4.2 };
		ang = KineMath.etsikulmat(p, armFromSwiwel, armFromOrigo, nearRange,
				farRange);
		assertEqual("Particular test case a", 135, ang[0], 1);
		assertEqual("Particular test case a", -135, ang[1], 1);

		armFromSwiwel = 15.5;
		armFromOrigo = Math.sqrt(17 * 17 + 4 * 4);

		nearRange = new double[] { -180, 180 };
		farRange = new double[] { -160, 150 };

		p = new double[] { 0, 7 };
		ang = KineMath.etsikulmat(p, armFromSwiwel, armFromOrigo, nearRange,
				farRange);
		assertEqual("Particular test case b", 155, ang[0], 1);
		assertEqual("Particular test case b", -155, ang[1], 1);

		double x = Math.sqrt(armFromOrigo * armFromOrigo + armFromSwiwel
				* armFromSwiwel);
		p = new double[] { x, x };
		ang = KineMath.etsikulmat(p, armFromSwiwel, armFromOrigo, nearRange,
				farRange);
		assertEqual("Particular test case d",  45, ang[0], 1);
		assertEqual("Particular test case d'", 0, ang[1], 1);
		
		p = new double[] { 0, 6 };
		ang = KineMath.etsikulmat(p, armFromSwiwel, armFromOrigo, nearRange,
				farRange);
		assertEqual("Particular test case e", 158, ang[0], 1);
		assertEqual("Particular test case e'",-158, ang[1], 1);
		
	}

	public void testBinSearchStraightUp() {
		double[] p = { 0, 10 };
		double dist = 5;
		double[] rp = KineMath.bestPointCW(p, dist, 0);
		assertEqual("bintest straight up", 0, rp[0], 0.1);
		assertEqual("bintest straight up", 5, rp[1], 0.1);
	}

	public void testBinSearchStraightDown() {
		double[] p = { 0, -10 };
		double dist = 5;
		double[] rp = KineMath.bestPointCW(p, dist, 0);
		assertEqual("bintest straight down", 0, rp[0], 0.1);
		assertEqual("bintest straight down", -5, rp[1], 0.1);
	}

	public void testBinSearchStraightRight() {
		double[] p = { 12, 0 };
		double dist = 5;
		double[] rp = KineMath.bestPointCW(p, dist, 0);
		assertEqual("bintest straight right", 12 - 5, rp[0], 0.1);
		assertEqual("bintest straight right", 0, rp[1], 0.1);
	}

	public void testBinSearchStraightLeft() {
		double[] p = { -12, 0 };
		double dist = 5;
		double[] rp = KineMath.bestPointCW(p, dist, 0);
		assertEqual("bintest straight left", -12 + 5, rp[0], 0.1);
		assertEqual("bintest straight left", 0, rp[1], 0.1);
	}

	public static void main(String[] args) {
		KineMathTest t = new KineMathTest();
		t.run();
	}

	public boolean equal(double d1, double d2, double tolerance) {
		return (Math.abs(d1 - d2) <= tolerance);
	}

	public void assertEqual(String testName, double d1, double d2,
			double tolerance) {
		if (!equal(d1, d2, tolerance))
			fail(testName, d1, d2);
	}

	public void fail(String testName, double expected, double got) {
		System.out.println("Failed test: " + testName + "; expected "
				+ expected + ", got " + got);
	}

	public void testAngleBetween00xx() {
		for (int i = 1; i < 100; i++) {
			double[] p1 = { 0, 0 };
			double[] p2 = { i, i };
			double result = Math.toDegrees(KineMath.angleBetween(p1, p2));
			assertEqual("Angle between (0,0) and (x,x)", 45, result, 0.1);
		}
	}

	public void testAngleBetweenxx00() {
		for (int i = 1; i < 100; i++) {
			double[] p1 = { i, i };
			double[] p2 = { 0, 0 };
			double result = Math.toDegrees(KineMath.angleBetween(p1, p2));
			assertEqual("Angle between (0,0) and (x,x)", -135, result, 0.1);
		}
	}

	public void testAngleBetween0x00() {
		for (int i = 1; i < 100; i++) {
			double[] p1 = { 0, i };
			double[] p2 = { 0, 0 };
			double result = Math.toDegrees(KineMath.angleBetween(p1, p2));
			assertEqual("Angle between (0,0) and (x,x)", -90, result, 0.1);
		}
	}

	public void armStraightToRight() {

	}
}
