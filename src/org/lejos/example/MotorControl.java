package org.lejos.example;

import lejos.nxt.Button;
import lejos.nxt.MotorPort;
import lejos.nxt.NXTRegulatedMotor;

public class MotorControl {

	NXTRegulatedMotor mnear;
	NXTRegulatedMotor mfar;
	int nearspeed = 200;
	int farspeed = 200;
	int gearRatio = 3;
	
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
		mnear.rotateTo(-(int)(near * gearRatio), true);
		mfar.rotateTo(-(int)(far * gearRatio), immediateReturn);
	}
	
	public void moveTo(float x, float y, boolean immediateReturn) {
		double[] angles = KineMath.etsikulmat(x, y);
		rotateTo(angles[0], angles[1], immediateReturn);
	}
	public boolean isMoving() {
		return mnear.isMoving() || mfar.isMoving();
	}
}
