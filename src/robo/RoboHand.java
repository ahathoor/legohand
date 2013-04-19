package robo;

import pictures.LineMap;
import pictures.PolyArrays;
import lejos.nxt.Button;
import lejos.nxt.Sound;

public class RoboHand {

	
	public static void main(String[] args){
		
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
		
		m.drawPolyArray(PolyArrays.MonaLisa, -7, 10);
		m.lopeta();
		m.drawLinemap(LineMap.hello, -7,10);
		m.drawRect(-8, 11, -8+17, 11+8 , 0.5f);
		m.lopeta();
	}
	public static void sisakkLaatikotDemo(MotorControl m) {
		for(int i = 0; i<5; i++)
	 		m.drawRect(0+i, 10+i, 15-i, 20-i, 0.5f);	
	}
}
