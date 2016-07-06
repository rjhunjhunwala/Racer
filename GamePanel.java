package racer;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.ConcurrentModificationException;
import java.util.concurrent.atomic.AtomicReference;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.JPanel;

public final class GamePanel extends JPanel {

	final static int screenheight;
	static final int screenlength;

	/**
	 * The following static block is used courtesy of stack overflow creative
	 * commons liscence
	 * http://stackoverflow.com/questions/3680221/how-can-i-get-the-monitor-size-in-java
	 */
	static {
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		double width = screenSize.getWidth();
		double height = screenSize.getHeight();
		screenheight = (int) height + 2;
//screenheight=768;		
screenlength = (int) width +2;
	//screenlength=1024;
	}
	
	/**
	 * Coordinate of the midpoint of the axis
	 */
	final static int middle = screenlength / 2;
	/**
	 * Y coordinate of the horizon
	 */
	final static int horizon = screenheight / 2;
 public static final double fov = screenlength/768.0;
	public static final Font monospacedNormal = new Font("Monospaced", 18, 12);
	final static double[] anglesOff;

	static {
		anglesOff = new double[screenlength];
		for (int i = 0; i < screenlength; i++) {
			anglesOff[i] = Math.atan(((middle - i) / (((screenheight/768.0))*1000.0)) / (fov));
		}
        for(double i:anglesOff){
            System.out.println(i);
        }
	}
	final static double[] fishEyeCorrection;

	static {
		fishEyeCorrection = new double[screenlength];
		for (int i = 0; i < screenlength; i++) {
			fishEyeCorrection[i] = Math.cos(anglesOff[i]);
		}
	}

public static final int pixelSize=screenheight/Texture.size;

public static Player p;

	/**
	 * Creates a new GamePanel as well as spawn the starting items
	 *
	 * environment
	 * @throws IOException
	 */
	public GamePanel() throws IOException {
	p = new Player();
		this.setBackground(Color.BLACK);
	}

	@Override
	public Dimension getPreferredSize() {
		return new Dimension(screenlength, screenheight);
	}
	public static BufferedImage wall;
	static{
		try{
			wall=ImageIO.read(new File("wall.gif"));
		}catch(Exception ex){}
	}
	public static final Texture wallT=new Texture(wall);
public static final int[][] map= file.getMapFromFile("map.txt");
static{
	for(int[] m:map){
		System.out.println(Arrays.toString(m));
	}
}
	public static final Color DARK_GREEN = new Color(13, 130, 47);
	public static final Color DARK_BLUE = new Color(43, 141, 173);
public void minimap(Graphics g){
	for(int i=0;i<map.length;i++){
		for(int j=0;j<map[i].length;j++){
			g.setColor(map[i][j]==1?Color.red:Color.BLUE);
			g.fillRect(i*16, j*16, 16, 16);
		}
	}
		g.setColor(Color.yellow);
g.fillRect((int) (p.x*16)-3, (int) (p.y*16)-3, 6, 6);
g.setColor(Color.green);
g.drawLine((int) (p.x*16), (int) (p.y*16),(int) (((p.x*16))+Math.cos(p.angle)*5) , (int) (((p.y*16))-Math.sin(p.angle)*5));
}
	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		minimap(g);

		g.fillRect(0, horizon, screenlength, horizon);
  g.setColor(Color.blue);
		g.fillRect(0, 0, screenlength, horizon);
		for (int i = 0; i < screenlength; i++) {
			double rayX = p.x;
			double rayY = p.y;
			double startX = rayX;
			double startY = rayY;
			double angle = p.angle + anglesOff[i];
			for (double rayDist = 0; rayDist < 60; rayDist += getReasonableStep(rayX, rayY, angle)) {
				rayX = (startX + Math.cos(angle) * (rayDist));
				rayY = (startY - Math.sin(angle) * (rayDist));
				int block = map[(int) rayX][(int) rayY];
				if (block != 0) {

					//rayDist+=1;
					int lineHeight = (int) (screenheight / (rayDist) / fishEyeCorrection[i] / (1.2));
					if (lineHeight > 3*screenheight) {
						lineHeight = 3*screenheight;
//kludge
					}
					Texture textured = wallT;

					Color[] texture = textured.texture[(lineHeight * Texture.size) / screenheight - 1][pixelLineOntexture(rayX, rayY)];
					int start = horizon - lineHeight / 2;
					for (int pi = 0; pi < texture.length; pi++) {
						g.setColor(texture[pi]);
						g.drawLine(i, start + pixelSize * pi, i, start + pixelSize * pi + pixelSize);
					}
//g.setColor(Color.black);
					//g.drawLine(i, horizon-lineHeight/2, i, horizon+lineHeight/2);
					//System.out.println("Break");
					break;
				}
			}
		}

	

	//	minimap(g);
	}
	/**
	 * Some arbitrarily small value, the smaller the better
	 */
	static final double e = .00001;

	/**
	 * gets a reasonable step ahead for the ray to make it more efficient
	 *
	 * @param rayX
	 * @param rayY
	 * @param angle
	 * @return
	 */
	public static double getReasonableStep(double rayX, double rayY, double angle) {
//        if(true)
//            return .01;
		angle %= 2 * Math.PI;
		angle += 2 * Math.PI;
		angle %= 2 * Math.PI;
		double yResidue;

		if (angle > Math.PI) {
			yResidue = Math.ceil(rayY + e) - rayY;
		} else {
			yResidue = rayY - Math.floor(rayY - e);
		}

		double xResidue;
		if (angle < Math.PI / 2 || angle > 3 * Math.PI / 2) {
			xResidue = Math.ceil(rayX + e) - rayX;
		} else {
			xResidue = rayX - Math.floor(rayX - e);
		}
		double actualYDist = Math.abs(yResidue / Math.sin(angle));
		double actualXDist = Math.abs(xResidue / Math.cos(angle));
		return actualYDist < actualXDist ? actualYDist + .005 : actualXDist + .005;
	}

	private int pixelLineOntexture(double rayX, double rayY) {
		//throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
		if (Math.abs((1 - ((rayY - .01) % 1))) <= .02) {
			return (int) ((rayX % 1) * Texture.size);
		} else {
			return (int) (((rayY % 1)) * Texture.size);
		}
	}

}
