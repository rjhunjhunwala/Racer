/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package racer;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

/**
 *
 * @author rohan
 */
public class Controller implements KeyListener{

	@Override
	public void keyTyped(KeyEvent e) {

	}

	@Override
	public void keyPressed(KeyEvent e) {
switch(e.getKeyChar()){
	case 'w':
	case 'W':
		GamePanel.p.x+=.1*Math.cos(GamePanel.p.angle);
		GamePanel.p.y-=.1*Math.sin(GamePanel.p.angle);
		break;
			case 's':
	case 'S':
		GamePanel.p.x-=.1*Math.cos(GamePanel.p.angle);
		GamePanel.p.y+=.1*Math.sin(GamePanel.p.angle);
		break;
	case 'd':
	case 'D':
		GamePanel.p.angle-=Math.PI/24;
break;
			case 'a':
	case 'A':
		GamePanel.p.angle+=Math.PI/24;
break;
}
	}

	@Override
	public void keyReleased(KeyEvent e) {
	
	}
	
}
