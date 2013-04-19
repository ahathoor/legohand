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
	
	private int[] alkukulmat = {-180-50,165};
	
	private double[] xy = new double[2];

	/**
	 * Ajaa kynän alkuasentoonsa käyden ensin maksimiasennossa ja nollaa kynän takometrin
	 */
	public void calibrate_pen() {
		while(Math.abs(mpen.getPosition()-mpen.getTachoCount())<5) {
			mpen.rotate(-20);
		}
		SoundSystem.note(SoundSystem.C4);
		mpen.rotate(140);
		mpen.resetTachoCount();
		penUp = true;
	}
	
	private void gotoStartAngles() {

		mnear.setSpeed(100);
		mfar.setSpeed(100);
		while(Math.abs(mnear.getPosition()-mnear.getTachoCount())<5) {
			mnear.rotate(-20);
		}

		SoundSystem.note(SoundSystem.G4);

		while(Math.abs(mfar.getPosition()-mfar.getTachoCount())<3) {
			mfar.rotate(3);
		}

		SoundSystem.note(SoundSystem.C5);
	}
	private void calibrate_motor() {
		gotoStartAngles();
		
		mnear.setSpeed(nearspeed);
		mfar.setSpeed(farspeed);
		mnear.setAcceleration(600);
		mnear.setAcceleration(600);
		

		mnear.resetTachoCount();
		mfar.resetTachoCount();
		liftPen();
		mfar.rotateTo(-alkukulmat[1]*gearRatio,true);
		mnear.rotateTo(-alkukulmat[0]*gearRatio,false);
		mnear.resetTachoCount();
		mfar.resetTachoCount();
	}
	/**
	 * Luo uuden moottorin
	 * @param near lähimmän moottorin portti
	 * @param far kauemman moottorin portti
	 * @param pen kynän nostajan portti
	 */
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
	/**
	 * Nostaa kynän
	 */
	public void liftPen() {
		if (!penUp)
			mpen.rotateTo(0,false);
		penUp = true;
	}
	/**
	 * Laskee kynän
	 */
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
		SoundSystem.fanfare();
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
	 * Draws a polygon p to coordinates (x,y)
	 * @param p
	 * @param x
	 * @param y
	 */
	public void drawPolygon(Polygon p, float x, float y) {
		float[][] points = p.getPoints();
		for(int i = 0; i<points.length; i++) {
			float[] p1 = points[i];
			float[] p2;
			if (i == points.length-1)
				p2 = points[0];
			else
				p2 = points[i+1];
			float scale = 0.3f;
			drawLine(x+p1[0]*scale,y+p1[1]*scale, x+p2[0]*scale,y+p2[1]*scale, 0.5f);
		}
	}
	public void drawPolyArray(Polygon[] pc, float x, float y) {
		for (Polygon p : pc)  {
			drawPolygon(p, x, y);
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
