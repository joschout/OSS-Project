package be.kuleuven.cs.oss.charts;

import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import be.kuleuven.cs.oss.datautils.Position;
import be.kuleuven.cs.oss.datautils.Size;
import be.kuleuven.cs.oss.drawingPackage.IDraw;
import be.kuleuven.cs.oss.resourceproperties.ResourcePropertiesManager;
import be.kuleuven.cs.oss.resourcevisualizations.ResourceVisualization;
import be.kuleuven.cs.oss.resourcevisualizations.ResourceVisualizationFactory;
import be.kuleuven.cs.oss.resourcevisualizations.BoxFactory; //eventually to remove
import be.kuleuven.cs.oss.sonarfacade.Resource;
import be.kuleuven.cs.oss.sonarfacade.SonarFacade;

public class ScatterPlot extends Chart{

	//Image frame properties
	private double axisOffset; 
	private int width;
	private int height;
	
	//global maxvalues over all resources
	private int xMax;
	private int yMax;
	private int widthMax;
	private int heightMax;
	//global minvalues over all resources
	private int xMin;
	private int yMin;
	private int widthMin;
	private int heightMin;
	
	//the values for the dimensions of the smallest and largest box in the plot
	//each value is calculated by multiplying the width or height of the frame with the factors
	private double minRVHeight;
	private double maxRVHeight;
	private double maxRVWidth;
	private double minRVWidth;
	private static final double minRVScalingFactor = 0.05;
	private static final double maxRVScalingFactor = 0.15;
	
	/**
	 * constructor 
	 * 
	 * @param resources
	 * @param rvf
	 * @param sonarF
	 * @param propManager
	 * @param width: width of the image frame
	 * @param height: height of the image frame
	 */
	public ScatterPlot(List<Resource> resources, 
			ResourceVisualizationFactory rvf, 
			SonarFacade sonarF, 
			ResourcePropertiesManager propManager,
			int width, int height) {
		super(resources, rvf, sonarF, propManager);
		this.width=width;
		this.height = height;
		this.xMax = 0;
		this.yMax = 0;
		this.widthMax = 0;
		this.heightMax = 0;
		this.widthMin = Integer.MAX_VALUE;
		this.heightMin = Integer.MAX_VALUE;
		this.xMin = Integer.MAX_VALUE;
		this.yMin = Integer.MAX_VALUE;
		
		this.axisOffset = (minRVScalingFactor+maxRVScalingFactor)/2*Math.min(width, height);
		
		minRVHeight= minRVScalingFactor*height;
		maxRVHeight = maxRVScalingFactor*height;
		maxRVWidth = maxRVScalingFactor*width;
		minRVWidth = minRVScalingFactor*width;
	}



	@Override
	public BufferedImage draw() {

		// 1) create an empty image
		getIDrawInstantiation().createEmptyImage(width, height);

		// 2) draw the axises on the image
		drawAxises(getIDrawInstantiation());
		
		// 
		setDefaultRVSizes();
		
		// 3) create the ResourceVsiualizations
		createResourceVisualizations();
			
		// 5) rescale the ResourceVsiualizations
		rescaleResourceVisualizations();
		
		// 6) draw the ResourceVisualizations
		drawResourceVisualizations();
		
		return getIDrawInstantiation().getBufferedImage();
	}

	private void setDefaultRVSizes() { //Hacky code. Change requested.
		((BoxFactory)this.rvf).setDefaultHeight((int)((maxRVScalingFactor+minRVScalingFactor)/2*(height-2*axisOffset)));
		((BoxFactory)this.rvf).setDefaultWidth((int)((maxRVScalingFactor+minRVScalingFactor)/2*(width-2*axisOffset)));
	}

	
	private void rescaleResourceVisualizations(){
		setExtremeValues();	
		for (ResourceVisualization rv : this.getResourceVisualizations()){
			rv.setPosition(new Position(convertX(rv.getX()), convertY(rv.getY())));
			int widthpx = widthMin;
			int heightpx = heightMin;
			if(widthMin != widthMax){
				widthpx = convertWidth(rv.getWidth());
			}
			if(heightMin != heightMax){
				heightpx = convertHeight(rv.getHeight());
			}
			rv.setSize(new Size(widthpx, heightpx));
		}	
	}
	

	
	private void setExtremeValues(){	
		for(ResourceVisualization rv : rvs){		
			double xCoord = rv.getX();
			double yCoord = rv.getY();
			double width = rv.getWidth();
			double height = rv.getHeight();
			
			if(xCoord > xMax ){
				xMax = ((int)xCoord);
			}
			if(yCoord > yMax){
				yMax = ((int)yCoord);
			}			
			if(width > widthMax){
				width = ((int)width);
			}	
			if(height > heightMax){
				height = ((int)height);
			}
			
			
			if(xCoord < xMin){
				xMax = ((int)xCoord);
			}
			if(yCoord < yMin){
				yMin = ((int)yCoord);;
			}			
			if(width < widthMin){
				widthMin = ((int)width);
			}	
			if(height < heightMin){
				heightMin = ((int)height);
			}	
		}
	}
	
	private void createResourceVisualizations(){
		for(Resource resource: resources){
			Map<String, Double> properties = super.getResourcePropertyValues(resource);
			ResourceVisualization rv = rvf.create(properties);
			this.getResourceVisualizations().add(rv);
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
		d.drawArrowRight( (int)axisOffset, 
						  (int)(height-axisOffset) , 
						  (int)(width-2*axisOffset));
		d.drawArrowUp( (int)axisOffset,
					   (int)(height -  axisOffset),
					   (int)(height- 2*axisOffset));
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
	private int convertX(double xCoord){
		return (int) (axisOffset + (width-2*axisOffset)* (xCoord-xMin)/(xMax-xMin));	
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
	private int convertY(double yCoord){
		return (int) (height-axisOffset+(2*axisOffset-height)*(yCoord-yMin)/(yMax-yMin));
	}
	
	private int convertWidth(double width){
		return (int) (minRVWidth+(maxRVWidth-minRVWidth)*(width-widthMin)/(widthMax-widthMin));
	}

	private int convertHeight(double height){
		return (int) (minRVHeight+(maxRVHeight-minRVHeight)*(height-heightMin)/(heightMax-heightMin));
	}
}
