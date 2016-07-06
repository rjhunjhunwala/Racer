/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package racer;

import java.util.logging.Level;
import java.util.logging.Logger;
import javax.sound.midi.MidiChannel;
import javax.sound.midi.MidiSystem;
import javax.sound.midi.MidiUnavailableException;
import javax.sound.midi.Synthesizer;

/**
 *
 * @author rohan
 */
public class Instrument implements Runnable {

	public static final int MiddleC = 60;
	public static final int[] blues = {0, 5, 0, 0, 5, 5, 0, 0, 7, 5, 0, 2};

	static final int key = MiddleC + 12;//+(int) (Math.random()*25);  
	int channel = 0; // 0 is a piano, 9 is percussion, other channels are for other instruments
	int volume = 35; // between 0 et 127
	int duration = 600; // in milliseconds per quarter note
	static Synthesizer synth;

	static {
		try {
			synth = MidiSystem.getSynthesizer();
		} catch (Exception ex) {
			//problem?
		}
	}

	static {
		try {
			synth.open();
		} catch (MidiUnavailableException ex) {
		}
	}
	static MidiChannel[] channels = synth.getChannels();

	/**
	 * @param args the command line arguments
	 */
	public static void main(String... args) {
		new Thread(new Runnable(){
		@Override
		public void run(){
			try {
				Thread.sleep(1000);
			} catch (InterruptedException ex) {
				Logger.getLogger(Instrument.class.getName()).log(Level.SEVERE, null, ex);
			}
new Thread(new Instrument()).start();
		new Thread(new Guitar()).start();
		}
		}).start();
		}

	public void playNotes(int... notes) {
		for (int note : notes) {
			channels[channel].noteOn(note, volume);
		}
	}

	public void cutNotes() {
		channels[channel].allNotesOff();
	}

	@Override
	public void run() {
		while (true) {
			for (int i = 0; i < 12; i++) {
				try {
					int off = blues[i];
					playNotes(key + off, key + off + 4, key + off + 7);
					Thread.sleep(duration);
					//playNotes(key+off);
					Thread.sleep(duration);
					//playNotes(key+off+4);
					Thread.sleep(duration);
					//playNotes(key+off+7);
					Thread.sleep(duration);
					cutNotes();
				} catch (Exception ex) {
				}
			}
		}
	}
}
