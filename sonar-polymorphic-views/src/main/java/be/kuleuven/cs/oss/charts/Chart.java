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


public abstract class Chart {

	protected List<ResourceVisualization> rvs;
	protected List<Resource> resources;
	protected ResourceVisualizationFactory rvf;
	protected SonarFacade sonarF;
	protected ResourcePropertiesManager propManager;
	
	//TODO LineFactory has to be one of the arguments
	public Chart(List<Resource> resources, ResourceVisualizationFactory rvf, SonarFacade sonarF, ResourcePropertiesManager propManager) {
		this.resources = resources;
		this.rvf = rvf;
		this.sonarF = sonarF;
		this.propManager = propManager;
		
	}
	
	public Map<String, Double> getResourcePropertyValues(Resource r){
		return propManager.getPropertyValues(r);
	}
	
	
	public abstract BufferedImage draw(IDraw drawInterface);
	
	
}
	
	
