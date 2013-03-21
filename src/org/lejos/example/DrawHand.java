package org.lejos.example;


/**
 * Example leJOS Project with an ant build file
 * 
 */
public class DrawHand {

	public static void main(String[] args) {
		MotorControl m = new MotorControl();
		m.rotateTo(20, -20);
		while(m.isMoving());
	}
}
