package be.kuleuven.cs.oss.datautils;

import java.awt.image.BufferedImage;

import be.kuleuven.cs.oss.drawingPackage.IDraw;
import be.kuleuven.cs.oss.lines.LineFactory;
import be.kuleuven.cs.oss.resourcevisualizations.ResourceVisualization;

public class Connection {
	
//parent	
private final ResourceVisualization origin;
//child
private final ResourceVisualization destination;

public Connection(ResourceVisualization orig, ResourceVisualization dest){
	this.origin = orig;
	this.destination = dest;
}

public ResourceVisualization getOrigin() {
	return origin;
}

public ResourceVisualization getDestination() {
	return destination;
}

public BufferedImage draw(IDraw d, LineFactory lf){
	int startPointYOffset = -origin.getHeight()/2;
	Position startPoint = new Position(origin.getPosition().getX(),origin.getPosition().getY()+startPointYOffset);
	int endPointYOffset = +destination.getHeight()/2;
	Position endPoint = new Position(destination.getPosition().getX(),destination.getPosition().getY()+endPointYOffset);
	Line line = lf.create(startPoint, endPoint);
	return new BufferedImage(null, null, false, null);
}

}
