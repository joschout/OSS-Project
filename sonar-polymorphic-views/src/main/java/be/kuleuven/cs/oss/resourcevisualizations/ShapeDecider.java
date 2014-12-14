package be.kuleuven.cs.oss.resourcevisualizations;

import be.kuleuven.cs.oss.sonarfacade.Resource;

public interface ShapeDecider {
	
	public ResourceVisualization create(Resource res);
	
	
}
