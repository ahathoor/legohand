package robo;

import lejos.nxt.Button;
import lejos.nxt.Motor;
import lejos.nxt.MotorPort;
import lejos.nxt.NXTRegulatedMotor;
import lejos.nxt.Sound;

public class MotorControl {

	private NXTRegulatedMotor mnear;
	private NXTRegulatedMotor mfar;
	private int nearspeed = 100;
	private int farspeed = 100;
	private int gearRatio = 3;
	private boolean penUp;

	private double armFromSwiwel = 11.3;
	private double armFromOrigo = 13.4;
	
	private double[] nearRange = {-180,180};
	private double[] farRange = {-160,150};
	
	private double[] xy = new double[2];

	public MotorControl(MotorPort near, MotorPort far) {
		mnear = new NXTRegulatedMotor(near);
		mfar = new NXTRegulatedMotor(far);
		mnear.setSpeed(nearspeed);
		mfar.setSpeed(farspeed);
	}

	public MotorControl() {
		this(MotorPort.A, MotorPort.C);
	}
	
	public void liftPen() {
		if (!penUp)
			Motor.B.rotateTo(-200);
		penUp = true;
	}
	public void lowerPen() {
		if (penUp)
			Motor.B.rotateTo(0);
		penUp = false;
	}	
	
	public double[] getXy() {
		return xy;
	}

	private void rotateTo(double near, double far, boolean immediateReturn) {
		mnear.rotateTo(-(int) (near * gearRatio), true);
		mfar.rotateTo(-(int) (far * gearRatio), immediateReturn);
	}

	public void moveTo(float x, float y, boolean immediateReturn) {
		double[] a = KineMath.etsikulmat(new double[] { x, y }, armFromSwiwel,
				armFromOrigo, nearRange, farRange);
		if (Double.isNaN(a[0]) || Double.isNaN(a[1])) {
			System.out.println("Point (" + x + "," + y + ") not reachable!");
			Sound.beep();
			Button.waitForPress();
		} else {
			rotateTo(a[0], a[1], immediateReturn);
			xy[0] = x;
			xy[1] = y;
		}
	}

	public boolean isMoving() {
		return mnear.isMoving() || mfar.isMoving();
	}
	
	public void drawLinemap(float[][] linemap, float x, float y) {
		for (float[] line : linemap) {
			float x1 = x + line[0];
			float y1 = y + line[1];
			float x2 = x + line[2];
			float y2 = y + line[3];
			drawLine(x1, y1, x2, y2, 0.5f);
		}
	}
	
	public void lopeta() {
		liftPen();
		rotateTo(0, 0, false);
		lowerPen();
		System.exit(0);
	}
	
	private static double dist(double x1, double y1, double x2, double y2) {
		return Math.sqrt((x1-x2)*(x1-x2) + (y1-y2)*(y1-y2));
	}
	/**
	 * 
	 * @param m 
	 * @param x1 
	 * @param y1
	 * @param x2
	 * @param y2
	 * @param maxSegment how long the 
	 */
	public void drawLine(float x1, float y1, float x2, float y2, float maxSegment) {
		if (dist(getXy()[0], getXy()[1], x1, y1)<0.2) //jos lähtöpiste on kovin lähellä kynän nykyistä sijaintia
			;
		else
			liftPen();

		moveTo(x1, y1, false);
		lowerPen();
		
		float linelenght = (float) dist(x1,y1,x2,y2);
		int pieces = Math.round((float)Math.ceil(linelenght/maxSegment));
		if (pieces < 1)
			pieces = 1;
		
		float deltax = x2-x1;
		float deltay = y2-y1;
		
		float piecex = deltax / pieces;
		float piecey = deltay / pieces;
		
		for(int i=0; i<=pieces; i++) {
			moveTo(x1+piecex*i,y1+piecey*i,false);
		}
		
	}
	public void drawRect(float x1, float y1, float x2, float y2, float step) {
		drawLine(x1,y1,x1,y2, step);
		drawLine(x1,y2,x2,y2, step);
		drawLine(x2,y2,x2,y1, step);
		drawLine(x2,y1,x1,y1, step);
	}
}
