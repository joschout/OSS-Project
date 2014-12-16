package be.kuleuven.cs.oss.drawingPackage;

import java.awt.BasicStroke;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.Polygon;
import java.awt.geom.AffineTransform;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Line2D;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.awt.Color;

/**
 * Java2DFacade implements the IDraw interface using the Java2D library.
 * An instantiation of the class has a BufferedImage as an instance variable,
 *  which contains a partial rendering of a chart.
 * 
 * @author Jonas
 *
 */
public class Java2DFacade implements IDraw{
	
	// white
	private static final  int DEFAULT_WHITE_R = 255;
	private static final  int DEFAULT_WHITE_G = 255;
	private static final  int DEFAULT_WHITE_B = 255;
	
	private static final  int DEFAULT_BLACK_R = 0;
	private static final  int DEFAULT_BLACK_G = 0;
	private static final  int DEFAULT_BLACK_B = 0;
	
	private static final  int DEFAULT_BORDER_WIDTH = 3;
	private static final  int DEFAULT_LINE_WIDTH = 3;
	private static final  int DEFAULT_TRIANGLE_WIDTH = 3;
	
	private static final  int DEFAULT_TEXT_ORIENTATION_DEGREES = 0;
	
	private static final int DEFAULT_TRIANGLE_BASE = 7;
	private static final int DEFAULT_TRIANGLE_ALTITUDE = 7;
		
	private BufferedImage im;

	/**
	 * Constructs a Java2DFacade object
	 */
	public Java2DFacade(){
		super();
	}
	
	/**
	 * Gets the BufferedImage the Java2DFacade has drawn up until know.
	 */
	public BufferedImage getBufferedImage() {
		return im;
	}

	/**
	 *  Sets the BufferedImage the Java2DFacade contains with the given BufferedImage
	 */
	public void setBufferedImage(BufferedImage im) {
		this.im = im;
	}

	/**
	 * Creates an empty image in the instance variable of the Java2DFacade object. 
	 * This empty image represents the background of the chart 
	 * @param width the width of the image
	 * @param height the height of the image
	 */
	public void createEmptyImage(int width, int height) {
		BufferedImage im = new BufferedImage(width, height, BufferedImage.TYPE_3BYTE_BGR);
		Graphics2D g2d = im.createGraphics();
		g2d.setPaint (  Color.WHITE );
		g2d.fillRect ( 0, 0, im.getWidth(), im.getHeight() );
		setBufferedImage(im);
	}

	/**
	 * Draws a box on the BufferedImage in memory. 
	 * The parameters xCoord, yCoord, width and height are expressed in pixels.
	 * This method draws the box with a black outline of a default width and a white fill
	 * @param xCoord: the x-coordinate of the center of the box 
	 * @param yCoord: the y-coordinate of the center of the box
	 * @param width: the width of the box
	 * @param heigth: the height of the box
	 */
	public void drawBox(int xCoord, int yCoord,
			int width, int heigth
			){
		drawBox(xCoord, yCoord, 
				width, heigth, 
				Java2DFacade.DEFAULT_BLACK_R, Java2DFacade.DEFAULT_BLACK_G, Java2DFacade.DEFAULT_BLACK_B,
				Java2DFacade.DEFAULT_WHITE_R, Java2DFacade.DEFAULT_WHITE_G, Java2DFacade.DEFAULT_WHITE_B,
				Java2DFacade.DEFAULT_BORDER_WIDTH);
		
	}
	
	/**
	 * Draws a box on the BufferedImage in memory. 
	 * The parameters xCoord, yCoord, width and height are expressed in pixels.
	 * The color arguments specify the RGB-color which will be used to fill the box.
	 * 
	 * @param xCoord: the x-coordinate of the center of the box
	 * @param yCoord: the y-coordinate of the center of the box
	 * @param width: the width of the box
	 * @param heigth: the height of the box
	 * @param redFill: the red channel of the RGB-color of the fill of the box
	 * @param greenFill: the green channel of the RGB-color of the fill of the box
	 * @param blueFill: the blue channel of the RGB-color of the fill of the box
	 */
	public void drawBox(int xCoord, int yCoord,
			int width, int heigth,
			int redFill, int greenFill, int blueFill
			){
		drawBox(xCoord, yCoord, 
				width, heigth, 
				Java2DFacade.DEFAULT_BLACK_R, Java2DFacade.DEFAULT_BLACK_G, Java2DFacade.DEFAULT_BLACK_B,
				redFill, greenFill, blueFill,
				Java2DFacade.DEFAULT_BORDER_WIDTH);
		
	}
	
