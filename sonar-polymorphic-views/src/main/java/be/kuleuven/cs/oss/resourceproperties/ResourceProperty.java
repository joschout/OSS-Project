package be.kuleuven.cs.oss.resourceproperties;

import be.kuleuven.cs.oss.sonarfacade.Resource;

/**
 * Interface for the resource properties. This interface is used to apply polymorphism to the 
 * ResourceProperties
 */

public interface ResourceProperty {
	
	/**
	 * Returns the value of this Resource Property
	 * 
	 * @param the Resource to get the property of
	 * 
	 * @return the value of the Resource Property
	 */
	
	public Double getValue(Resource r);
	
	/**
	 * Gets the name of the metric used for this resource property
	 * 
	 * @return the metric name
	 */
	public String getPropertyName();
	
	
	
}
