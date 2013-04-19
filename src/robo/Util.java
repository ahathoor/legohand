package robo;

import lejos.nxt.Sound;

public class Util {
	public static void fanfare() {
		int F = 698;
		int Db = 554;
		int Eb = 622;
		Sound.playNote(Sound.FLUTE, F, 120);
		Sound.playNote(Sound.FLUTE, F, 120);
		Sound.playNote(Sound.FLUTE, F, 120);
		
		Sound.playNote(Sound.FLUTE, F, 360);
		Sound.playNote(Sound.FLUTE, Db, 360);
		Sound.playNote(Sound.FLUTE, Eb, 360);
		
		Sound.playNote(Sound.FLUTE, F, 120*2);

		Sound.playNote(Sound.FLUTE, Eb, 120);
		Sound.playNote(Sound.FLUTE, F, 360*3);
	}
}
