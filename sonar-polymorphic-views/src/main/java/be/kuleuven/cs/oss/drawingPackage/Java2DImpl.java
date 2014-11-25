package be.kuleuven.cs.oss.drawingPackage;

import java.awt.BasicStroke;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.geom.AffineTransform;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Line2D;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.awt.Color;

public class Java2DImpl implements IDraw{
	
	
	public static final  int DEFAULT_FILL_R = 255;
	public static final  int DEFAULT_FILL_G = 255;
	public static final  int DEFAULT_FILL_B = 255;
	
	public static final  int DEFAULT_BORDER_R = 0;
	public static final  int DEFAULT_BORDER_G = 0;
	public static final  int DEFAULT_BORDER_B = 0;
	
	public static final  int DEFAULT_BORDER_WIDTH = 3;
	public static final  int DEFAULT_LINE_WIDTH = 3;
	
	public static final  int DEFAULT_LINE_R = 0;
	public static final  int DEFAULT_LINE_G = 0;
	public static final  int DEFAULT_LINE_B = 0;
	
	public static final  int DEFAULT_TEXT_ORIENTATION_DEGREES = 0;
	public static final  int DEFAULT_TEXT_R = 0;
	public static final  int DEFAULT_TEXT_G = 0;
	public static final  int DEFAULT_TEXT_B = 0;
	
	public BufferedImage im;

	
	public Java2DImpl(int width, int height){
		createEmptyImage(width, height);
	}
	
	public BufferedImage getBufferedImage() {
		return im;
	}

	public void setBufferedImage(BufferedImage im) {
		this.im = im;
	}

	@Override
	public void createEmptyImage(int width, int height) {
		BufferedImage im = new BufferedImage(width, height, BufferedImage.TYPE_3BYTE_BGR);
		Graphics2D g2d = im.createGraphics();
		g2d.setPaint (  Color.WHITE );
		g2d.fillRect ( 0, 0, im.getWidth(), im.getHeight() );
		setBufferedImage(im);
	}

	
	public void drawBox(int xCoord, int yCoord,
			int width, int heigth
			){
		drawBox(xCoord, yCoord, 
				width, heigth, 
				Java2DImpl.DEFAULT_BORDER_R, Java2DImpl.DEFAULT_BORDER_G, Java2DImpl.DEFAULT_BORDER_B,
				Java2DImpl.DEFAULT_FILL_R, Java2DImpl.DEFAULT_FILL_G, Java2DImpl.DEFAULT_FILL_B,
				Java2DImpl.DEFAULT_BORDER_WIDTH);
		
	}
	
	public void drawBox(int xCoord, int yCoord,
			int width, int heigth,
			int redFill, int greenFill, int blueFill
			){
		drawBox(xCoord, yCoord, 
				width, heigth, 
				Java2DImpl.DEFAULT_BORDER_R, Java2DImpl.DEFAULT_BORDER_G, Java2DImpl.DEFAULT_BORDER_B,
				redFill, greenFill, blueFill,
				Java2DImpl.DEFAULT_BORDER_WIDTH);
		
	}
	
		
	@Override
	public void drawBox(
			int xCoord, int yCoord,
			int width, int heigth,
			int redBorder, int greenBorder, int blueBorder,
			int redFill, int greenFill, int blueFill,
			int borderWidth
			) {
		
		Graphics2D g2d = getBufferedImage().createGraphics();
		Rectangle2D rect = new Rectangle2D.Double(xCoord, yCoord, width, heigth);
		
		g2d.setColor(new Color(redFill, greenFill, blueFill));
		g2d.fill(rect);
		
		BasicStroke stroke = new BasicStroke(borderWidth);
		g2d.setStroke(stroke);
		g2d.setColor(new Color(redBorder, greenBorder, blueBorder));
		g2d.draw(rect);

		
	}

	@Override
	public void drawCircle(
			int xCoord, int yCoord,
			int width, int heigth, 
			int redBorder, int greenBorder, int blueBorder,
			int redFill, int greenFill, int blueFill,
			int borderWidth
			) {
		
		Graphics2D g2d = getBufferedImage().createGraphics();
		Ellipse2D rect = new Ellipse2D.Double(xCoord, yCoord, width, heigth);
		
		g2d.setColor(new Color(redFill, greenFill, blueFill));
		g2d.fill(rect);
		
		BasicStroke stroke = new BasicStroke(borderWidth);
		g2d.setStroke(stroke);
		g2d.setColor(new Color(redBorder, greenBorder, blueBorder));
		g2d.draw(rect);
		
		
		
		
	}

	public void drawText(
			String textToDraw,
			int xCoord, int yCoord
			) {
		drawText(textToDraw, 
				xCoord, yCoord, 
				Java2DImpl.DEFAULT_TEXT_ORIENTATION_DEGREES, 
				Java2DImpl.DEFAULT_TEXT_R, Java2DImpl.DEFAULT_TEXT_G, Java2DImpl.DEFAULT_TEXT_B);
		
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
				Java2DImpl.DEFAULT_LINE_R, Java2DImpl.DEFAULT_LINE_G, DEFAULT_BORDER_B,
				Java2DImpl.DEFAULT_LINE_WIDTH
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
	


}
