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
	BufferedImage createEmptyImage(int width, int heigth);

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
	BufferedImage drawBox(BufferedImage im, 
			int xCoord, int yCoord,
			int width, int heigth,
			int red, int green, int blue);
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
	BufferedImage drawCircle(BufferedImage im, 
			int xCoord, int yCoord,
			int width, int heigth,
			int red, int green, int blue);
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
	BufferedImage drawText(BufferedImage im ,
			String textToDraw,
			int xCoord, int yCoord,
			int orientationAngle,
			int red, int green, int blue);
} 
