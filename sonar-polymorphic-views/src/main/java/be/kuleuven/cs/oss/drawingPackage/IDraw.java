/**
 * 
 */
package be.kuleuven.cs.oss.drawingPackage;

import java.awt.image.BufferedImage;


/**
 * @invar  The upper left corner is the origin of the image plane.
 * @author Jonas
 * 
 */
public interface IDraw {

	public BufferedImage getBufferedImage();

	public void setBufferedImage(BufferedImage im) ;

	/**
	 * Creates an empty image in the instance variable of the IDraw object. 
	 * This empty image represents the background of the chart 
	 * @param width the width of the image
	 * @param height the height of the image
	 */
	public void createEmptyImage(int width, int height);

	/**
	 * 
	 * @param xCoord
	 * @param yCoord
	 * @param width
	 * @param heigth
	 */
	public void drawBox(int xCoord, int yCoord,
			int width, int heigth
			);

	/**
	 * 
	 * @param xCoord
	 * @param yCoord
	 * @param width
	 * @param heigth
	 * @param redFill
	 * @param greenFill
	 * @param blueFill
	 */
	public void drawBox(int xCoord, int yCoord,
			int width, int heigth,
			int redFill, int greenFill, int blueFill
			);


	public void drawBox(
			int xCoord, int yCoord,
			int width, int heigth,
			int redBorder, int greenBorder, int blueBorder,
			int redFill, int greenFill, int blueFill,
			int borderWidth
			);


	public void drawEllipse(
			int xCoord, int yCoord,
			int width, int heigth, 
			int redBorder, int greenBorder, int blueBorder,
			int redFill, int greenFill, int blueFill,
			int borderWidth
			) ;

	public void drawText(
			String textToDraw,
			int xCoord, int yCoord
			);


	public void drawText(
			String textToDraw,
			int xCoord, int yCoord,
			int orientationAngleInDegrees,
			int red, int green, int blue);

	public void drawStraightLine(
			int x1, int y1,
			int x2, int y2 
			);


	public void drawStraightLine(
			int x1, int y1,
			int x2, int y2, 
			int red, int green, int blue, 
			int width);

	public void drawTriangle(int x1, int y1,
			int x2, int y2,
			int x3, int y3
			);


	public void drawTriangle(
			int x1, int y1,
			int x2, int y2,
			int x3, int y3,
			int redBorder, int greenBorder, int blueBorder,
			int redFill, int greenFill, int blueFill,
			int borderWidth
			);

	/**
	 * PRECONDITION: the x-values should be equal
	 * 
	 * @param lineEndArrowX
	 * @param lineEndArrowY
	 * @param lineEndX
	 * @param lineEndY
	 */
	public void drawArrowUp(
			int lineEndX, int lineEndY,
			int lineLength
			);

	public void drawArrowRight(
			int lineEndX, int lineEndY,
			int lineLength
			);

	public void drawTrapezoid(	
			int xCoord, int yCoord,
			int trap1, int trap2, int trap3,
			int redBorder, int greenBorder, int blueBorder,
			int redFill, int greenFill, int blueFill,
			int borderWidth);

	public void drawTrapezoid(
			int xCoord, int yCoord,
			int trap1, int trap2, int trap3,
			int redFill, int greenFill, int blueFill
			);
	
	public void drawCircle(
			int xCoord, int yCoord,
			int radius,
			int redBorder, int greenBorder, int blueBorder,
			int redFill, int greenFill, int blueFill,
			int borderWidth);
	
	public void drawCircle(
			int xCoord, int yCoord,
			int radius ,
			int redFill, int greenFill, int blueFill
			) ;
	
}
