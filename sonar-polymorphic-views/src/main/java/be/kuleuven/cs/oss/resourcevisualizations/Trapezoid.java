package be.kuleuven.cs.oss.resourcevisualizations;

import be.kuleuven.cs.oss.datautils.Color;
import be.kuleuven.cs.oss.datautils.Position;
import be.kuleuven.cs.oss.datautils.Size;
import be.kuleuven.cs.oss.drawingPackage.IDraw;

public class Trapezoid extends ResourceVisualization {

	
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

	private void setBaseLine(int baseLine) {
		this.baseLine = baseLine;
	}

	public int getLeftLine() {
		return leftLine;
	}

	private void setLeftLine(int leftLine) {
		this.leftLine = leftLine;
	}

	public int getRightLine() {
		return rightLine;
	}

	private void setRightLine(int rightLine) {
		this.rightLine = rightLine;
	}
	
	public void setSize(Size nsize){
		super.setSize(nsize);
		setBaseLine(nsize.getWidth());	
		if (getLeftLine() >= getRightLine() && getLeftLine() != 0){
			double newRight = (double)getRightLine()/(double)getLeftLine()*(double)nsize.getHeight();
			setRightLine((int)newRight);
			setLeftLine(nsize.getHeight());
		}
		else if (getRightLine() > getLeftLine() && getRightLine() != 0 ){
			double newLeft = (double)getLeftLine()/(double)getRightLine()*(double)nsize.getHeight();
			setLeftLine((int)newLeft);
			setRightLine(nsize.getHeight());
		}
		
		else {
			setRightLine(nsize.getHeight());
			setLeftLine(nsize.getHeight());
		}
	}
	
	@Override
	public void draw(IDraw d) {
		d.drawTrapezoid(getX(), getY(),
				getLeftLine(), getBaseLine(), getRightLine(),
				getColor().getRed(), getColor().getGreen(), getColor().getBlue());
		d.drawText(getName(), getX(), getY()- getHeight()/2 - getTextOffset(), 0, 0, 0, 0);
	}

    @Override
    public boolean equals (Object other) {
        if ((other == null) || (this.getClass() != other.getClass())) {
            return false;
        }
        Trapezoid otherTrapezoid = (Trapezoid) other;
        return (this.getPosition().equals(otherTrapezoid.getPosition())
        		&& this.getSize().equals(otherTrapezoid.getSize())
        		&& this.getColor().equals(otherTrapezoid.getColor())
        		&& this.getName().equals(otherTrapezoid.getName())
        		&& (this.getBaseLine() == otherTrapezoid.getBaseLine())
        		&& (this.getLeftLine() == otherTrapezoid.getLeftLine())
        		&& (this.getRightLine() == otherTrapezoid.getRightLine()));
    }
	
	
}
