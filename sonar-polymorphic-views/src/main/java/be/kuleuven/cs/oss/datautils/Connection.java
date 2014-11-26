package be.kuleuven.cs.oss.datautils;

import be.kuleuven.cs.oss.drawingPackage.IDraw;
import be.kuleuven.cs.oss.lines.Line;
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

public void draw(IDraw d, LineFactory lf){
	Double startPointYOffset = -origin.getHeight()/2;
	Position startPoint = new Position(origin.getPosition().getX(),origin.getPosition().getY()+startPointYOffset);
	Double endPointYOffset = destination.getHeight()/2;
	Position endPoint = new Position(destination.getPosition().getX(),destination.getPosition().getY()+endPointYOffset);
	Line line = lf.create(startPoint, endPoint);
	line.draw(d); //Adjusted by Lennart De Graef
}

}
