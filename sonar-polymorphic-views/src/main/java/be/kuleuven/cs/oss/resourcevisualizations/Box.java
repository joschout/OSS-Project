package be.kuleuven.cs.oss.resourcevisualizations;

import be.kuleuven.cs.oss.datautils.Color;
import be.kuleuven.cs.oss.datautils.Position;
import be.kuleuven.cs.oss.datautils.Size;
import be.kuleuven.cs.oss.drawingPackage.IDraw;

/**
 * Class representing Box resource visualizations.
 *
 */
public class Box extends ResourceVisualization{                     
	
	
	
	
	public Box(Position position, Size size, Color color, String name) throws IllegalArgumentException{
		super(position, size, color, name);
	}

	
	/**
	 * Let the drawing class draw the box
	 */
	@Override
	public void draw(IDraw d){
		d.drawBox(getX(), getY(), getWidth(), getHeight(), getColor().getRed(), getColor().getGreen(), getColor().getBlue());
		d.drawText(getName(), getX(), getY()- getHeight()/2 - getTextOffset(), 0, 0, 0, 0);

	}

	/**
	 * A box equals another box when all of its properties are the same
	 */
    @Override
    public boolean equals (Object other) {
        if ((other == null) || (this.getClass() != other.getClass())) {
            return false;
        }
        Box otherBox = (Box) other;
        return (this.getPosition().equals(otherBox.getPosition())
        		&& this.getSize().equals(otherBox.getSize())
        		&& this.getColor().equals(otherBox.getColor())
        		&& this.getName().equals(otherBox.getName()));
    }
	
	
}
