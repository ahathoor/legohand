package robo;

import lejos.nxt.Sound;

public class SoundSystem {

	public static int F5 = 698;
	public static int Db5 = 554;
	public static int Eb5 = 622;
	public static int C4 = 262;
	public static int G4 = 392;
	public static int A4 = 440;
	public static int C5 = 523; 
	
	public static void note(int note) {
		Sound.playNote(Sound.FLUTE, note, 1000);
	}
	
	public static void fanfare() {
		Sound.playNote(Sound.FLUTE, F5, 120);
		Sound.playNote(Sound.FLUTE, F5, 120);
		Sound.playNote(Sound.FLUTE, F5, 120);
		
		Sound.playNote(Sound.FLUTE, F5, 360);
		Sound.playNote(Sound.FLUTE, Db5, 360);
		Sound.playNote(Sound.FLUTE, Eb5, 360);
		
		Sound.playNote(Sound.FLUTE, F5, 120*2);

		Sound.playNote(Sound.FLUTE, Eb5, 120);
		Sound.playNote(Sound.FLUTE, F5, 360*3);
	}
}
