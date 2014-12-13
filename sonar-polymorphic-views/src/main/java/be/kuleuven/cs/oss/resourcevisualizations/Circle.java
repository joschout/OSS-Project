package be.kuleuven.cs.oss.resourcevisualizations;

import be.kuleuven.cs.oss.datautils.Color;
import be.kuleuven.cs.oss.datautils.Position;
import be.kuleuven.cs.oss.datautils.Size;
import be.kuleuven.cs.oss.drawingPackage.IDraw;

public class Circle extends ResourceVisualization{


	private static final int TEXT_OFFSET = 1;
	private int radius;

	public Circle(Position position, int radius, Color color, String name) throws IllegalArgumentException {
		super(position, new Size(radius*2, radius*2), color, name);
		this.radius = radius;
	}


	@Override
	public void draw(IDraw d) {
		d.drawCircle(getX(), getY(), getWidth(), getHeight(),  getColor().getRed(), getColor().getGreen(), getColor().getBlue());
		d.drawText(getName(), getX(), getY()- getHeight()/2 - TEXT_OFFSET, 0, 0, 0, 0);
	}


	public int getRadius() {
		return getWidth()/2;
	}


}
