package be.kuleuven.cs.oss.lines;

import be.kuleuven.cs.oss.datautils.Color;
import be.kuleuven.cs.oss.datautils.Position;
import be.kuleuven.cs.oss.drawingPackage.IDraw;

/**
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

	public Line(Position orig, Position dest, int w, Color c){
		this.origin = orig;
		this.destination = dest;
		this.width = w;
		this.color = c;
	}

	/**
	 * Draw the line with its given characteristics
	 * @param d
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
