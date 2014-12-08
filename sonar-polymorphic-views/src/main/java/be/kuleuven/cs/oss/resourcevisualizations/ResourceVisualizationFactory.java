package be.kuleuven.cs.oss.resourcevisualizations;

import java.util.Map;

import be.kuleuven.cs.oss.sonarfacade.Resource;


/**
 * ResourceVisualizationFactory is an interface which should be implemented by all classes, whose instantiations act as factories for a specific kind of ResourceVisualization objects.
 * 
 * In other words, an instantiation of this class can create ResourceVisualization objects of a specific kind.
 * @author Jonas
 *
 */
public interface ResourceVisualizationFactory {
	
	/**
	 * Creates a ResourceVisualization for one specific resource, based on the given input map.
	 * The map contains the info for one specific resource (e. g. one class)
	 * and maps Strings onto values (doubles). 
	 *  -> The Strings are the names of resource properties of the resource
	 * 		(e.g. xmetric, ymetric, boxwidth, boxheight, colorR, ...)
	 *  -> the Doubles are the actual values of the resource properties for the resource 
	 * 
	 * @param map: maps the names of resource properties for one specific resource to their values
	 * @return the ResourceVisualization for one specific resource
	 */
	public ResourceVisualization create(Resource resource);

}
