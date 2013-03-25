package robo;

import lejos.nxt.Button;
import lejos.nxt.MotorPort;
import lejos.nxt.NXTRegulatedMotor;
import lejos.nxt.Sound;

public class MotorControl {

	NXTRegulatedMotor mnear;
	NXTRegulatedMotor mfar;
	int nearspeed = 200;
	int farspeed = 200;
	int gearRatio = 3;

	double armFromSwiwel = 16;
	double armFromOrigo = Math.sqrt(15 * 15 + 4 * 4);
	
	double[] nearRange = {-160,130};
	double[] farRange = {0,90};

	public MotorControl(MotorPort near, MotorPort far) {
		mnear = new NXTRegulatedMotor(near);
		mfar = new NXTRegulatedMotor(far);
		mnear.setSpeed(nearspeed);
		mfar.setSpeed(farspeed);
	}

	public MotorControl() {
		this(MotorPort.A, MotorPort.C);
	}

	public void rotateTo(double near, double far, boolean immediateReturn) {
		mnear.rotateTo(-(int) (near * gearRatio), true);
		mfar.rotateTo(-(int) (far * gearRatio), immediateReturn);
	}

	public void moveTo(float x, float y, boolean immediateReturn) {
		double[] a = KineMath.etsikulmat(new double[] { x, y }, armFromSwiwel,
				armFromOrigo);
		a = new double[] { KineMath.constrainAngle(a[0], nearRange[0], nearRange[1]),
				KineMath.constrainAngle(a[1], farRange[0], farRange[1]) };
		if (Double.isNaN(a[0]) || Double.isNaN(a[1])) {
			System.out.println("Point (" + x + "," + y + ") not reachable!");
			Sound.beep();
			Button.waitForPress();
		} else
			rotateTo(a[0], a[1], immediateReturn);
	}

	public boolean isMoving() {
		return mnear.isMoving() || mfar.isMoving();
	}
}
