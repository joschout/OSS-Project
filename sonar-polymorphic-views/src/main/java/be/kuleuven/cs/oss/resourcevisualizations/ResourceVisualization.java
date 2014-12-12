package be.kuleuven.cs.oss.resourcevisualizations;

import java.awt.image.BufferedImage;

import be.kuleuven.cs.oss.datautils.Color;
import be.kuleuven.cs.oss.datautils.Position;
import be.kuleuven.cs.oss.datautils.Size;
import be.kuleuven.cs.oss.drawingPackage.IDraw;

public abstract class ResourceVisualization {
	
	private Position p;
	private Size size;
	private Color color;
	private String name;

	public ResourceVisualization(Position position, Size size, Color color, String name) throws IllegalArgumentException {
		setPosition(position);
		setSize(size);
		setColor(color);
		setName(name);
	}
	
	/**
	 * Gets the position of this RV
	 * @return Position of the RV
	 */
	public Position getPosition() {
		return this.p;
	}
	/**
	 * Sets the position of this RV
	 * @param p the new position
	 */
	
	public void setPosition(Position p) {
		this.p = p;
	}
	
	public int getX() {
		return getPosition().getX();
	}
	
	public int getY() {
		return getPosition().getY();
	}
	
	public int getWidth() {
		return getSize().getWidth();
	}

	
	public int getHeight() {
		return getSize().getHeight();
	}

	/**
	 * Gets the Size of this RV
	 * @return Width of the RV
	 */
	public Size getSize() {
		return this.size;
	}

	
	/**
	 * Sets the Size of this RV, which contains a width and a height
	 * @param size the new size of the RV
	 */
	public void setSize(Size size) {
		this.size = size;

	}
		
	/**
	 * Get the color of this RV
	 * @return Color of the RV
	 */
	public Color getColor() {
		return this.color;
	}

	
	/**
	 * set the color of this RV
	 * @param c Color to be set
	 */
	public void setColor(Color c) {
		this.color = c;
	}

	
	/**
	 * gets the name of this RV
	 * @return the name of the RV
	 */
	public String getName() {
		return this.name;
	}

	
	/**
	 * Sets the name of this RV
	 * @param name The name to be set
	 */
	public void setName(String name) {
		this.name = name;

	}
		

	
	
	public abstract void draw(IDraw d);
	
}
