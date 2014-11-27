package be.kuleuven.cs.oss.charts;

import java.awt.image.BufferedImage;
import java.util.List;
import java.util.Map;

import be.kuleuven.cs.oss.datautils.Color;
import be.kuleuven.cs.oss.datautils.Position;
import be.kuleuven.cs.oss.datautils.Size;
import be.kuleuven.cs.oss.drawingPackage.IDraw;
import be.kuleuven.cs.oss.drawingPackage.Java2DImpl;
import be.kuleuven.cs.oss.resourceproperties.ResourcePropertiesManager;
import be.kuleuven.cs.oss.resourceproperties.ResourceProperty;
import be.kuleuven.cs.oss.resourcevisualizations.ResourceVisualization;
import be.kuleuven.cs.oss.resourcevisualizations.ResourceVisualizationFactory;
import be.kuleuven.cs.oss.sonarfacade.Resource;
import be.kuleuven.cs.oss.sonarfacade.SonarFacade;

public class ScatterPlot extends Chart{

	private double axisOffset; 
	private static final double OFFSET_SCALE = 0.1;
	private int width;
	private int height;

	private int xMax;
	private int yMax;
	



	public ScatterPlot(List<Resource> resources, 
			ResourceVisualizationFactory rvf, 
			SonarFacade sonarF, 
			ResourcePropertiesManager propManager,
			int width, int height) {
		super(resources, rvf, sonarF, propManager);
		setWidth(width);
		setHeight(height);
		setXMax(0);
		setYMax(0);
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

	public int getXMax() {
		return xMax;
	}


	public void setXMax(int xMax) {
		this.xMax = xMax;
	}


	public int getYMax() {
		return yMax;
	}


	public void setYMax(int yMax) {
		this.yMax = yMax;
	}





	@Override
	public BufferedImage draw(IDraw d) {

		// 1) create an empty image
		d.createEmptyImage(getWidth(), getHeight());

		// 2) draw the axises on the image
		drawAxises(d);
		
		// 3) calculate the scale
		computeScale();
		
		// 4) create the ResourceVsiualizations
		createResourceVisualizations();
		
		// 5) draw the ResourceVisualizations
		drawResourceVisualizations();
		
		return d.getBufferedImage();
	}

	
	public void computeScale(){	
		double maxXCoordUpUntilNow = 0;
		double maxYCoordUpUntilNow = 0;
		
		for(Resource resource : resources){		
			double xCoord = getResourcePropertyValues(resource).get("xmetric");
			double yCoord = getResourcePropertyValues(resource).get("ymetric");
			
			if(xCoord > maxXCoordUpUntilNow){
				maxXCoordUpUntilNow = xCoord;
			}
			if(yCoord > maxYCoordUpUntilNow){
				maxYCoordUpUntilNow = yCoord;
			}			
		}
		setXMax((int)maxXCoordUpUntilNow);
		setYMax((int)maxYCoordUpUntilNow);	
	}
	
	private void createResourceVisualizations(){
		for(Resource resource: resources){
			Map<String, Double> properties = super.getResourcePropertyValues(resource);
			
			int xCoord = convertX(properties.get("xmetric"), getXMax());
			int yCoord = convertY(properties.get("ymetric"), getYMax());
			Position position = new Position(xCoord, yCoord);
			
			Color color = new Color(properties.get("colorR").intValue(),properties.get("colorB").intValue(),properties.get("colorG").intValue());
			Size size = new Size(properties.get("width").intValue(), properties.get("height").intValue());
			ResourceVisualization rv = rvf.create(position, size, color, resource.getName() );
			this.rvs.add(rv);
		}
	}
	
	private void drawResourceVisualizations(){
		for(ResourceVisualization rv: getResourceVisualizations()){
			rv.draw(getIDrawInstantiation());
		}
	}


	/**
	 * Draws the axises of the scatter plot. The axises consist of 2 arrows, 
	 * one pointing up and one pointing right, like a normal carthesian coordinate system.
	 * 
	 * @param d
	 */
	private void drawAxises(IDraw d){
		d.drawArrowRight( (int)getAxisOffset(), 
						  (int)(getHeight()-getAxisOffset()) , 
						  (int)(getWidth() -2*getAxisOffset()));
		d.drawArrowUp( (int)getAxisOffset(),
					   (int)(getHeight()- getAxisOffset()),
					   (int)(getHeight()- 2*getAxisOffset()));
	}

	/**
	 * Converts the given x-coordinate relative to the axises of the scatter plot
	 *  to an x-coordinate in the plane of the image.
	 *  
	 *  This method makes use of the following convention:
	 *  	- the origin in the image plane is located in the upper left corner of the image
	 *  	- the origin of the scatter plot is drawn near the lower left corner of the image:
	 *  		-> the point (0, 0) in the scatter plot is mapped to the point
	 *  		 (axisOffset, imageHeight - axisOffet)
	 *  		relative to the coordinate system of the image plane
	 *  
	 *  		=> the point (xCoord, yCoord) in the scatter plot is mapped to the point
	 *  		( axisOffset + (imageWidth - 2* axisOffset) * xCoord/xMax,
	 *  		 imageHeight - axisOffset +(2*axisOffset - imageHeight )* yCoord/yMax )
	 *  		relative to the coordinate system of the image plane
	 *  
	 * In this conversion, axisOffset denotes the offset between the border of image and the axises.
	 * xMax and yMax are the maximal values relative to the coordinate system of the scatter plot 
	 *  	that have to be drawn on the image.
	 *  
	 * @param xCoord
	 * @param xMax
	 * @return the x coordinate in the image plane
	 */
	private int convertX(double xCoord, double xMax){
		return (int) (getAxisOffset() + (width-2*getAxisOffset())* xCoord/xMax);	
	}

	/**
	 * Converts the given y-coordinate relative to the axises of the scatter plot
	 *  to an y-coordinate in the plane of the image.
	 *   
	 *  This method makes use of the following convention:
	 *  	- the origin in the image plane is located in the upper left corner of the image
	 *  	- the origin of the scatter plot is drawn near the lower left corner of the image:
	 *  		-> the point (0, 0) in the scatter plot is mapped to the point
	 *  		 (axisOffset, imageHeight - axisOffet)
	 *  		relative to the coordinate system of the image plane
	 *  
	 *  		=> the point (xCoord, yCoord) in the scatter plot is mapped to the point
	 *  		( axisOffset + (imageWidth - 2* axisOffset) * xCoord/xMax,
	 *  		 imageHeight - axisOffset +(2*axisOffset - imageHeight )* yCoord/yMax )
	 *  		relative to the coordinate system of the image plane
	 *  
	 * In this conversion, axisOffset denotes the offset between the border of image and the axises.
	 * xMax and yMax are the maximal values relative to the coordinate system of the scatter plot 
	 *  	that have to be drawn on the image.
	 *  
	 * @param yCoord
	 * @param yMax
	 * @return the y coordinate in the image plane
	 */
	private int convertY(double yCoord, double yMax){
		return (int) (getHeight()-getAxisOffset()+(2*getAxisOffset()-getHeight())*yCoord/yMax);
	}

}
