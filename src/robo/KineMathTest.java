package robo;

public class KineMathTest {

//	KineMath k = new KineMath();
	
	public void run() {
		testAngleBetweenxx00();
		testAngleBetween00xx();
		testAngleBetween0x00();
		testBinSearchStraightUp();
		testBinSearchStraightDown();
		testBinSearchStraightLeft();
		testBinSearchStraightRight();
	}
	public void testBinSearchStraightUp() {
		double[] p = {0,10};
		double dist = 5;
		double[] rp = KineMath.binSearchPointWithDist(p, dist);
		assertEqual("bintest straight up", 0, rp[0], 0.1);
		assertEqual("bintest straight up", 5, rp[1], 0.1);
	}

	public void testBinSearchStraightDown() {
		double[] p = {0,-10};
		double dist = 5;
		double[] rp = KineMath.binSearchPointWithDist(p, dist);
		assertEqual("bintest straight down", 0, rp[0], 0.1);
		assertEqual("bintest straight down", -5, rp[1], 0.1);
	}
	public void testBinSearchStraightRight() {
		double[] p = {12, 0};
		double dist = 5;
		double[] rp = KineMath.binSearchPointWithDist(p, dist);
		assertEqual("bintest straight right", 12-5, rp[0], 0.1);
		assertEqual("bintest straight right", 0, rp[1], 0.1);
	}
	public void testBinSearchStraightLeft() {
		double[] p = {-12, 0};
		double dist = 5;
		double[] rp = KineMath.binSearchPointWithDist(p, dist);
		assertEqual("bintest straight left", -12+5, rp[0], 0.1);
		assertEqual("bintest straight left", 0, rp[1], 0.1);
	}
	public static void main(String[] args) {
		KineMathTest t = new KineMathTest();
		t.run();
	}
	public boolean equal(double d1, double d2, double tolerance) {
		return (Math.abs(d1-d2) <= tolerance);
	}
	public void assertEqual(String testName, double d1, double d2, double tolerance) {
		if (!equal(d1,d2,tolerance))
			fail(testName, d1, d2);
	}
	public void fail(String testName, double expected, double got) {
		System.out.println("Failed test: " + testName + "; expected " + expected + ", got " + got);
	}
	public void testAngleBetween00xx() {
		for (int i = 1; i<100; i++) {
			double[] p1 = {0,0};
			double[] p2 = {i,i};
			double result = Math.toDegrees(KineMath.angleBetween(p1,p2));
			assertEqual("Angle between (0,0) and (x,x)", 45, result, 0.1);
		}
	}
	public void testAngleBetweenxx00() {
		for (int i = 1; i<100; i++) {
			double[] p1 = {i,i};
			double[] p2 = {0,0};
			double result = Math.toDegrees(KineMath.angleBetween(p1,p2));
			assertEqual("Angle between (0,0) and (x,x)", -135, result, 0.1);
		}
	}

	public void testAngleBetween0x00() {
		for (int i = 1; i<100; i++) {
			double[] p1 = {0,i};
			double[] p2 = {0,0};
			double result = Math.toDegrees(KineMath.angleBetween(p1,p2));
			assertEqual("Angle between (0,0) and (x,x)", -90, result, 0.1);
		}
	}
	
	public void armStraightToRight() {
		
	}
}