	/**
	 * Draws a box on the BufferedImage in memory. 
	 * The parameters xCoord, yCoord, width and height are expressed in pixels.
	 * The color arguments specify the RGB-colors: one for the line that borders the box,
	 * the other for the fill of the box.
	 * 
	 * @param xCoord: the x-coordinate of the center of the box
	 * @param yCoord: the y-coordinate of the center of the box
	 * @param width: the width of the box
	 * @param heigth: the height of the box
	 * @param redBorder: the red channel of the RGB-color of the border of the box
	 * @param greenBorder: the green channel of the RGB-color of the border of the box
	 * @param blueBorder: the blue channel of the RGB-color of the border of the box
	 * @param redFill: the red channel of the RGB-color of the fill of the box
	 * @param greenFill: the green channel of the RGB-color of the fill of the box
	 * @param blueFill: the blue channel of the RGB-color of the fill of the box
	 * @param borderWidth: the width of the border around the box
	 */
	public void drawBox(
			int xCoord, int yCoord,
			int width, int heigth,
			int redBorder, int greenBorder, int blueBorder,
			int redFill, int greenFill, int blueFill,
			int borderWidth
			) {
		
		Graphics2D g2d = getBufferedImage().createGraphics();
		
		/*
		 * public Rectangle2D.Double(double x, double y, double w,double h)
		 *	Constructs and initializes a Rectangle2D from the specified double coordinates.
		 *  Parameters:
		 *  x - the X coordinate of the upper-left corner of the newly constructed Rectangle2D
		 *  y - the Y coordinate of the upper-left corner of the newly constructed Rectangle2D
		 *  w - the width of the newly constructed Rectangle2D
		 *  h - the height of the newly constructed Rectangle2D
		 * 
		 */
		Rectangle2D rect = new Rectangle2D.Double(xCoord - width/2, yCoord - heigth/2, width, heigth);
		
		g2d.setColor(new Color(redFill, greenFill, blueFill));
		g2d.fill(rect);
		
		BasicStroke stroke = new BasicStroke(borderWidth);
		g2d.setStroke(stroke);
		g2d.setColor(new Color(redBorder, greenBorder, blueBorder));
		g2d.draw(rect);

		
	}

	/**
	 * Draws a ellipse on the BufferedImage in memory. 
	 * The parameters xCoord, yCoord, width and height are expressed in pixels.
	 * The color arguments specify the RGB-colors: one for the line that borders the ellipse,
	 * the other for the fill of the ellipse.
	 * 
	 * @param xCoord: the x-coordinate of the center of the ellipse
	 * @param yCoord: the y-coordinate of the center of the ellipse
	 * @param width: the width of the ellipse
	 * @param heigth: the height of the ellipse
	 * @param redBorder: the red channel of the RGB-color of the border of the ellipse
	 * @param greenBorder: the green channel of the RGB-color of the border of the ellipse
	 * @param blueBorder: the blue channel of the RGB-color of the border of the ellipse
	 * @param redFill: the red channel of the RGB-color of the fill of the ellipse
	 * @param greenFill: the green channel of the RGB-color of the fill of the ellipse
	 * @param blueFill: the blue channel of the RGB-color of the fill of the ellipse
	 * @param borderWidth: the width of the border around the ellipse
	 */
	public void drawEllipse(
			int xCoord, int yCoord,
			int width, int heigth, 
			int redBorder, int greenBorder, int blueBorder,
			int redFill, int greenFill, int blueFill,
			int borderWidth
			) {
		
		Graphics2D g2d = getBufferedImage().createGraphics();
		Ellipse2D ellipse = new Ellipse2D.Double(xCoord - width/2 , yCoord- heigth/2, width, heigth);
		
		g2d.setColor(new Color(redFill, greenFill, blueFill));
		g2d.fill(ellipse);
		
		BasicStroke stroke = new BasicStroke(borderWidth);
		g2d.setStroke(stroke);
		g2d.setColor(new Color(redBorder, greenBorder, blueBorder));
		g2d.draw(ellipse);
	}
	
