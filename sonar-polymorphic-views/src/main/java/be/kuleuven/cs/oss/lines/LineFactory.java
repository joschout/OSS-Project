package be.kuleuven.cs.oss.lines;

import be.kuleuven.cs.oss.datautils.Color;
import be.kuleuven.cs.oss.datautils.Position;

public interface LineFactory {

	public Line create();
	
	public Line create(Position orig, Position dest);
	
	public Line create(Position orig, Position dest, int width);
	
	public Line create(Position orig, Position dest, int width, Color color);


}
