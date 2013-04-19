package robo;

import lejos.nxt.Button;
import lejos.nxt.MotorPort;
import lejos.nxt.NXTRegulatedMotor;
import lejos.nxt.Sound;

public class MotorControl {

	private NXTRegulatedMotor mnear;
	private NXTRegulatedMotor mfar;
	private NXTRegulatedMotor mpen;
	private int nearspeed = 300;
	private int farspeed = 300;
	private int gearRatio = 3;
	private boolean penUp = false;

	private double armFromSwiwel = 11.6;
	private double armFromOrigo = 13.4;
	
	private double[] nearRange = {0,220};
	private double[] farRange = {-150,160};
	
	private int[] alkukulmat = {-180-40,160};
	
	private double[] xy = new double[2];

	
	public void calibrate_pen() {
		while(Math.abs(mpen.getPosition()-mpen.getTachoCount())<5) {
			mpen.rotate(-20);
		}
		Sound.beep();
		mpen.rotate(170);
		mpen.resetTachoCount();
		penUp = true;
	}
	
	public void gotoStartAngles() {

		mnear.setSpeed(100);
		mfar.setSpeed(100);
		while(Math.abs(mnear.getPosition()-mnear.getTachoCount())<5) {
			mnear.rotate(-20);
		}

		while(Math.abs(mfar.getPosition()-mfar.getTachoCount())<3) {
			mfar.rotate(8);
		}
	}
	public void calibrate_motor() {
//		gotoStartAngles();
		
		mnear.setSpeed(nearspeed);
		mfar.setSpeed(farspeed);
		mnear.setAcceleration(600);
		mnear.setAcceleration(600);
		

		mnear.resetTachoCount();
		mfar.resetTachoCount();
		liftPen();
		mnear.rotateTo(-alkukulmat[0]*gearRatio,true);
		mfar.rotateTo(-alkukulmat[1]*gearRatio,false);
		mnear.resetTachoCount();
		mfar.resetTachoCount();
	}
	public MotorControl(MotorPort near, MotorPort far, MotorPort pen) {
		mnear = new NXTRegulatedMotor(near);
		mfar = new NXTRegulatedMotor(far);
		mpen = new NXTRegulatedMotor(pen);
		
		calibrate_pen();
		calibrate_motor();
	}

	public MotorControl() {
		this(MotorPort.A, MotorPort.C, MotorPort.B);
	}
	
	public void liftPen() {
		if (!penUp)
			mpen.rotateTo(0,false);
		penUp = true;
	}
	public void lowerPen() {
		while(mnear.isMoving() || mfar.isMoving())
			;
		if (penUp)
			mpen.rotateTo(230,false);
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
	
	/**
	 * Siirtää kynän alkukulmaan ja lopettaa suorituksen
	 */
	public void lopeta() {
		liftPen();
		rotateTo(-alkukulmat[0], -alkukulmat[1], false);
		System.exit(0);
	}
	
	private static double dist(double x1, double y1, double x2, double y2) {
		return Math.sqrt((x1-x2)*(x1-x2) + (y1-y2)*(y1-y2));
	}
	/**
	 * Piirtää viivan pisteestä (x1,y1) pisteeseen (x2,y2)
	 * @param m 
	 * @param x1 
	 * @param y1
	 * @param x2
	 * @param y2
	 * @param maxSegment enintään kuinka pitkän liikkeen kerrallaan viiva piirretään
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
		
		float deltax = x2-x1;
		float deltay = y2-y1;
		
		float piecex = deltax / pieces;
		float piecey = deltay / pieces;
		
		for(int i=1; i<=pieces; i++) {
			moveTo(x1+piecex*i,y1+piecey*i,false);
		}
		
	}
	
	/** 
	 * Piirtää suorakaiteen pisteestä (x1,y1) pisteeseen (x2,y2) käyttäen enintään step -pituisia viivoja
	 * @param x1
	 * @param y1
	 * @param x2
	 * @param y2
	 * @param step
	 */
	public void drawRect(float x1, float y1, float x2, float y2, float step) {
		drawLine(x1,y1,x1,y2, step);
		drawLine(x1,y2,x2,y2, step);
		drawLine(x2,y2,x2,y1, step);
		drawLine(x2,y1,x1,y1, step);
	}
}
