package be.kuleuven.cs.oss.lines;

import be.kuleuven.cs.oss.datautils.Color;
import be.kuleuven.cs.oss.datautils.Position;

/**
 * An interface that should be implemented by all classes that create lines
 * 
 * @author jeroenreinenbergh
 *
 */
public interface LineFactory {

	public void setDefaultPosition(Position def);

	public void setDefaultWidth(int def);
	
	public void setDefaultColor(Color def);
	
	/**
	 * Creates a new line with no given parameters
	 * @return the new line
	 */
	public Line create();
	
	/**
	 * Creates a new line with a given origin and destination
	 * @param orig the given origin
	 * @param dest the given destination
	 * @return the new line
	 */
	public Line create(Position orig, Position dest);
	
	/**
	 * Creates a new line with a given origin, destination and width
	 * @param orig the given origin
	 * @param dest the given destination
	 * @param width the given width
	 * @return the new line
	 */
	public Line create(Position orig, Position dest, int width);
	
	/**
	 * Creates a new line with a given origin, destination, width and color
	 * @param orig the given origin
	 * @param dest the given destination
	 * @param width the given width
	 * @param color the given color
	 * @return the new line
	 */
	public Line create(Position orig, Position dest, int width, Color color);


}
