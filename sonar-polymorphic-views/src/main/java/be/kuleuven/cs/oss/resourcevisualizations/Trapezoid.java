package be.kuleuven.cs.oss.resourcevisualizations;

import be.kuleuven.cs.oss.datautils.Color;
import be.kuleuven.cs.oss.datautils.Position;
import be.kuleuven.cs.oss.datautils.Size;
import be.kuleuven.cs.oss.drawingPackage.IDraw;

public class Trapezoid extends ResourceVisualization {

	private static final int TEXT_OFFSET = 1;
	private int baseLine;
	private int leftLine;
	private int rightLine;
	
	
	public Trapezoid(Position position, int leftLine, int baseLine, int rightLine, Color color, String name)
			throws IllegalArgumentException {
		super(position, new Size(baseLine, java.lang.Math.max(leftLine, rightLine)), color, name);
		this.baseLine = baseLine;
		this.leftLine = leftLine;
		this.rightLine = rightLine;
	}
	

	public int getBaseLine() {
		return baseLine;
	}


	public void setBaseLine(int baseLine) {
		this.baseLine = baseLine;
	}


	public int getLeftLine() {
		return leftLine;
	}


	public void setLeftLine(int leftLine) {
		this.leftLine = leftLine;
	}


	public int getRightLine() {
		return rightLine;
	}


	public void setRightLine(int rightLine) {
		this.rightLine = rightLine;
	}

	@Override
	public void draw(IDraw d) {
		d.drawTrapezoid(getX(), getY(), getLeftLine(), getBaseLine(), getRightLine(), getColor().getRed(), getColor().getGreen(), getColor().getBlue());
	}

}
