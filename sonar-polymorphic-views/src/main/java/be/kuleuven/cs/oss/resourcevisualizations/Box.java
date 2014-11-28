package be.kuleuven.cs.oss.resourcevisualizations;

import java.awt.image.BufferedImage;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import be.kuleuven.cs.oss.datautils.Color;
import be.kuleuven.cs.oss.datautils.Position;
import be.kuleuven.cs.oss.datautils.Size;
import be.kuleuven.cs.oss.drawingPackage.IDraw;
import be.kuleuven.cs.oss.polymorphicviews.plugin.PolymorphicViewsChart;


/**
 * Class representing Box resource visualizations.
 * @author Lennart De Graef
 *
 */
public class Box implements ResourceVisualization{                     
	
	private static final int TEXT_OFFSET = 1;
	
	private final static Logger LOG = LoggerFactory.getLogger(PolymorphicViewsChart.class);
	
	private Position p;
	private Size size;
	private Color color;
	private String name;
	
	public Box(Position p, Size size, Color c, String name) throws IllegalArgumentException{
		setPosition(p);
		
		setSize(size);
		setColor(c);
		setName(name);
		
		LOG.info("CREATING BOX WITH SIZE: " + getWidth() + " " + getHeight()  + " COLOR: " + color.getRed() + " " + color.getGreen() + " " + color.getBlue());
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
//
//	/**
//	 * {@inheritDoc}
//	 */
//	@Override
//	public double getWidth() {
//		return this.width;
//	}
//	
//	/**
//	 * {@inheritDoc}
//	 */
//	@Override
//	public void setWidth(double w) throws IllegalArgumentException{
//		if(w <= 0) 
//			throw new IllegalArgumentException("Width of a resource cannot be less than or equal to zero");
//		this.width = w;
//	}
//	
//	/**
//	 * {@inheritDoc}
//	 */
//	@Override
//	public double getHeight() {
//		return this.height;
//	}
//	
//	/**
//	 * {@inheritDoc}
//	 */
//	@Override
//	public void setHeight(double h) throws IllegalArgumentException{
//		if(h <= 0) 
//			throw new IllegalArgumentException("Height of a resource cannot be less than or equal to zero");
//		this.height = h;
//	}
	
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
		d.drawText(getName(), p.getX(), p.getY()- getHeight()/2 - TEXT_OFFSET, 0, 0, 0, 0);

	}


	@Override
	public Size getSize() {
		return this.size;
	}


	@Override
	public void setSize(Size size) {
		this.size = size;
		
	}


	@Override
	public int getWidth() {
		return getSize().getWidth();
	}


	@Override
	public int getHeight() {
		return getSize().getHeight();
	}


	@Override
	public int getX() {
		return getPosition().getX();
	}


	@Override
	public int getY() {
		return getPosition().getY();
	}

	
}
