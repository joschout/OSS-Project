package be.kuleuven.cs.oss.resourcevisualizations;

import java.util.Collections;
import java.util.Comparator;

import be.kuleuven.cs.oss.datautils.Color;
import be.kuleuven.cs.oss.datautils.Position;
import be.kuleuven.cs.oss.datautils.Size;
import be.kuleuven.cs.oss.drawingPackage.IDraw;

public abstract class ResourceVisualization implements Comparable<ResourceVisualization>{
	
	private Position p;
	private Size size;
	private Color color;
	private String name;
	
	/**
	 * The text_offset determines the distance between the center of the rv and the name of the rv on the image.
	 */
	private static final int TEXT_OFFSET = 1;



	/**
	 * 
	 * @param position: X and Y coordinate in the image for this resourceVisualization
	 * @param size :  width and height
	 * @param color : color for this resourceVisualization. Consists of an R, B and G value.
	 * @param name :  the name of the resource represented by this resourceVisualization
	 * @throws IllegalArgumentException
	 */
	public ResourceVisualization(Position position, Size size, Color color, String name) throws IllegalArgumentException {
		setPosition(position);
		setSize(size);
		setColor(color);
		setName(name);
	}
	
	public static int getTextOffset() {
		return TEXT_OFFSET;
	}
	
	public Position getPosition() {
		return this.p;
	}
	
	
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

	
	public Size getSize() {
		return this.size;
	}

	
	public void setSize(Size size) {
		this.size = size;
	}
		
	public Color getColor() {
		return this.color;
	}

	
	public void setColor(Color c) {
		this.color = c;
	}

	public String getName() {
		return this.name;
	}

	
	public void setName(String name) {
		this.name = name;
	}
	
	/**
	 * An rv is smaller than another rv when it's area is bigger.
	 * When a list of rv's is sorted, the first rv is the biggest. 
	 */
	@Override
	public int compareTo(ResourceVisualization rv)
	{
		int area1 = this.getSize().getWidth()*this.getSize().getHeight();
		int area2 = rv.getSize().getWidth()*rv.getSize().getHeight();
        
   	 if(area1 == area2){
            return 0;
            }
        return area1 < area2 ? 1 : -1;
    }
	
	/**
	 * Let the drawing class draw the rv.
	 * @param d: the drawing class
	 */
	public abstract void draw(IDraw d);
	
}
