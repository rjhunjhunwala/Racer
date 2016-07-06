package racer;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author rohan
 */
public class Player {
	public double x=15;
	public double y=15;
	public double angle = 0;
	public double dX;
	public double dY;
	public int getXBlock(){
		return (int) x;
	}
	public int getYBlock(){
		return (int) y;
	}
}
