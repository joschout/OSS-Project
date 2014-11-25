package be.kuleuven.cs.oss.datautils;

import java.awt.image.BufferedImage;

import be.kuleuven.cs.oss.drawingPackage.IDraw;
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

public BufferedImage draw(IDraw d){
	//TODO
	return new BufferedImage(null, null, false, null);
}

}
