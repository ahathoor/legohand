package org.lejos.example;

import lejos.nxt.MotorPort;
import lejos.nxt.NXTRegulatedMotor;

public class MotorControl {

	NXTRegulatedMotor mnear;
	NXTRegulatedMotor mfar;
	int speed = 20;
	
	public MotorControl(MotorPort near, MotorPort far) {
		mnear = new NXTRegulatedMotor(near);
		mfar = new NXTRegulatedMotor(far);
		mnear.setSpeed(speed);
		mfar.setSpeed(speed);
	}
	
	public MotorControl() {
		this(MotorPort.B, MotorPort.C);
	}
	
	public void rotateTo(int near, int far) {
		mnear.rotateTo(near, true);
		mfar.rotateTo(far, true);
	}
	public boolean isMoving() {
		return mnear.isMoving() || mfar.isMoving();
	}
}
