package be.kuleuven.cs.oss.resourcevisualizations;

import java.util.Map;

import be.kuleuven.cs.oss.datautils.Color;
import be.kuleuven.cs.oss.datautils.Position;
import be.kuleuven.cs.oss.datautils.Size;

public class BoxFactory implements ResourceVisualizationFactory {

	private static final int DEFAULT_XCOORD = 0;
	private static final int DEFAULT_YCOORD = 0;
	private static final int DEFAULT_WIDTH = 5;
	private static final int DEFAULT_HEIGHT = 5;
	private static final Color DEFAULT_COLOR = new Color(255, 255, 255);
	private static final String DEFAULT_NAME = "";
	
	public BoxFactory() {
	}
	 
	@Override
	public ResourceVisualization create(Map<String, Double> map) {
		
		Position position = new Position(DEFAULT_XCOORD, DEFAULT_YCOORD);
		Size size = new Size(DEFAULT_WIDTH, DEFAULT_HEIGHT);
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
