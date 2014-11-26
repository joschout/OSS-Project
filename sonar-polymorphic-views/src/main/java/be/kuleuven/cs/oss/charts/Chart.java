package be.kuleuven.cs.oss.charts;

import java.awt.image.BufferedImage;
import java.util.List;
import java.util.Map;

import be.kuleuven.cs.oss.drawingPackage.IDraw;
import be.kuleuven.cs.oss.resourceproperties.ResourcePropertiesManager;
import be.kuleuven.cs.oss.resourcevisualizations.ResourceVisualization;
import be.kuleuven.cs.oss.resourcevisualizations.ResourceVisualizationFactory;
import be.kuleuven.cs.oss.sonarfacade.Resource;
import be.kuleuven.cs.oss.sonarfacade.SonarFacade;


/**
 * @author Milan
 *
 * The superclass of SysCom and ScatterPlot.
 * 
 */

public abstract class Chart {

	
	protected List<ResourceVisualization> rvs;
	protected List<Resource> resources;
	protected ResourceVisualizationFactory rvf;
	protected SonarFacade sonarF;
	protected ResourcePropertiesManager propManager;
	
	//TODO LineFactory has to be one of the arguments
	/**
	 * Declare the different fields which are used to store data about different charts
	 * 
	 * @param List<Resource> resources
	 * @param ResourceVisualizationFactory rvf
	 * @param SonarFacade sonarF
	 * @param ResourcePropertiesManager propManager
	 */
	public Chart(List<Resource> resources, ResourceVisualizationFactory rvf, SonarFacade sonarF, ResourcePropertiesManager propManager) {
		this.resources = resources;
		this.rvf = rvf;
		this.sonarF = sonarF;
		this.propManager = propManager;
		
	}
	
	/**
	 * 
	 * gets the different values for each metric for a certain resource
	 * 
	 * @param Resource resource  
	 * @return Map<String, Double> with the values for different metrics for a certain resource
	 */
	public Map<String, Double> getResourcePropertyValues(Resource resource){
		return propManager.getPropertyValues(resource);
	}
	
	
	public abstract BufferedImage draw(IDraw drawInterface);
	
	
}
	
	
