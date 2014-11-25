package be.kuleuven.cs.oss.resourcevisualizations;

import java.awt.image.BufferedImage;

import be.kuleuven.cs.oss.datautils.Color;
import be.kuleuven.cs.oss.datautils.Position;
import be.kuleuven.cs.oss.drawingPackage.IDraw;


/**
 * Class representing Box resource visualizations.
 * @author Lennart De Graef
 *
 */
public class Box implements ResourceVisualization{
	
	private Position p;
	private int width;
	private int height;
	private Color color;
	
	public Box(Position p, int width, int height, Color c) throws IllegalArgumentException{
		setPosition(p);
		setWidth(width);
		setHeight(height);
		setColor(color);
	}

	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public Position getPosition() {
		return this.p;
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public void setPosition(Position p) {
		this.p = p;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public int getWidth() {
		return this.width;
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public void setWidth(int w) throws IllegalArgumentException{
		if(w <= 0) 
			throw new IllegalArgumentException("Width of a resource cannot be less than or equal to zero");
		this.width = w;
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public int getHeight() {
		return this.height;
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public void setHeight(int h) throws IllegalArgumentException{
		if(h <= 0) 
			throw new IllegalArgumentException("Height of a resource cannot be less than or equal to zero");
		this.height = h;
	}
	
	/**
	 * {@inheritDoc}
	 * 
	 */
	@Override
	public Color getColor() {
		return this.color;
	}

	/**
	 * {@inheritDoc}
	 * 
	 */
	@Override
	public void setColor(Color c) {
		this.color = c;
	}
	
	public BufferedImage draw(IDraw d, BufferedImage im){
		BufferedImage out  = d.drawBox(im, p.getX(), p.getY(), getWidth(), getHeight(), color.getRed(), color.getGreen(), color.getBlue());
		return out;
	}


	


	@Override
	public BufferedImage draw(BufferedImage im, IDraw d) {
		// TODO Auto-generated method stub
		return null;
	}
	
}