	/**
	 * Draws a circle on the BufferedImage in memory. 
	 * The parameters xCoord, yCoord, width and height are expressed in pixels.
	 * The color arguments specify the RGB-colors: one for the line that borders the circle,
	 * the other for the fill of the circle.
	 * 
	 * @param xCoord: the x-coordinate of the center of the circle
	 * @param yCoord: the y-coordinate of the center of the circle
	 * @param diameter: the diameter of the circle
	 * @param redBorder: the red channel of the RGB-color of the border of the circle
	 * @param greenBorder: the green channel of the RGB-color of the border of the circle
	 * @param blueBorder: the blue channel of the RGB-color of the border of the circle
	 * @param redFill: the red channel of the RGB-color of the fill of the circle
	 * @param greenFill: the green channel of the RGB-color of the fill of the circle
	 * @param blueFill: the blue channel of the RGB-color of the fill of the circle
	 * @param borderWidth: the width of the border around the circle
	 */
	public void drawCircle(
			int xCoord, int yCoord,
			int diameter,
			int redBorder, int greenBorder, int blueBorder,
			int redFill, int greenFill, int blueFill,
			int borderWidth){
		drawEllipse(xCoord, yCoord, diameter, diameter, redBorder, greenBorder, blueBorder, redFill, greenFill, blueFill, borderWidth);
	}

	/**
	 * Draws a circle on the BufferedImage in memory. 
	 * The parameters xCoord, yCoord, width and height are expressed in pixels.
	 * The color arguments specify the RGB-color which will be used to fill the circle.
	 * 
	 * @param xCoord: the x-coordinate of the center of the circle
	 * @param yCoord: the y-coordinate of the center of the circle
	 * @param diameter: the diameter of the circle
	 * @param redFill: the red channel of the RGB-color of the fill of the circle
	 * @param greenFill: the green channel of the RGB-color of the fill of the circle
	 * @param blueFill: the blue channel of the RGB-color of the fill of the circle
	 */
	public void drawCircle(
			int xCoord, int yCoord,
			int diameter,
			int redFill, int greenFill, int blueFill
			) {
		drawCircle(xCoord, yCoord, diameter, Java2DFacade.DEFAULT_BLACK_R, Java2DFacade.DEFAULT_BLACK_G, Java2DFacade.DEFAULT_BLACK_B,
				redFill, greenFill, blueFill,
				Java2DFacade.DEFAULT_BORDER_WIDTH);
	}
	
	/**
	 * Draws a text on the BufferedImage in memory.
	 * Draws the text in black and parallel to the horizontal image sides
	 * 
	 * @param textToDraw: the text the method draws on the image
	 * @param xCoord: the x-coordinate of the middlepoint of the underside of the text
	 * @param yCoord: the y-coordinate of the middlepoint of the underside of the text
	 */
	public void drawText(
			String textToDraw,
			int xCoord, int yCoord
			) {
		drawText(textToDraw, 
				xCoord, yCoord, 
				Java2DFacade.DEFAULT_TEXT_ORIENTATION_DEGREES, 
				Java2DFacade.DEFAULT_BLACK_R, Java2DFacade.DEFAULT_BLACK_G, Java2DFacade.DEFAULT_BLACK_B);
		
	}
	
