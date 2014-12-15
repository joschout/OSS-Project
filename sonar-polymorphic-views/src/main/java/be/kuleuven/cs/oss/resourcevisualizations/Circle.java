package be.kuleuven.cs.oss.resourcevisualizations;


import be.kuleuven.cs.oss.datautils.Color;
import be.kuleuven.cs.oss.datautils.Position;
import be.kuleuven.cs.oss.datautils.Size;
import be.kuleuven.cs.oss.drawingPackage.IDraw;

public class Circle extends ResourceVisualization{


	private static final int TEXT_OFFSET = 1;
	private int diameter;

	public Circle(Position position, int diameter, Color color, String name) throws IllegalArgumentException {
		super(position, new Size(diameter, diameter), color, name);  
	}


	@Override
	public void draw(IDraw d) {
		d.drawCircle(getX(), getY(), getDiameter(),  getColor().getRed(), getColor().getGreen(), getColor().getBlue());
		d.drawText(getName(), getX(), getY()- getHeight()/2 - TEXT_OFFSET, 0, 0, 0, 0);
	}


	public int getDiameter() {
		return diameter;
	}
	private void setDiameter(int diameter){
		this.diameter = diameter;
	}


	public void setSize(Size size){
		if(getSize() != size){ 
			super.setSize(size);
			setDiameter(size.getWidth());
			}

	}

    @Override
    public boolean equals (Object other) {
        if ((other == null) || (this.getClass() != other.getClass())) {
            return false;
        }
        Circle otherCircle = (Circle) other;
        return (this.getPosition().equals(otherCircle.getPosition())
        		&& this.getSize().equals(otherCircle.getSize())
        		&& this.getColor().equals(otherCircle.getColor())
        		&& this.getName().equals(otherCircle.getName())
        		&& (this.getDiameter() == otherCircle.getDiameter()));
    }
	


}
