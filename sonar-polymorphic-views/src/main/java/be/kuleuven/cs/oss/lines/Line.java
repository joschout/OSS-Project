package be.kuleuven.cs.oss.lines;

import be.kuleuven.cs.oss.datautils.Color;
import be.kuleuven.cs.oss.datautils.Position;
import be.kuleuven.cs.oss.drawingPackage.IDraw;


/**
 * An abstract class that should be extended by all classes that represent some kind of line
 * 
 * @author jeroenreinenbergh
 *
 */
public abstract class Line {

	private final Position origin;
	private final Position destination;
	private final int width;
	private final Color color;

	public Position getOrigin() {
		return origin;
	}

	public Position getDestination() {
		return destination;
	}

	public int getWidth() {
		return width;
	}

	public Color getColor() {
		return color;
	}

	/**
	 * Creates a new line
	 * @param orig the given origin
	 * @param dest the given destination
	 * @param w the given width
	 * @param c the given color
	 */
	public Line(Position orig, Position dest, int w, Color c){
		this.origin = orig;
		this.destination = dest;
		this.width = w;
		this.color = c;
	}

	/**
	 * Draw the line
	 * @param d an instance of a drawing interface
	 */
	public void draw(IDraw d){
		d.drawStraightLine(getOrigin().getX(), getOrigin().getY(), getDestination().getX(), getDestination().getY(), getColor().getRed(), getColor().getGreen(), getColor().getBlue(), getWidth());
	}

	@Override
	public boolean equals (Object other) {
		if ((other == null) || (this.getClass() != other.getClass())) {
			return false;
		}
		Line otherLine = (Line) other;
		return (this.getOrigin().equals(otherLine.getOrigin())
				&& this.getDestination().equals(otherLine.getDestination())
				&& this.getColor().equals(otherLine.getColor())
				&& (this.getWidth() == otherLine.getWidth()));
	}
}
