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
	
	private static final int TEXT_OFFSET = 5;
	
	private Position p;
	private double width;
	private double height;
	private Color color;
	private String name;
	
	public Box(Position p, double width, double height, Color c, String name) throws IllegalArgumentException{
		setPosition(p);
		setWidth(width);
		setHeight(height);
		setColor(color);
		setName(name);
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
	public double getWidth() {
		return this.width;
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public void setWidth(double w) throws IllegalArgumentException{
		if(w <= 0) 
			throw new IllegalArgumentException("Width of a resource cannot be less than or equal to zero");
		this.width = w;
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public double getHeight() {
		return this.height;
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public void setHeight(double h) throws IllegalArgumentException{
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

	@Override
	public String getName() {
		return this.name;
	}


	@Override
	public void setName(String name) {
		this.name = name;
		
	}
	
	@Override
	public void draw(IDraw d){
		//TODO zet overloading ook in interface
		d.drawBox((int)p.getX(), (int)p.getY(), (int)getWidth(), (int)getHeight(), color.getRed(), color.getGreen(), color.getBlue());
		d.drawText(getName(), (int)p.getX(), (int)p.getY()+ (int)getHeight()/2 + TEXT_OFFSET, 0, 0, 0, 0);
	}

	
}
