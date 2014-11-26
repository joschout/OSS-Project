package be.kuleuven.cs.oss.charts;

import java.awt.image.BufferedImage;
import java.util.List;
import java.util.Map;

import be.kuleuven.cs.oss.datautils.Color;
import be.kuleuven.cs.oss.datautils.Position;
import be.kuleuven.cs.oss.drawingPackage.IDraw;
import be.kuleuven.cs.oss.resourceproperties.ResourcePropertiesManager;
import be.kuleuven.cs.oss.resourceproperties.ResourceProperty;
import be.kuleuven.cs.oss.resourcevisualizations.Box;
import be.kuleuven.cs.oss.resourcevisualizations.BoxResourceVisualizationFactory;
import be.kuleuven.cs.oss.resourcevisualizations.ResourceVisualization;
import be.kuleuven.cs.oss.resourcevisualizations.ResourceVisualizationFactory;
import be.kuleuven.cs.oss.sonarfacade.Resource;
import be.kuleuven.cs.oss.sonarfacade.SonarFacade;

public class ScatterPlot extends Chart{

	public ScatterPlot(List<Resource> resources, 
			ResourceVisualizationFactory rvf, 
			SonarFacade sonarF, 
			ResourcePropertiesManager propManager) {
		super(resources, rvf, sonarF, propManager);
	}

	@Override
	public BufferedImage draw(IDraw drawInterface) {
		fillResourceVisualizations();
		ScatterPlotDrawing drawer = new ScatterPlotDrawing();
		drawer.
		
		
		for(ResourceVisualization rv: rvs){
			rv.draw(drawInterface);	
		}
		drawInterface.getBufferedImage();	
		
		return ;
	}
	
	private void fillResourceVisualizations(){
		for(Resource resource: resources){
			Map<String, Double> properties = super.getResourcePropertyValues(resource);
			Position position = new Position(properties.get("xcoordinate"), properties.get("ycoordinate"));
			Color color = new Color(properties.get("colorR").intValue(),properties.get("colorB").intValue(),properties.get("colorG").intValue());
			ResourceVisualization rv = rvf.create(position, properties.get("width"), properties.get("height"), color, resource.getName() );
			this.rvs.add(rv);
		}
	}

}
