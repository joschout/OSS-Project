package be.kuleuven.cs.oss.charts;

import java.awt.image.BufferedImage;
import java.util.List;

import be.kuleuven.cs.oss.drawingPackage.IDraw;
import be.kuleuven.cs.oss.resourceproperties.ResourceProperty;
import be.kuleuven.cs.oss.resourcevisualizations.ResourceVisualization;
import be.kuleuven.cs.oss.resourcevisualizations.ResourceVisualizationFactory;
import be.kuleuven.cs.oss.sonarfacade.Resource;
import be.kuleuven.cs.oss.sonarfacade.SonarFacade;

public abstract class Chart {

	protected List<ResourceVisualization> RVs;
	protected List<Resource> resources;
	protected ResourceVisualizationFactory RVF;
	protected List<ResourceProperty> resourceProps;
	protected SonarFacade sonarF;
	
	//TODO LineFactory has tob e one of the arguments
	public Chart(List<Resource> resources, ResourceVisualizationFactory RVF, List<ResourceProperty> resourceProps, SonarFacade sonarF) {
		this.resources = resources;
		this.RVF = RVF;
		this.resourceProps = resourceProps;
		this.sonarF = sonarF;
		
	}
	
	
	public abstract BufferedImage draw(IDraw drawInterface);
	
	
}
	
	
