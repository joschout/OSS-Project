package be.kuleuven.cs.oss.lines;

import java.awt.image.BufferedImage;

import be.kuleuven.cs.oss.datautils.Color;
import be.kuleuven.cs.oss.datautils.Position;
import be.kuleuven.cs.oss.drawingPackage.IDraw;

public class Line {
	
protected final Position origin;
protected final Position destination;
protected final int width;
protected final Color color;

public Line(Position orig, Position dest, int w, Color c){
	this.origin = orig;
	this.destination = dest;
	this.width = w;
	this.color = c;
}

public BufferedImage draw(IDraw d){
	//TODO
	return new BufferedImage(null, null, false, null);
}
}
