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

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


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
	
//	public static final  int DEFAULT_LINE_R = 0;
//	public static final  int DEFAULT_LINE_G = 0;
//	public static final  int DEFAULT_LINE_B = 0;
	
	private static final  int DEFAULT_TEXT_ORIENTATION_DEGREES = 0;
//	public static final  int DEFAULT_TEXT_R = 0;
//	public static final  int DEFAULT_TEXT_G = 0;
//	public static final  int DEFAULT_TEXT_B = 0;
	
	private static final int DEFAULT_TRIANGLE_BASE = 7;
	private static final int DEFAULT_TRIANGLE_ALTITUDE = 7;
	

	private final static Logger LOG = LoggerFactory.getLogger(Java2DFacade.class);
	
	private BufferedImage im;


	public Java2DFacade(){
		super();
	}
	
	public BufferedImage getBufferedImage() {
		return im;
	}

	public void setBufferedImage(BufferedImage im) {
		this.im = im;
	}

	/**
	 * Creates an empty image in the instance variable of the Java2DFacade object. 
	 * This empty image represents the background of the chart 
	 * @param width the width of the image
	 * @param height the height of the image
	 */
	@Override
	public void createEmptyImage(int width, int height) {
		BufferedImage im = new BufferedImage(width, height, BufferedImage.TYPE_3BYTE_BGR);
		Graphics2D g2d = im.createGraphics();
		g2d.setPaint (  Color.WHITE );
		g2d.fillRect ( 0, 0, im.getWidth(), im.getHeight() );
		setBufferedImage(im);
	}

	/**
	 * 
	 * @param xCoord
	 * @param yCoord
	 * @param width
	 * @param heigth
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
	 * 
	 * @param xCoord: the x-coordinate of the center of the box
	 * @param yCoord: the y-coordinate of the center of the box
	 * @param width
	 * @param heigth
	 * @param redFill
	 * @param greenFill
	 * @param blueFill
	 */
	public void drawBox(int xCoord, int yCoord,
			int width, int heigth,
			int redFill, int greenFill, int blueFill
			){
		LOG.info("DRAWING BOX IN IDRAW IMPL");
		System.out.println(width + " " + heigth);
		drawBox(xCoord, yCoord, 
				width, heigth, 
				Java2DFacade.DEFAULT_BLACK_R, Java2DFacade.DEFAULT_BLACK_G, Java2DFacade.DEFAULT_BLACK_B,
				redFill, greenFill, blueFill,
				Java2DFacade.DEFAULT_BORDER_WIDTH);
		
	}
	
	/**
	 * @param xCoord: the x-coordinate of the center of the box
	 * @param yCoord: the y-coordinate of the center of the box
	 */
	@Override
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
	 * @param xCoord: the x-coordinate of the center of the ellipse
	 * @param yCoord: the y-coordinate of the center of the ellipse
	 */
	@Override
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
	
	public void drawCircle(
			int xCoord, int yCoord,
			int diameter,
			int redBorder, int greenBorder, int blueBorder,
			int redFill, int greenFill, int blueFill,
			int borderWidth){
		drawEllipse(xCoord, yCoord, diameter, diameter, redBorder, greenBorder, blueBorder, redFill, greenFill, blueFill, borderWidth);
	}

	@Override
	public void drawCircle(
			int xCoord, int yCoord,
			int diameter,
			int redFill, int greenFill, int blueFill
			) {
		drawCircle(xCoord, yCoord, diameter, Java2DFacade.DEFAULT_BLACK_R, Java2DFacade.DEFAULT_BLACK_G, Java2DFacade.DEFAULT_BLACK_B,
				redFill, greenFill, blueFill,
				Java2DFacade.DEFAULT_BORDER_WIDTH);
	}
	
	
	public void drawText(
			String textToDraw,
			int xCoord, int yCoord
			) {
		drawText(textToDraw, 
				xCoord, yCoord, 
				Java2DFacade.DEFAULT_TEXT_ORIENTATION_DEGREES, 
				Java2DFacade.DEFAULT_BLACK_R, Java2DFacade.DEFAULT_BLACK_G, Java2DFacade.DEFAULT_BLACK_B);
		
	}
	
	
	@Override
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

	public void drawStraightLine(
			int x1, int y1,
			int x2, int y2 
			) {
		
		drawStraightLine(x1, y1, x2, y2,
				Java2DFacade.DEFAULT_BLACK_R, Java2DFacade.DEFAULT_BLACK_G, DEFAULT_BLACK_B,
				Java2DFacade.DEFAULT_LINE_WIDTH
				);
		
	}
	@Override
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
			){
		drawStraightLine(lineEndX, lineEndY- lineLength, lineEndX, lineEndY );
		drawTriangle(lineEndX, lineEndY - lineLength -DEFAULT_TRIANGLE_ALTITUDE,
				lineEndX - DEFAULT_TRIANGLE_BASE/2, lineEndY- lineLength,
				lineEndX + DEFAULT_TRIANGLE_BASE/2, lineEndY- lineLength);
	}
	
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
	 * 
	 * @param xCoord: the x-coordinate of the center of the trapezoid
	 * 					relative to the width and the highest of the two sides
	 * @param yCoord: the y-coordinate of the center of the trapezoid
	 * 					relative to the width and the highest of the two sides
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
	public void drawTrapezoid(
			int xCoord, int yCoord,
			int trap1, int trap2, int trap3,
			int redFill, int greenFill, int blueFill
			){
		LOG.info("DRAWING BOX IN IDRAW IMPL");
		drawTrapezoid(xCoord, yCoord, 
				trap1, trap2, trap3,
				Java2DFacade.DEFAULT_BLACK_R, Java2DFacade.DEFAULT_BLACK_G, Java2DFacade.DEFAULT_BLACK_B,
				redFill, greenFill, blueFill,
				Java2DFacade.DEFAULT_BORDER_WIDTH);
		
	}
	
	
	
	


}
