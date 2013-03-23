package org.lejos.example;

import lejos.nxt.MotorPort;
import lejos.nxt.NXTRegulatedMotor;

public class MotorControl {

	NXTRegulatedMotor mnear;
	NXTRegulatedMotor mfar;
	int nearspeed = 200;
	int farspeed = 200;
	
	double[] nollat = {Math.cos(Math.PI/4) * 16 - 15, Math.sin(Math.PI / 4) * 16};
	
	public MotorControl(MotorPort near, MotorPort far) {
		mnear = new NXTRegulatedMotor(near);
		mfar = new NXTRegulatedMotor(far);
		mnear.setSpeed(nearspeed);
		mfar.setSpeed(farspeed);
	}
	
	public MotorControl() {
		this(MotorPort.B, MotorPort.C);
	}
	
	public void rotateTo(int near, int far) {
//		int ns = Math.abs(mnear.getPosition() - near);
//		int fs = Math.abs(mfar.getPosition() - far);
//		mnear.setSpeed(ns);
//		mfar.setSpeed(fs);
		mnear.rotateTo(near, true);
		mfar.rotateTo(far, true);
	}
	
	public void moveTo(float x, float y) {
		double[] angles = KineMath.math(x, y);
		rotateTo((int)angles[0], (int)angles[1]);
	}
	public boolean isMoving() {
		return mnear.isMoving() || mfar.isMoving();
	}
	@Override
	public String toString() {
		return "near: " + mnear.getTachoCount() + " \n far: " + mfar.getTachoCount();
	}
}
