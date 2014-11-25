package be.kuleuven.cs.oss.resourcevisualizations;

import java.awt.image.BufferedImage;

import be.kuleuven.cs.oss.datautils.Color;
import be.kuleuven.cs.oss.datautils.Position;
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
	
	/**
	 * Gets the width of this RV
	 * @return Width of the RV
	 */
	public int getWidth();	
	/**
	 * Sets the width of this RV, needs to be larger than zero
	 * @param w the new width of the RV, larger than zero
	 * @throws IllegalArgumentException throws exception when trying to set width lower or equal to zero
	 */
	public void setWidth(int w) throws IllegalArgumentException;
	
	/**
	 * gets the height of the RV
	 * @return Height of the RV
	 */
	public int getHeight();
	/**
	 * Sets the height of this RV, needs to be larger than zero
	 * @param h the new height of the RV, larger than zero
	 * @throws IllegalArgumentException throws exception when trying to set height lower or equal to zero
	 */
	public void setHeight(int h) throws IllegalArgumentException;
	
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
	
	
	public void draw(IDraw d);
	//TODO make the IDraw interface
	
	
}
