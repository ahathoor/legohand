package org.lejos.example;

import lejos.nxt.Button;
import lejos.nxt.LCD;
import lejos.nxt.Motor;
import lejos.nxt.NXTRegulatedMotor;
import lejos.nxt.Sound;
import lejos.nxt.comm.Bluetooth;
import lejos.nxt.comm.NXTCommConnector;
import lejos.nxt.comm.NXTConnection;
import lejos.nxt.comm.RS485;
import lejos.nxt.comm.USB;
import lejos.util.Delay;

/**
 * Example leJOS Project with an ant build file
 * 
 */
public class HelloWorld {

	public static void main(String[] args) {
		while (!Button.ESCAPE.isPressed()) {
			if (Button.ENTER.isPressed())
				Motor.C.backward();
			else
				Motor.C.stop();
		}
		System.exit(0);
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
		while (!Button.ENTER.isPressed()) {
			System.out.println(m.toString());
			Delay.msDelay(100);
		}
	}
}
