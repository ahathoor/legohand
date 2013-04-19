package robo;

import lejos.nxt.Button;
import lejos.nxt.Sound;

public class RoboHand {

	static float[][] hello = 
	{{0,0,0,6},
	{0,3,2,3},
	{2,0,2,6},

	{3,0,3,6},
	{3,0,5,0},
	{3,3,5,3},
	{3,6,5,6},

	{6,6,6,0},
	{6,0,8,0},

	{9,6,9,0},
	{9,0,11,0},

	{12,0,12,6},
	{12,6,15,6},
	{15,6,15,0},
	{15,0,12,0}};
	
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
		
		MonaLisa.draw(m, 0, 11);
		m.lopeta();
		m.drawLinemap(hello, -7,10);
		m.drawRect(-8, 11, -8+17, 11+8 , 0.5f);
		m.lopeta();
	}
	public static void sisakkLaatikotDemo(MotorControl m) {
		for(int i = 0; i<5; i++)
	 		m.drawRect(0+i, 10+i, 15-i, 20-i, 0.5f);	
	}
}
