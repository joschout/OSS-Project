package be.kuleuven.cs.oss.resourcevisualizations;

import be.kuleuven.cs.oss.datautils.Color;
import be.kuleuven.cs.oss.datautils.Position;
import be.kuleuven.cs.oss.datautils.Size;

public class BoxBuilder {
	
	private static final int DEFAULT_XCOORD = 0;
	private static final int DEFAULT_YCOORD = 0;
	private static final int DEFAULT_WIDTH = 5;
	private static final int DEFAULT_HEIGHT = 5;
	private static final Color DEFAULT_COLOR = new Color(255, 255, 255);
	private static final String DEFAULT_NAME = "";
	
	
	private Box box;
	
	public BoxBuilder(){
		
	}

	public void createBox(){
		Position position = new Position(DEFAULT_XCOORD, DEFAULT_YCOORD);
		Size size = new Size(DEFAULT_WIDTH, DEFAULT_HEIGHT);
		Box box = new Box(position, size, DEFAULT_COLOR, DEFAULT_NAME);
		setBox(box);
		
	}

	public Box getBox() {
		return box;
	}

	public void setBox(Box box) {
		this.box = box;
	}
	
	public void setXCoorc(int xCoord){
		getBox().setPosition(new Position(xCoord, getBox().getPosition().getY()));
	}
	
	public void setYCoorc(int yCoord){
		getBox().setPosition(new Position(getBox().getPosition().getX(), yCoord));
	}
	
	
	public void setColor(Color color){
		getBox().setColor(color);
	}
	
	public void setName(String name){
		getBox().setName(name);
	}
	
	public void setWidth(int width){
		getBox().setSize(new Size(width, getBox().getHeight()));
	}
	
	public void setHeight(int height){
		getBox().setSize(new Size(getBox().getWidth(), height));
	}
	
}
