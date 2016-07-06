/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package racer;

import java.io.IOException;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author rohan
 */
public class Racer {
public static final AtomicBoolean playing=new AtomicBoolean(false);
public static GameFrame g;
/**
		* Plays one racing game
	 * @param args the command line arguments
	 */
	public static void main(String... args) {

		try {
 g = new GameFrame();
	} catch (IOException ex) {
		Logger.getLogger(Racer.class.getName()).log(Level.SEVERE, null, ex);
	}
		playing.set(true);
		
		while(playing.get()){
			g.repaint();
		}
		g.setVisible(false);
	}
	
}
