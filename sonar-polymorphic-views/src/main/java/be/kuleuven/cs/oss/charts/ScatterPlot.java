package be.kuleuven.cs.oss.charts;

import java.awt.image.BufferedImage;
import java.util.List;

import be.kuleuven.cs.oss.drawingPackage.IDraw;
import be.kuleuven.cs.oss.resourceproperties.ResourceProperty;
import be.kuleuven.cs.oss.resourcevisualizations.ResourceVisualization;
import be.kuleuven.cs.oss.resourcevisualizations.ResourceVisualizationFactory;
import be.kuleuven.cs.oss.sonarfacade.Resource;
import be.kuleuven.cs.oss.sonarfacade.SonarFacade;

public class ScatterPlot extends Chart{

	public ScatterPlot(List<Resource> resources, ResourceVisualizationFactory RVF, List<ResourceProperty> resourceProps, SonarFacade sonarF) {
		super(resources, RVF, resourceProps, sonarF);
	}

	@Override
	public BufferedImage draw(IDraw drawInterface) {
		fillResourceVisualizations;
		for(ResourceVisualization rv: RVs){
			rv.draw(IDraw);
		}
			
		return null;
	}
	
	private void fillResourceVisualizations(){
		
	}

}
