package be.kuleuven.cs.oss.resourcevisualizations;

import be.kuleuven.cs.oss.datautils.Color;
import be.kuleuven.cs.oss.datautils.Position;


/**
 * Class representing Box resource visualizations.
 * @author Lennart De Graef
 *
 */
public class Box implements ResourceVisualization{
	
	private Position p;
	private int width;
	private int height;
	
	public Box(Position p, int width, int height, Color c) throws IllegalArgumentException{
		setPosition(p);
		setWidth(width);
		setHeight(height);
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
	
	public void draw(){
		//TODO drawing interface!
	}
	
}
