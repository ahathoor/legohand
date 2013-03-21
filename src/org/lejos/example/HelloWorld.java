package org.lejos.example;

import lejos.nxt.Button;
import lejos.util.Delay;


/**
 * Example leJOS Project with an ant build file
 * 
 */
public class HelloWorld {

	public static void main(String[] args) {
		MotorControl m = new MotorControl();
		m.rotateTo(-90, -130);
		while(m.isMoving());
		m.rotateTo(0, 0);
		while(m.isMoving());
		m.rotateTo(-90, -130);
		while(m.isMoving());
		m.rotateTo(0, 0);
		while(m.isMoving());
		m.rotateTo(-90, -130);
		while(m.isMoving());
		m.rotateTo(0, 0);
		while(m.isMoving());
		m.rotateTo(-90, -130);
		while(m.isMoving());
		m.rotateTo(0, 0);
		while(m.isMoving());
		while(!Button.ENTER.isPressed()) {
			System.out.println(m.toString());
			Delay.msDelay(100);
		}
	}
}
