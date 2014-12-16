package be.kuleuven.cs.oss.resourcevisualizations;

import java.util.TreeMap;

import be.kuleuven.cs.oss.resourceproperties.ResourceProperty;
import be.kuleuven.cs.oss.sonarfacade.Resource;

public class IntervalShapeDecider implements ResourceVisualizationCreator {
	
	private TreeMap<Double, ResourceVisualizationCreator>  boundaryToFactoryMap;
	private ResourceProperty resourceProperty;

	public ResourceProperty getResourceProperty() {
		return resourceProperty;
	}

	public void setResourceProperty(ResourceProperty resourceProperty) {
		this.resourceProperty = resourceProperty;
	}

	public TreeMap<Double, ResourceVisualizationCreator> getBoundaryToFactoryMap() {
		return boundaryToFactoryMap;
	}
	
	public void addBoundaryWithFactory(double b, ResourceVisualizationCreator rvf){
		this.boundaryToFactoryMap.put(b, rvf);
	}

	@Override
	public ResourceVisualization create(Resource res) {
		double keyValue = getResourceProperty().getValue(res);
		if(!getBoundaryToFactoryMap().containsKey(keyValue)){
			keyValue = getBoundaryToFactoryMap().higherKey(keyValue);
		}
		ResourceVisualizationCreator factory = getBoundaryToFactoryMap().get(keyValue);
		return factory.create(res);
	}

}
