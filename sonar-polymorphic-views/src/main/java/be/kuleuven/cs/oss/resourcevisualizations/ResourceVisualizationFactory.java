package be.kuleuven.cs.oss.resourcevisualizations;

import java.util.Map;


/**
 * ResourceVisualizationFactory is an interface which should be implemented by all classes whose objects act as factories for a specific kind of ResourceVisualization objects.
 * @author Jonas
 *
 */
public interface ResourceVisualizationFactory {
	
	
	public ResourceVisualization create(Map<String,Double> map);

}