	/**
	 * Draws a text on the BufferedImage in memory.
	 * 
	 * @param textToDraw: the text the method draws on the image
	 * @param xCoord: the x-coordinate of the middlepoint of the underside of the text
	 * @param yCoord: the y-coordinate of the middlepoint of the underside of the text
	 * @param orientationAngleInDegrees: the orientation of the text in degrees 
	 * @param red: the red channel of the RGB-color used for the text color
	 * @param green: the green channel of the RGB-color used for the text color
 	 * @param blue: the blue channel of the RGB-color used for the text color
	 */
	public void drawText(
			String textToDraw,
			int xCoord, int yCoord,
			int orientationAngleInDegrees,
			int red, int green, int blue) {
		Graphics2D g2d = getBufferedImage().createGraphics();
		FontMetrics fm = g2d.getFontMetrics();
		int textWidth =  fm.stringWidth(textToDraw);
		g2d.setColor(new Color(red, green, blue));
		double orientationAngleInRadians = Math.toRadians((double) orientationAngleInDegrees);
		
		AffineTransform transform = new AffineTransform();
		transform.rotate(orientationAngleInRadians, xCoord, yCoord);
		AffineTransform old = g2d.getTransform();
		g2d.transform(transform);
		g2d.drawString(textToDraw, xCoord-textWidth/2, yCoord);
		g2d.setTransform(old);
				
	}

	/**
	 * Draws a straight line segment on the BufferedImage in memory.
	 * The line is drawn in black with the default line width.
	 * 
	 * @param x1 the x-coordinate of the first endpoint
	 * @param y1 the y-coordinate of the first endpoint
	 * @param x2 the x-coordinate of the second endpoint
	 * @param y2 the y-coordinate of the second endpoint
	 * 
	 */
	public void drawStraightLine(
			int x1, int y1,
			int x2, int y2 
			) {
		
		drawStraightLine(x1, y1, x2, y2,
				Java2DFacade.DEFAULT_BLACK_R, Java2DFacade.DEFAULT_BLACK_G, DEFAULT_BLACK_B,
				Java2DFacade.DEFAULT_LINE_WIDTH
				);
		
	}
	
	/**
	 * Draws a straight line segment on the BufferedImage in memory
	 * 
	 * @param x1 the x-coordinate of the first endpoint
	 * @param y1 the y-coordinate of the first endpoint
	 * @param x2 the x-coordinate of the second endpoint
	 * @param y2 the y-coordinate of the second endpoint
	 * @param red the red channel of the RGB-color used for the line color
	 * @param green the green channel of the RGB-color used for the line color
	 * @param blue the blue channel of the RGB-color used for the line color
	 * @param width the line width
	 * 
	 */
	public void drawStraightLine(
			int x1, int y1,
			int x2, int y2, 
			int red, int green, int blue, 
			int width) {
		
		Graphics2D g2d = getBufferedImage().createGraphics();
		Line2D line = new Line2D.Double(x1, y1, x2, y2);
		g2d.setColor(new Color(red, green, blue));
		BasicStroke stroke = new BasicStroke(width);
		g2d.setStroke(stroke);
		g2d.draw(line);	
	}
	
	/**
	 * Draws a triangle on the BufferedImage in memory.
	 * A triangle is defined by three vertices.
	 * The triangle' sides are drawn in black.
	 * The triangle is also filled in black.
	 * 
	 * @param x1 the x-coordinate of the first vertex
	 * @param y1 the y-coordinate of the first vertex
	 * @param x2 the x-coordinate of the second vertex
	 * @param y2 the y-coordinate of the second vertex
     * @param x3 the x-coordinate of the third vertex
	 * @param y3 the y-coordinate of the third vertex
	 */
	public void drawTriangle(int x1, int y1,
			int x2, int y2,
			int x3, int y3
			){
		drawTriangle(
				x1, y1, 
				x2, y2, 
				x3, y3, 
				Java2DFacade.DEFAULT_BLACK_R, Java2DFacade.DEFAULT_BLACK_G, Java2DFacade.DEFAULT_BLACK_B, 
				Java2DFacade.DEFAULT_BLACK_R, Java2DFacade.DEFAULT_BLACK_G, Java2DFacade.DEFAULT_BLACK_B, 
				Java2DFacade.DEFAULT_TRIANGLE_WIDTH);	
	}
	
