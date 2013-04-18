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
		for(int i=0;i<5;i++)
		hello(-5,12,m);
		
		lopeta(m);
	}
	
	public static void hello(float x, float y, MotorControl m) {
		for (float[] line : hello) {
			float x1 = x + line[0];
			float y1 = y + line[1];
			float x2 = x + line[2];
			float y2 = y + line[3];
			drawLine(m, x1, y1, x2, y2, 0.5f);
		}
	}
	
	public static void sisakkLaatikotDemo(MotorControl m) {
		for(int i = 0; i<5; i++)
	 		drawRect(m, 0+i, 10+i, 15-i, 20-i, 0.5f);	
	}
	
	public static void lopeta(MotorControl m) {
		m.liftPen();
		m.rotateTo(0, 0, false);
		m.lowerPen();
		System.exit(0);
	}
	
	private static double dist(double x1, double y1, double x2, double y2) {
		return Math.sqrt((x1-x2)*(x1-x2) + (y1-y2)*(y1-y2));
	}
	public static void drawLine(MotorControl m, float x1, float y1, float x2, float y2, float step) {
		if (dist(m.getXy()[0], m.getXy()[1], x1, y1)<0.2) //jos lähtöpiste on kovin lähellä kynän nykyistä sijaintia
			;
		else
			m.liftPen();

		m.moveTo(x1, y1, false);
		m.lowerPen();
		
		float linelenght = (float) dist(x1,y1,x2,y2);
		int pieces = Math.round((float)Math.ceil(linelenght/step));
		
		float deltax = x2-x1;
		float deltay = y2-y1;
		
		float piecex = deltax / pieces;
		float piecey = deltay / pieces;
		
		for(int i=0; i<=pieces; i++) {
			m.moveTo(x1+piecex*i,y1+piecey*i,false);
		}
		
	}
	public static void drawRect(MotorControl m, float x1, float y1, float x2, float y2, float step) {
		drawLine(m,x1,y1,x1,y2, step);
		drawLine(m,x1,y2,x2,y2, step);
		drawLine(m,x2,y2,x2,y1, step);
		drawLine(m,x2,y1,x1,y1, step);
	}
}
