package org.lejos.example;

import lejos.nxt.MotorPort;
import lejos.nxt.NXTRegulatedMotor;

public class MotorControl {

	NXTRegulatedMotor mnear;
	NXTRegulatedMotor mfar;
	int nearspeed = 40;
	int farspeed = 100;
	
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
		int ns = Math.abs(mnear.getPosition() - near);
		int fs = Math.abs(mfar.getPosition() - far);
		mnear.setSpeed(ns/2);
		mfar.setSpeed(fs/2);
		mnear.rotateTo(near, true);
		mfar.rotateTo(far, true);
	}
	public boolean isMoving() {
		return mnear.isMoving() || mfar.isMoving();
	}
	@Override
	public String toString() {
		return "near: " + mnear.getTachoCount() + " \t far: " + mfar.getTachoCount();
	}
}
