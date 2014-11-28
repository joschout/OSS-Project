package be.kuleuven.cs.oss.resourcevisualizations;

import java.util.Map;
import be.kuleuven.cs.oss.datautils.Color;
import be.kuleuven.cs.oss.datautils.Position;
import be.kuleuven.cs.oss.datautils.Size;

/**
 *  The class BoxFactory is a specific implementation of the ResourceVisualizationFactory class.
 *  It creates a ResourceVisualization in the form of a Box object for one specific resource,
 *   based on the given map of ResourcePropert names and their values,
 *    which it gets as an argument in its create method.
 *    
 * @author Jonas
 *
 */
public class BoxFactory implements ResourceVisualizationFactory {

	
	private int defaultXCoord = 0;
	private int defaultYCoord = 0;
	private int defaultWidth = 10;
	private int defaultHeight = 10;
	
	// default color of a Box is White ( = RGB 255,255,255)
	private static final Color DEFAULT_COLOR = new Color(255, 255, 255);
	// default name of a Box is the empty string
	private static final String DEFAULT_NAME = "";
	
	/**
	 * Empty constuctor.
	 */
	public BoxFactory() {
	}
	 
	public int getDefaultXCoord() {
		return defaultXCoord;
	}

	public void setDefaultXCoord(int defaultXCoord) {
		this.defaultXCoord = defaultXCoord;
	}

	public int getDefaultYCoord() {
		return defaultYCoord;
	}

	public void setDefaultYCoord(int defaultYCoord) {
		this.defaultYCoord = defaultYCoord;
	}

	public int getDefaultWidth() {
		return defaultWidth;
	}

	public void setDefaultWidth(int defaultWidth) {
		this.defaultWidth = defaultWidth;
	}

	public int getDefaultHeight() {
		return defaultHeight;
	}

	public void setDefaultHeight(int defaultHeight) {
		this.defaultHeight = defaultHeight;
	}

	/**
	 * {@inheritDoc}
	 * 
	 */
	@Override
	public ResourceVisualization create(Map<String, Double> map) {
		
		Position position = new Position(defaultXCoord, defaultYCoord);
		Size size = new Size(defaultWidth, defaultHeight);
		Box box = new Box(position, size, DEFAULT_COLOR, DEFAULT_NAME);
		
		for(String property: map.keySet()){
			switch(property){
			case "xmetric": box.setPosition(new Position(map.get("xmetric").intValue(), box.getPosition().getY()));
							break;
			case "ymetric": box.setPosition(new Position( box.getPosition().getX(), map.get("ymetric").intValue()));
							break;
			case "boxwidth":box.setSize(new Size(map.get("boxwidth").intValue(), box.getHeight()));
							break;
			case "boxheight":box.setSize(new Size(box.getWidth(),map.get("boxheight").intValue()));
							break;
			case "colorR": box.setColor(new Color(map.get("colorR").intValue(), box.getColor().getGreen(), box.getColor().getBlue()));
							break;
			case "colorG": box.setColor(new Color(box.getColor().getRed(),map.get("colorG").intValue(), box.getColor().getBlue()));
							break;
			case "colorB": box.setColor(new Color(box.getColor().getRed(), box.getColor().getGreen(), map.get("colorB").intValue()));
							break;
			default : break;
		
			}
		
		}
		return box;
	}

}
