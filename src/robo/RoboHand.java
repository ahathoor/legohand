package robo;

import lejos.nxt.Button;
import lejos.nxt.Sound;

/**
 * Example leJOS Project with an ant build file
 * 
 */
public class RoboHand {
	public static void main(String[] args) {

		Thread panicbutton = new Thread() {
			@Override
			public void run() {
				while (true)
					if (Button.ESCAPE.isPressed()) {
						Sound.beep();
						System.exit(0);
					}
			}
		};
		panicbutton.start();

		MotorControl m = new MotorControl();
		// m.rotateTo(45, 45, false);
		// m.rotateTo(0,0,false);
		// System.exit(0);
		for (int i = 0; i < 10; i++) {
			m.moveTo(0, 25-i, false);
			m.moveTo(25-i, 0, false);
		}
		m.rotateTo(0, 0, false);

	}
}