	/**
	 * Draws a triangle on the BufferedImage in memory.
	 * A triangle is defined by three vertices.
	 * 
	 * 
	 * @param x1 the x-coordinate of the first vertex
	 * @param y1 the y-coordinate of the first vertex
	 * @param x2 the x-coordinate of the second vertex
	 * @param y2 the y-coordinate of the second vertex
     * @param x3 the x-coordinate of the third vertex
	 * @param y3 the y-coordinate of the third vertex
	 * @param redBorder: the red channel of the RGB-color of the border of the triangle
	 * @param greenBorder: the green channel of the RGB-color of the border of the triangle
	 * @param blueBorder: the blue channel of the RGB-color of the border of the triangle
	 * @param redFill: the red channel of the RGB-color of the fill of the triangle
	 * @param greenFill: the green channel of the RGB-color of the fill of the triangle
	 * @param blueFill: the blue channel of the RGB-color of the fill of the triangle
	 * @param borderWidth: the width of the border around the triangle
	 */
	public void drawTriangle(
			int x1, int y1,
			int x2, int y2,
			int x3, int y3,
			int redBorder, int greenBorder, int blueBorder,
			int redFill, int greenFill, int blueFill,
			int borderWidth
			){
		
		Graphics2D g2d = getBufferedImage().createGraphics();
		int[] xArray   = new int[3];
		xArray[0] = x1;
		xArray[1] = x2;
		xArray[2] = x3;
		
		int[] yArray = new int[3];
		yArray[0] = y1;
		yArray[1] = y2;
		yArray[2] = y3;
		Polygon pol = new Polygon( xArray, yArray, 3);
				
		g2d.setColor(new Color(redFill, greenFill, blueFill));
		g2d.fill(pol);
		
		BasicStroke stroke = new BasicStroke(borderWidth);
		g2d.setStroke(stroke);
		g2d.setColor(new Color(redBorder, greenBorder, blueBorder));
		g2d.draw(pol);
	}
	
	/**
	 * Draws an arrow parallel to the vertical sides of the image,
	 *  with its point pointing upwards.
	 *  An arrow consists of a straight line segment and a triangle.
	 * The line segment is defined by a vertex and its length,
	 *  since its orientation is parallel with the vertical edges of the image.
	 * The triangle is attached to the other vertex.
	 * 
	 * @param lineEndX the x-coordinate of the vertex not attached to the triangle
	 * @param lineEndY the y-coordinate of the vertex not attached to the triangle
	 * @param lineLenght the length of the line
	 */
	public void drawArrowUp(
			int lineEndX, int lineEndY,
			int lineLength
			){
		drawStraightLine(lineEndX, lineEndY- lineLength, lineEndX, lineEndY );
		drawTriangle(lineEndX, lineEndY - lineLength -DEFAULT_TRIANGLE_ALTITUDE,
				lineEndX - DEFAULT_TRIANGLE_BASE/2, lineEndY- lineLength,
				lineEndX + DEFAULT_TRIANGLE_BASE/2, lineEndY- lineLength);
	}
	
	/**
	 * Draws an arrow parallel to the horizontal sides of the image,
	 *  with its point pointing to the right.
	 *  An arrow consists of a straight line segment and a triangle.
	 * The line segment is defined by a vertex and its length,
	 *  since its orientation is parallel with the horizontal edges of the image.
	 * The triangle is attached to the other vertex.
	 * 
	 * @param lineEndX the x-coordinate of the vertex not attached to the triangle
	 * @param lineEndY the y-coordinate of the vertex not attached to the triangle
	 * @param lineLenght the length of the line
	 */
	public void drawArrowRight(
			int lineEndX, int lineEndY,
			int lineLength
			){
		drawStraightLine(lineEndX, lineEndY, lineEndX + lineLength, lineEndY);
		drawTriangle(lineEndX + lineLength + DEFAULT_TRIANGLE_ALTITUDE, lineEndY,
				lineEndX + lineLength , lineEndY- DEFAULT_TRIANGLE_BASE/2,
				lineEndX + lineLength , lineEndY+ DEFAULT_TRIANGLE_BASE/2);
	}
	
