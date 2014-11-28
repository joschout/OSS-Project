package be.kuleuven.cs.oss.charts;

import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import be.kuleuven.cs.oss.datautils.Position;
import be.kuleuven.cs.oss.datautils.Size;
import be.kuleuven.cs.oss.drawingPackage.IDraw;
import be.kuleuven.cs.oss.polymorphicviews.plugin.PolymorphicViewsChart;
import be.kuleuven.cs.oss.resourceproperties.ResourcePropertiesManager;
import be.kuleuven.cs.oss.resourceproperties.ResourceProperty;
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
	
	private final static Logger LOG = LoggerFactory.getLogger(PolymorphicViewsChart.class);
	
	/**
	 * constructor 
	 * 
	 * @param resources: The classes or packages to appear on the plot
	 * @param rvf: The ResourceVisualisationFactory which makes a ResourceVisualisation for each resource (such as a Box)
	 * @param sonarF: Inherited. We don't actually need it. Can be used to ask Sonar the values of extra metrics.
	 * @param propManager: Inherited
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
		this.axisOffset = (minRVScalingFactor+maxRVScalingFactor)/2*Math.min(width, height);
		
		this.xMax = 0;
		this.yMax = 0;
		this.widthMax = 0;
		this.heightMax = 0;
		this.widthMin = Integer.MAX_VALUE;
		this.heightMin = Integer.MAX_VALUE;
		this.xMin = Integer.MAX_VALUE;
		this.yMin = Integer.MAX_VALUE;
		
		minRVHeight= minRVScalingFactor*height;
		maxRVHeight = maxRVScalingFactor*height;
		maxRVWidth = maxRVScalingFactor*width;
		minRVWidth = minRVScalingFactor*width;
		LOG.info("ScatterPlot constructed");
	}


	/**
	 * The draw method goes through several steps
	 * 1. create an empty image
	 * 2. draw the axes on the image
	 * 3. based on the size of the image, calculate a default resource visualisation size (e.g.boxwidth and boxheight)
	 * 4. create the resource visualisations (such as boxes)
	 * 5. modify/scale the values of resource visualisations in order to make the resource visualisation ready to be drawn
	 * 		(the values are now expressed in pixels)
	 * 6. draw the resource visualisations
	 * @return the buffered image of the plot
	 */
	@Override
	public BufferedImage draw() {
		LOG.info("In draw method in ScatterPlot");
		//1
		getIDrawInstantiation().createEmptyImage(width, height);
		LOG.info("empty frame made");
		//2
		drawAxises(getIDrawInstantiation());
		LOG.info("Axes drawn on frame");
		//3
		setDefaultRVSizes();
		LOG.info("defaultRVSizes set");
		//4
		createResourceVisualizations();
		LOG.info("RVs created");
		//5
		rescaleResourceVisualizations();
		LOG.info("RVs scaled");
		//6
		drawResourceVisualizations();
		LOG.info("RVs drawn");
		//7
		drawAxesLabels(getIDrawInstantiation());
		LOG.info("Axes's labels drawn");
		
		return getIDrawInstantiation().getBufferedImage();
	}

	private void drawAxesLabels(IDraw d){
		ResourceProperty xprop = propManager.getResourceProperty("xmetric");
		String xname = xprop.getPropertyName();
		ResourceProperty yprop = propManager.getResourceProperty("ymetric");
		String yname = yprop.getPropertyName();
		
		d.drawText("" +xMax, (int)(width- axisOffset/2), (int)(height - axisOffset/2));
		d.drawText(xname, (int)(axisOffset + width/2), (int)(height - axisOffset/2));
		
		d.drawText("" +yMax, (int)(axisOffset/2), (int)axisOffset, -90, 0, 0, 0);
		d.drawText(yname, (int)(axisOffset/2) , (int)(axisOffset + height/2), -90, 0, 0, 0);
	}
	
	
	/**
	 * Draws the axes of the scatter plot. The axes consist of 2 arrows, 
	 * one pointing up and one pointing right, like a normal Cartesian coordinate system.
	 * 
	 * @param d : IDraw
	 */
	private void drawAxises(IDraw d){
		d.drawArrowRight( (int)axisOffset, 
						  (int)(height - axisOffset) , 
						  (int)(width - 2*axisOffset));
		d.drawArrowUp( (int)axisOffset,
					   (int)(height - axisOffset),
					   (int)(height - 2*axisOffset));
	}

	/**
	 * Before the resource visualizations are made, the default values for its dimensions are set.
	 * Here we choose for the average of the minimum and maximum size for a resource visualisation.
	 */
	private void setDefaultRVSizes() { //Hacky code. Change requested. No casting wanted.
		((BoxFactory)this.rvf).setDefaultHeight((int)((maxRVScalingFactor+minRVScalingFactor)/2*(height-2*axisOffset)));
		((BoxFactory)this.rvf).setDefaultWidth((int)((maxRVScalingFactor+minRVScalingFactor)/2*(width-2*axisOffset)));
	}

	/**
	 * Get the measures for the given metrics and put them in a resource visualisation.
	 * If there are no metrics for a certain property of the resource visualisation, it gets the default value.
	 */
	private void createResourceVisualizations(){
		LOG.info("Starting creation of RVs");
		for(Resource resource: resources){
			String name = resource.getName();
			Map<String, Double> properties = super.getResourcePropertyValues(resource);
			LOG.info("MAP PROPERIES: " + properties.toString());
			ResourceVisualization rv = rvf.create(properties);
			rv.setName(name);
			LOG.info("RV: " + rv.toString());
			this.getResourceVisualizations().add(rv);
		}
	}

	/**
	 * Using interpolation, the resource visualization gets new values in order to fit in the image.
	 * If the min and max value for a property are the same, it means that this metric has the default value. It doesn't need rescaling then.
	 */
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
	

	/**
	 * For each property, find the maximum and minimum value of all the resources.
	 */
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
				xMin = ((int)xCoord);
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
	
	/**
	 * Let the resource visualisations draw themselves.
	 */
	private void drawResourceVisualizations(){
		for(ResourceVisualization rv: getResourceVisualizations()){
			rv.draw(getIDrawInstantiation());
			LOG.info("DREW ONE RV");
		}
	}


	/**
	 * Converts the given x-coordinate relative to the axes of the scatter plot
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
	 * In this conversion, axisOffset denotes the offset between the border of image and the axes.
	 * xMax and yMax are the maximal values relative to the coordinate system of the scatter plot 
	 *  	that have to be drawn on the image.
	 *  
	 * @param xCoord
	 * @param xMax
	 * @return the x coordinate in the image plane
	 */
	private int convertX(double xCoord){
		LOG.info("xCoord: " + xCoord);
		return (int) (axisOffset + (width-2*axisOffset)* (xCoord-xMin)/(xMax-xMin));	
	}

	/**
	 * Converts the given y-coordinate relative to the axes of the scatter plot
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
	 * In this conversion, axisOffset denotes the offset between the border of image and the axes.
	 * xMax and yMax are the maximal values relative to the coordinate system of the scatter plot 
	 *  	that have to be drawn on the image.
	 *  
	 * @param yCoord
	 * @param yMax
	 * @return the y coordinate in the image plane
	 */
	private int convertY(double yCoord){
		return (int) (height-axisOffset + (2*axisOffset-height) *(yCoord-yMin)/(yMax-yMin));
	}
	
	/**
	 * Using interpolation, let the width of a resource visualisation be between the minimum and maximum width for a resource visualisation, 
	 * proportionally to the minimum and maximum value for the metric represented by the width. 
	 * @param width: The value of the metric represented by the width to be converted.
	 * @return the converted width (in pixels)
	 */
	private int convertWidth(double width){
		return (int) (minRVWidth+(maxRVWidth-minRVWidth)*(width-widthMin)/(widthMax-widthMin));
	}
	/**
	 * Using interpolation, let the height of a resource visualisation be between the minimum and maximum height for a resource visualisation, 
	 * proportionally to the minimum and maximum value for the metric represented by the height. 
	 * @param width: The value of the metric represented by the height to be converted.
	 * @return the converted height (in pixels)
	 */
	private int convertHeight(double height){
		return (int) (minRVHeight+(maxRVHeight-minRVHeight)*(height-heightMin)/(heightMax-heightMin));
	}
}
