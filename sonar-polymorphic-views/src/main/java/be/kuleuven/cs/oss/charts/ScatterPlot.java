package be.kuleuven.cs.oss.charts;

import java.awt.image.BufferedImage;
import java.util.List;

import be.kuleuven.cs.oss.datautils.Position;
import be.kuleuven.cs.oss.drawingPackage.IDraw;
import be.kuleuven.cs.oss.resourceproperties.ResourceProperty;
import be.kuleuven.cs.oss.resourcevisualizations.Box;
import be.kuleuven.cs.oss.resourcevisualizations.BoxResourceVisualizationFactory;
import be.kuleuven.cs.oss.resourcevisualizations.ResourceVisualization;
import be.kuleuven.cs.oss.resourcevisualizations.ResourceVisualizationFactory;
import be.kuleuven.cs.oss.sonarfacade.Resource;
import be.kuleuven.cs.oss.sonarfacade.SonarFacade;

public class ScatterPlot extends Chart{

	public ScatterPlot(List<Resource> resources, ResourceVisualizationFactory RVF, List<ResourceProperty> resourceProps, SonarFacade sonarF) {
		super(resources, RVF, sonarF);
	}

	@Override
	public BufferedImage draw(IDraw drawInterface) {
		fillResourceVisualizations;
		for(ResourceVisualization rv: RVs){
			rv.draw(IDraw);
			IDraw.getBufferedImage();
		}
			
		return null;
	}
	
	private void fillResourceVisualizations(){
		for(Resource resource: resources){
			Map<string, Double> properties = super.getResourcePropertyValues(resource);
			Position position = new Position(properties.get("xcoordinate"), properties.get("ycoordinate"));
			Box box = BoxResourceVisualizationFactory.create(position, properties.get("width"), properties.get("height"), properties.get("color"));
			this.rvs.add(box);
		}
	}

}