	/**
	 * Trapezoid on the BufferedImage in memory.
	 * The trapezoid has a base line parallel to the horizontal edges of the image.
	 * Its two lower vertices have right angles. 
	 * The trapezoid is defined by the lengths of its left, lower and right edge. 
	 * 
	 * @param xCoord: the x-coordinate of the center of the trapezoid
	 * 					relative to the width and the highest of the two sides
	 * @param yCoord: the y-coordinate of the center of the trapezoid
	 * 					relative to the width and the highest of the two sides
	 * @param trap1: the left edge of the trapezoid
	 * @param trap2: the lower edge of the trapezoid
	 * @param trap3: the right edge of the trapezoid
	 * @param redBorder: the red channel of the RGB-color of the border of the trapezoid
	 * @param greenBorder: the green channel of the RGB-color of the border of the trapezoid
	 * @param blueBorder: the blue channel of the RGB-color of the border of the trapezoid
	 * @param redFill: the red channel of the RGB-color of the fill of the trapezoid
	 * @param greenFill: the green channel of the RGB-color of the fill of the trapezoid
	 * @param blueFill: the blue channel of the RGB-color of the fill of the trapezoid
	 * @param borderWidth: the width of the border around the trapezoid
	 */
	public void drawTrapezoid(	
			int xCoord, int yCoord,
			int trap1, int trap2, int trap3,
			int redBorder, int greenBorder, int blueBorder,
			int redFill, int greenFill, int blueFill,
			int borderWidth){
		
		Graphics2D g2d = getBufferedImage().createGraphics();
		int[] xArray   = new int[4];
		
		
		//begin linksonderhoek en draai tegen de klok in
		xArray[0] = xCoord - trap2/2;
		xArray[1] = xCoord + trap2/2;
		xArray[2] = xCoord + trap2/2;
		xArray[3] = xCoord - trap2/2;
		
		int[] yArray = new int[4];
		yArray[0] = yCoord + trap1/2;
		yArray[1] = yCoord + trap1/2;
		yArray[2] = yCoord + trap1/2 - trap3;
		yArray[3] = yCoord - trap1/2;
		Polygon pol = new Polygon( xArray, yArray, 4);
				
		g2d.setColor(new Color(redFill, greenFill, blueFill));
		g2d.fill(pol);
		
		BasicStroke stroke = new BasicStroke(borderWidth);
		g2d.setStroke(stroke);
		g2d.setColor(new Color(redBorder, greenBorder, blueBorder));
		g2d.draw(pol);
		
	}
	
	/**
	 * Trapezoid on the BufferedImage in memory.
	 * The trapezoid has a base line parallel to the horizontal edges of the image.
	 * Its two lower vertices have right angles. 
	 * The trapezoid is defined by the lengths of its left, lower and right edge. 
	 * The trapezoid is drawn with a black border and the default border width.
	 * 
	 * 
	 * @param xCoord: the x-coordinate of the center of the trapezoid
	 * 					relative to the width and the highest of the two sides
	 * @param yCoord: the y-coordinate of the center of the trapezoid
	 * 					relative to the width and the highest of the two sides
	 * @param trap1: the left edge of the trapezoid
	 * @param trap2: the lower edge of the trapezoid
	 * @param trap3: the right edge of the trapezoid
	 * @param redFill: the red channel of the RGB-color of the fill of the trapezoid
	 * @param greenFill: the green channel of the RGB-color of the fill of the trapezoid
	 * @param blueFill: the blue channel of the RGB-color of the fill of the trapezoid
	 */
	public void drawTrapezoid(
			int xCoord, int yCoord,
			int trap1, int trap2, int trap3,
			int redFill, int greenFill, int blueFill
			){
		drawTrapezoid(xCoord, yCoord, 
				trap1, trap2, trap3,
				Java2DFacade.DEFAULT_BLACK_R, Java2DFacade.DEFAULT_BLACK_G, Java2DFacade.DEFAULT_BLACK_B,
				redFill, greenFill, blueFill,
				Java2DFacade.DEFAULT_BORDER_WIDTH);
		
	}
	
	
	
	


}
