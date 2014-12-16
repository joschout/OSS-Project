package be.kuleuven.cs.oss.resourcevisualizations;


import be.kuleuven.cs.oss.sonarfacade.Resource;


/**
 * An interface that should be implemented by all classes that are able to return a resource visualization when a resource is given
 * 
 * @author jeroenreinenbergh
 *
 */
public interface ResourceVisualizationCreator {
	
	/**
	 * Creates a resource visualization that represents the given resource
	 * @param resource the given resource
	 * @return the created resource visualization that represents the given resource
	 */
	public ResourceVisualization create(Resource resource);

}
