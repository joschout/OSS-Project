package be.kuleuven.cs.oss.resourcevisualizations;

import java.awt.image.BufferedImage;

import be.kuleuven.cs.oss.datautils.Color;
import be.kuleuven.cs.oss.datautils.Position;
import be.kuleuven.cs.oss.datautils.Size;
import be.kuleuven.cs.oss.drawingPackage.IDraw;

public interface ResourceVisualization {
	
	/**
	 * Gets the position of this RV
	 * @return Position of the RV
	 */
	public Position getPosition();	
	/**
	 * Sets the position of this RV
	 * @param p the new position
	 */
	public void setPosition(Position p);
	
	
	
	public int getWidth();
	
	public int getHeight();
	
	/**
	 * Gets the Size of this RV
	 * @return Width of the RV
	 */
	public Size getSize();
	
	/**
	 * Sets the Size of this RV, which contains a width and a height
	 * @param size the new size of the RV
	 */
	public void setSize(Size size);
		
	/**
	 * Get the color of this RV
	 * @return Color of the RV
	 */
	public Color getColor();
	
	/**
	 * set the color of this RV
	 * @param c Color to be set
	 */
	public void setColor(Color c);
	
	/**
	 * gets the name of this RV
	 * @return the name of the RV
	 */
	public String getName();
	
	/**
	 * Sets the name of this RV
	 * @param name The name to be set
	 */
	public void setName(String name);
		
	public void draw(IDraw d);
	//TODO make the IDraw interface
	
	
}
