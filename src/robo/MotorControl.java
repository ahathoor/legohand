package robo;

import lejos.nxt.Button;
import lejos.nxt.Motor;
import lejos.nxt.MotorPort;
import lejos.nxt.NXTRegulatedMotor;
import lejos.nxt.Sound;

public class MotorControl {

	NXTRegulatedMotor mnear;
	NXTRegulatedMotor mfar;
	int nearspeed = 300;
	int farspeed = 300;
	int gearRatio = 3;
	private boolean penUp;

	double armFromSwiwel = 11.3;
	double armFromOrigo = 13.4;
	
	double[] nearRange = {-180,180};
	double[] farRange = {-160,150};
	
	double[] xy = new double[2];

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

	public void rotateTo(double near, double far, boolean immediateReturn) {
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
}
