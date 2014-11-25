package be.kuleuven.cs.oss.charts;

import java.awt.image.BufferedImage;
import java.util.List;

import be.kuleuven.cs.oss.drawingPackage.IDraw;
import be.kuleuven.cs.oss.resourcevisualizations.ResourceVisualization;
import be.kuleuven.cs.oss.resourcevisualizations.ResourceVisualizationFactory;
import be.kuleuven.cs.oss.sonarfacade.Resource;

public abstract class IChart {

	protected List<ResourceVisualization> RVs;
	protected List<Resource> resources;
	protected ResourceVisualizationFactory RVF;
	
	public abstract BufferedImage draw(IDraw drawInterface);
	
	
}
	
	
