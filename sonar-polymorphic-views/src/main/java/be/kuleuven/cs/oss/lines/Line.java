package be.kuleuven.cs.oss.lines;

import java.awt.image.BufferedImage;

import be.kuleuven.cs.oss.datautils.Color;
import be.kuleuven.cs.oss.datautils.Position;
import be.kuleuven.cs.oss.drawingPackage.IDraw;

/**
 * 
 * @author jeroenreinenbergh
 *
 */
public abstract class Line {
	
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

/**
 * Draw the line with its given characteristics
 * @param d
 */
public void draw(IDraw d){
	d.drawStraightLine(origin.getX(), origin.getY(), destination.getX(), destination.getY(), color.getRed(), color.getGreen(), color.getBlue(), width);
}
}
