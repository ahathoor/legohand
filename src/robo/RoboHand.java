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
		m.moveTo(16,16,false);

		m.moveTo(28,28,false);
		m.rotateTo(0, 0, false);

	}
}
