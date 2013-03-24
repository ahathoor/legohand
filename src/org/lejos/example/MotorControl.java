package org.lejos.example;

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
	
	public void rotateTo(int near, int far, boolean immediateReturn) {
		mnear.rotateTo(near * gearRatio, true);
		mfar.rotateTo(far * gearRatio, immediateReturn);
	}
	
	public void moveTo(float x, float y, boolean immediateReturn) {
		x+=15; y+=15;
		double[] angles = KineMath.etsikulmat(x, y);
		rotateTo((int)(angles[0]*gearRatio), (int)(angles[1]*gearRatio), immediateReturn);
	}
	public boolean isMoving() {
		return mnear.isMoving() || mfar.isMoving();
	}
}
