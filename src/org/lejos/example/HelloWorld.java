package org.lejos.example;

import lejos.nxt.Button;
import lejos.nxt.Sound;

/**
 * Example leJOS Project with an ant build file
 * 
 */
public class HelloWorld {
	public static void main(String[] args) {

		Thread panicbutton = new Thread() {
			@Override
			public void run() {
				while(true)
				if (Button.ESCAPE.isPressed()) {
					Sound.beep();
					System.exit(0);
				}
			}
		};
		panicbutton.start();
		
//		NXTRegulatedMotor[] m = {Motor.A,Motor.B,Motor.C};
//		int i = 0;
//		while (!Button.ESCAPE.isPressed()) {
//			NXTRegulatedMotor s = m[i%3];
//			s.setSpeed(100);
//			if(Button.ENTER.isPressed()) 
//				s.stop();
//			if (Button.RIGHT.isPressed())
//				s.rotate(180*3);
//			if(Button.LEFT.isPressed())
//				s.rotate(-180*3);
//		}
//		System.exit(0);
		MotorControl m = new MotorControl();
		float s = 0.3f;
		for (int k = 5; k < 13; k++) {
			for (float i = 0; i < k; i += s) {
				m.moveTo(0, i);
				while (m.isMoving())
					;
			}
			for (float i = 0; i < k; i += s) {
				m.moveTo(-i, k);
				while (m.isMoving())
					;
			}

			for (float i = 0; i < k; i += s) {
				m.moveTo(-k, k - i);
				while (m.isMoving())
					;
			}

			for (float i = 0; i < k; i += s) {
				m.moveTo(-k + i, 0);
				while (m.isMoving())
					;
			}
		}

		m.moveTo(0, 0);
		while (m.isMoving())
			;
		Sound.beep();
		System.exit(0);
//		while (!Button.ENTER.isPressed()) {
//			System.out.println(m.toString());
//			Delay.msDelay(100);
//		}
	}
}
