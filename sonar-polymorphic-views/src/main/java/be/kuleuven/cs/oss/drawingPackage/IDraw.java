/**
 * 
 */
package be.kuleuven.cs.oss.drawingPackage;

import java.awt.image.BufferedImage;


/**
 * @author Jonas
 *
 */
public interface IDraw {

	/**
	 * 
	 * @return
	 */
	public void createEmptyImage(int width, int heigth);

	public BufferedImage getBufferedImage();
	
	
	/**
	 * 
	 * @param im
	 * @param xCoord
	 * @param yCoord
	 * @param width
	 * @param heigth
	 * @param red
	 * @param green
	 * @param blue
	 * @return
	 */
	public void drawBox(
			int xCoord, int yCoord,
			int width, int heigth,
			int redBorder, int greenBorder, int blueBorder,
			int redFill, int greenFill, int blueFill,
			int borderWidth
			);
	/**
	 * 
	 * @param im
	 * @param xCoord
	 * @param yCoord
	 * @param width
	 * @param heigth
	 * @param red
	 * @param green
	 * @param blue
	 * @return
	 */
	public void drawCircle( 
			int xCoord, int yCoord,
			int width, int heigth,
			int redBorder, int greenBorder, int blueBorder,
			int redFill, int greenFill, int blueFill,
			int borderWidth
			);
	/**
	 * 
	 * @param im
	 * @param textToDraw
	 * @param xCoord
	 * @param yCoord
	 * @param orientationAngle
	 * @param red
	 * @param green
	 * @param blue
	 * @return
	 */
	public void drawText(
			String textToDraw,
			int xCoord, int yCoord,
			int orientationAngle,
			int red, int green, int blue);
	
	
	
	/**
	 * 
	 * @param im
	 * @param x1
	 * @param y1
	 * @param x2
	 * @param y2
	 * @param red
	 * @param green
	 * @param blue
	 * @param width
	 * @return
	 */
	public void drawStraightLine(
			int x1, int y1,
			int x2, int y2,
			int red, int green, int blue,
			int width);
	
	
	
} 
