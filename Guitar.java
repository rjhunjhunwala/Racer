package racer;

import java.util.logging.Level;
import java.util.logging.Logger;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author rohan
 */
public class Guitar extends Instrument {


public static final int PACE=3;
	public Guitar() {
		channel = 1;
		duration = new Instrument().duration/PACE;
		volume = 40;
	}

	@Override
	public void run() {
		while (true) {
			int time = duration;
			for (int i = 0; i < 12; i++) {
				try {
				for(int j=0;j<PACE;j++){
					int note = Instrument.blues[i] + Instrument.key -12;
					playNotes(note);
					Thread.sleep(duration);
					cutNotes();
					playNotes(note + ((int) (Math.random()*2)==0?2:9));
					Thread.sleep(duration);
					cutNotes();
					playNotes(note + (((int) (Math.random()*2)==0)?4:7));
					Thread.sleep(duration);
					cutNotes();
					note = Instrument.blues[(i + 1) % 12] + key+((((int) (Math.random()*2))==0?(((int) (Math.random()*2))==0?7:5):((int) (Math.random()*2))==0?-1:5));
					playNotes(note);
					Thread.sleep(duration);
					cutNotes();
				}
				} catch (Exception ex) {
					System.exit(-55);
				}
			}
		}
	}
}
