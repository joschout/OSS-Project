package be.kuleuven.cs.oss.charts;

import java.awt.image.BufferedImage;
import java.util.List;
import java.util.Map;

import be.kuleuven.cs.oss.datautils.Color;
import be.kuleuven.cs.oss.datautils.Position;
import be.kuleuven.cs.oss.datautils.Size;
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

	private double axisOffset; 
	private static final double OFFSET_SCALE = 0.1;
	private int width;
	private int height;
	
	
	private 
	
	public ScatterPlot(List<Resource> resources, 
			ResourceVisualizationFactory rvf, 
			SonarFacade sonarF, 
			ResourcePropertiesManager propManager,
			int width, int height) {
		super(resources, rvf, sonarF, propManager);
		setWidth(width);
		setHeight(height);
		
	}
	

	public int getHeight() {
		return height;
	}

	public void setHeight(int height) {
		this.height = height;
	}

	public int getWidth() {
		return width;
	}

	public void setWidth(int width) {
		this.width = width;
	}
	
	public double getAxisOffset() {
		return axisOffset;
	}

	public void setAxisOffset(double axisOffset) {
		this.axisOffset = axisOffset;
	}

	@Override
	public BufferedImage draw(IDraw d) {
		d.createEmptyImage(getWidth(), getHeight());
		drawAxises(d);
		computeScale();
		
		return d.getBufferedImage();
	
		computeScale();
		fillResourceVisuazations()
		
		
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
			ResourceVisualization rv = rvf.create(position, new Size(properties.get("width").intValue(), properties.get("height").intValue()), color, resource.getName() );
			this.rvs.add(rv);
		}
	}
	
	private void drawAxises(IDraw d){
	d.drawArrowRight( (int)getAxisOffset(), 
			(int)(getHeight()-getAxisOffset()) , 
			(int)(getWidth() -2*getAxisOffset()));
	d.drawArrowUp((int)getAxisOffset(),
			(int)(getHeight()- getAxisOffset()),
			(int)(getHeight()- 2*getAxisOffset()));
}
	
	/**
	 * Converts the given x-coordinate relative to the axises of the scatterplot
	 *  to an x-coordinate in the plane of the image.
	 *  
	 * @param xCoord
	 * @param xMax
	 * @return 
	 */
	private int convertX(double xCoord, double xMax){
		return (int) (getAxisOffset() + (width-2*getAxisOffset())* xCoord/xMax);	
	}
	
	private int convertYCoordToImageCoord(double yCoord, double yMax){
		return (int) (getHeight()-getAxisOffset()+(2*getAxisOffset()-getHeight())*yCoord/yMax);
	}

}
