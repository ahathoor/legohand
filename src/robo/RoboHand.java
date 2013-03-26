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
 		drawRect(m, 0, 5+5, 2, 11+5, 0.5f);
 		drawRect(m, 0, 7+5, 6, 9+5, 0.5f);
 		drawRect(m, 4, 5+5, 6, 11+5, 0.5f);
 		drawRect(m, 8, 5+5, 10, 11+5, 0.5f);
				
		m.liftPen();
		m.rotateTo(0, 0, false);
		m.lowerPen();
		System.exit(0);
	}
	public static void drawRect(MotorControl m, float x1, float y1, float x2, float y2, float step) {
		m.liftPen();
		m.moveTo(x1, y1, false);
		m.lowerPen();
		for (float i = 0; y1+i <= y2; i += step)
			m.moveTo(x1, y1 + i, false);
		for (float i = 0; x1+i <= x2; i += step)
			m.moveTo(x1 + i, y2 , false);

		for (float i = 0; i <= y2-y1; i += step)
			m.moveTo(x2, y2 - i, false);

		for (float i = 0; i <= x2-x1; i += step)
			m.moveTo(x2-i, y1, false);
	}
}
