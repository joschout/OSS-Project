package be.kuleuven.cs.oss.resourcevisualizations;

import java.util.TreeMap;

import be.kuleuven.cs.oss.resourceproperties.ResourceProperty;
import be.kuleuven.cs.oss.sonarfacade.Resource;

/**
 * A shape decider that is responsible for assigning different factories to different intervals
 * and passing through the created resource visualizations
 * 
 * @author jeroenreinenbergh
 *
 */
public class IntervalShapeDecider implements ResourceVisualizationCreator {
	private TreeMap<Double, ResourceVisualizationFactory>  boundaryToFactoryMap;
	private ResourceProperty resourceProperty;

	/**
	 * Creates a new interval shape decider
	 */
	public IntervalShapeDecider(){
		this.boundaryToFactoryMap = new TreeMap<Double, ResourceVisualizationFactory>();
	}
	
	public ResourceProperty getResourceProperty() {
		return resourceProperty;
	}

	public void setResourceProperty(ResourceProperty resourceProperty) {
		this.resourceProperty = resourceProperty;
	}

	public TreeMap<Double, ResourceVisualizationFactory> getBoundaryToFactoryMap() {
		return boundaryToFactoryMap;
	}
	
	/**
	 * Adds a boundary and corresponding resource visualization factory to the interval shape decider
	 * @param b the given boundary
	 * @param rvf the corresponding resource visualization factory
	 */
	public void addBoundaryWithFactory(double b, ResourceVisualizationFactory rvf){
		this.boundaryToFactoryMap.put(b, rvf);
	}

	@Override
	public ResourceVisualization create(Resource res) {
		double keyValue = getResourceProperty().getValue(res);
		if(!getBoundaryToFactoryMap().containsKey(keyValue)){
			keyValue = getBoundaryToFactoryMap().higherKey(keyValue);
		}
		ResourceVisualizationFactory factory = getBoundaryToFactoryMap().get(keyValue);
		return factory.create(res);
	}

}
