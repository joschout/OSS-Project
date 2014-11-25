package be.kuleuven.cs.oss.drawingPackage;

import java.awt.BasicStroke;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.awt.Color;

public class Java2DImpl implements IDraw{
	
	
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
		g2d.setColor(new Color(redBorder, greenBorder, blueBorder));
		g2d.draw(rect);
		
		
		
		
	}

	@Override
	public void drawText(
			String textToDraw,
			int xCoord, int yCoord,
			int orientationAngle,
			int red, int green, int blue) {
		Graphics2D g2d = getBufferedImage().createGraphics();
		
		
		g2d.setColor(new Color(red, green, blue));
		g2d.drawString(textToDraw, xCoord, yCoord);
		
		
		
	}

	@Override
	public void drawStraightLine(
			int x1, int y1,
			int x2, int y2, 
			int red, int green, int blue, 
			int width) {
		// TODO Auto-generated method stub
		
	}
	


}
