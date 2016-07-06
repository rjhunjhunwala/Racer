/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package racer;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author rohan
 */
public class Game implements Runnable{
	public static final int FPS=50;//divisor of 1000
	@Override
	public void run(){
	while(Racer.playing.get()){
		try {
			Thread.sleep(1000/FPS);
		} catch (InterruptedException ex) {
			Logger.getLogger(Game.class.getName()).log(Level.SEVERE, null, ex);
		}
		
	}
	}
}
