package be.kuleuven.cs.oss.resourcevisualizations;


import be.kuleuven.cs.oss.resourceproperties.ResourceProperty;


/**
 * ResourceVisualizationFactory is an interface which should be implemented by all classes, whose instantiations act as factories for a specific kind of ResourceVisualization object.
 * 
 * In other words, an instantiation of this class can create ResourceVisualization objects of a specific kind.
 * @author Jonas
 *
 */
public interface ResourceVisualizationFactory extends ResourceVisualizationCreator{
	
	public void setRedProperty(ResourceProperty redProperty);
	
	public void setGreenProperty(ResourceProperty redProperty);
	
	public void setBlueProperty(ResourceProperty redProperty);

}