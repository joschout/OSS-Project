package be.kuleuven.cs.oss.charts;

import java.awt.image.BufferedImage;

import org.jfree.util.Log;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import be.kuleuven.cs.oss.datautils.Position;
import be.kuleuven.cs.oss.datautils.Size;
import be.kuleuven.cs.oss.drawingPackage.IDraw;
import be.kuleuven.cs.oss.resourceproperties.ResourceProperty;
import be.kuleuven.cs.oss.resourcevisualizations.ResourceVisualization;
import be.kuleuven.cs.oss.sonarfacade.Resource;


public class ScatterPlot extends Chart{

	//Image frame properties
	private double axisOffset; 
	private Size imageFrameSize; 

	//ResourceProperties specific for this chart
	private ResourceProperty xMetric;
	private ResourceProperty yMetric;

	//global maxvalues over all resources
	private Position maxResourcePosition = new Position(0, 0);
	private Size maxResourceSize = new Size(0,0);

	//global minvalues over all resources
	//made protected for test
	private Position minResourcePosition = new Position(Integer.MAX_VALUE, Integer.MAX_VALUE);
	private Size minResourceSize = new Size(Integer.MAX_VALUE,Integer.MAX_VALUE);

	//the values for the dimensions of the smallest and largest box in the plot
	//each value is calculated by multiplying the width or height of the frame with the factors
	private double minRVHeight;
	private double maxRVHeight;
	private double maxRVWidth;
	private double minRVWidth;
	private static final double minRVScalingFactor = 0.05;
	private static final double maxRVScalingFactor = 0.15;
	private final static Logger LOG = LoggerFactory.getLogger(ScatterPlot.class);

	public ScatterPlot(){
		super();
		LOG.info("ScatterPlot constructed");
	}

	/**
	 * constructor 
	 */
	public ScatterPlot(Size imageFrameSize) {
		super();
		setImageFrameSize(imageFrameSize);
	}


	public void setAxisMetrics(ResourceProperty xMetric, ResourceProperty yMetric) {
		this.xMetric = xMetric;
		this.yMetric = yMetric;
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

		calculateAxisOffSet();
		Log.info("calculated the offset for the axes");

		calculateRVSizeExtrema();
		LOG.info("calculated the initial RV size extrema");

		//1
		getIDrawInstantiation().createEmptyImage(getImageFrameSize().getWidth(), getImageFrameSize().getHeight());
		LOG.info("empty frame made");
		//2
		drawAxises(getIDrawInstantiation());
		LOG.info("Axes drawn on frame");
		//4
		createResourceVisualizations();
		LOG.info("RVs created");

		setExtremeValues();	

		convertPosition();

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

	private void calculateAxisOffSet(){
		this.axisOffset = (minRVScalingFactor+maxRVScalingFactor)/2*Math.min(getImageFrameSize().getWidth(), getImageFrameSize().getHeight());
	}

	private void calculateRVSizeExtrema(){
		minRVHeight= minRVScalingFactor*getImageFrameSize().getHeight();
		maxRVHeight = maxRVScalingFactor*getImageFrameSize().getHeight();
		maxRVWidth = maxRVScalingFactor*getImageFrameSize().getWidth();
		minRVWidth = minRVScalingFactor*getImageFrameSize().getWidth();
	}

	/**
	 * Draws the axes of the scatter plot. The axes consist of 2 arrows, 
	 * one pointing up and one pointing right, like a normal Cartesian coordinate system.
	 * 
	 * @param d : IDraw
	 */
	private void drawAxises(IDraw d){
		d.drawArrowRight( (int)axisOffset, 
				(int)(getImageFrameSize().getHeight() - axisOffset) , 
				(int)(getImageFrameSize().getWidth() - 2*axisOffset));
		d.drawArrowUp( (int)axisOffset,
				(int)(getImageFrameSize().getHeight() - axisOffset),
				(int)(getImageFrameSize().getHeight() - 2*axisOffset));
	}

	/**
	 * Creates the resourceVisualizations with the factory
	 * This also sets the values for the x and y position
	 */
	private void createResourceVisualizations(){
		for(Resource resource: getResources()){
			ResourceVisualization rv = getRvf().create(resource);
			//ResourceVisualization rv = getShapeDecider().create(resource);
			//These will be the values of the properties that govern the position of the box. To be rescaled!
			Position metricsPosition = new Position(xMetric.getValue(resource).intValue(), yMetric.getValue(resource).intValue());
			rv.setPosition(metricsPosition);
			this.getResourceVisualizations().add(rv);
		}
	}

	/**
	 * 
	 */
	private void convertPosition(){

		for (ResourceVisualization rv : this.getResourceVisualizations()){	
			rv.setPosition(new Position(convertX(rv.getX()), convertY(rv.getY())));
		}
	}



	/**
	 * Using interpolation, the resource visualization gets new values in order to fit in the image.
	 * If the min and max value for a property are the same, it means that this metric has the default value. It doesn't need rescaling then.
	 */
	private void rescaleResourceVisualizations(){

		for (ResourceVisualization rv : this.getResourceVisualizations()){	
			int widthpx = getMinResourceSize().getWidth();
			int heightpx = getMinResourceSize().getHeight();


			if(getMinResourceSize().getWidth() != getMaxResourceSize().getWidth()){
				//	System.out.println("in if");
				widthpx = convertWidth(rv.getWidth());

			}
			if(getMinResourceSize().getHeight() != getMaxResourceSize().getHeight()){
				heightpx = convertHeight(rv.getHeight());
			}
			rv.setSize(new Size(widthpx, heightpx));
		}	
	}

	private void rescaleResourceVisualization(){}
	/**
	 * For each property, find the maximum and minimum value of all the resources.
	 */
	private void setExtremeValues(){	

		for(ResourceVisualization rv : getResourceVisualizations()){	

			double xCoord = rv.getX();
			double yCoord = rv.getY();
			double width = rv.getWidth();
			double height = rv.getHeight();
			System.out.println("in for.");
			System.out.println("maxResourcepos: " + getMaxResourcePosition().getX());
			if(xCoord > getMaxResourcePosition().getX() ){
				getMaxResourcePosition().setX((int)xCoord);
			}
			if(yCoord > getMaxResourcePosition().getY()){
				getMaxResourcePosition().setY((int)yCoord);
			}			
			if(width > getMaxResourceSize().getWidth()){
				getMaxResourceSize().setWidth ((int)width);
			}	
			if(height > getMaxResourceSize().getHeight()){
				getMaxResourceSize().setHeight ((int)height);
			}


			if(xCoord <= getMinResourcePosition().getX()){
				getMinResourcePosition().setX((int)xCoord);
			}
			if(yCoord <= getMinResourcePosition().getY()){
				getMinResourcePosition().setY((int)yCoord);
			}			
			if(width <= getMinResourceSize().getWidth()){
				getMinResourceSize().setWidth((int)width);
			}	
			if(height <= getMinResourceSize().getHeight()){
				getMinResourceSize().setHeight((int)height);
			}			
		}
	}

	/**
	 * Let the resource visualizations draw themselves.
	 */
	private void drawResourceVisualizations(){
		for(ResourceVisualization rv: getResourceVisualizations()){
			//DIT MQG STRQKS ZEG
			LOG.info("xCoord: " + rv.getX());
			rv.draw(getIDrawInstantiation());
		}
	}

	private void drawAxesLabels(IDraw d){
		String xname = xMetric.getPropertyName();
		String yname = yMetric.getPropertyName();

		d.drawText("" +getMaxResourcePosition().getX(), (int)(getImageFrameSize().getWidth()- axisOffset/2), (int)(getImageFrameSize().getHeight() - axisOffset/2));
		d.drawText(xname, (int)(axisOffset + getImageFrameSize().getWidth()/2), (int)(getImageFrameSize().getHeight() - axisOffset/2));

		d.drawText("" +getMaxResourcePosition().getY(), (int)(axisOffset/2), (int)axisOffset, -90, 0, 0, 0);
		d.drawText(yname, (int)(axisOffset/2) , (int)(axisOffset + getImageFrameSize().getHeight()/2), -90, 0, 0, 0);
	}

	public Size getImageFrameSize() {
		return imageFrameSize;
	}

	public void setImageFrameSize(Size imageFrameSize) {
		this.imageFrameSize = imageFrameSize;
	}

	public Position getMaxResourcePosition() {
		return maxResourcePosition;
	}


	public void setMaxResourcePosition(Position maxResourcePosition) {
		this.maxResourcePosition = maxResourcePosition;
	}


	public Size getMaxResourceSize() {
		return maxResourceSize;
	}


	public void setMaxResourceSize(Size maxResourceSize) {
		this.maxResourceSize = maxResourceSize;
	}


	public Position getMinResourcePosition() {
		return minResourcePosition;
	}


	public void setMinResourcePosition(Position minResourcePosition) {
		this.minResourcePosition = minResourcePosition;
	}


	public Size getMinResourceSize() {
		return minResourceSize;
	}


	public void setMinResourceSize(Size minResourceSize) {
		this.minResourceSize = minResourceSize;
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
	 *  		( axisOffset + (imageWidth - 2* axisOffset) * xCoord/getMaxResourcePosition().getX(),
	 *  		 imageHeight - axisOffset +(2*axisOffset - imageHeight )* yCoord/yMax )
	 *  		relative to the coordinate system of the image plane
	 *  
	 * In this conversion, axisOffset denotes the offset between the border of image and the axes.
	 * getMaxResourcePosition().getX() and yMax are the maximal values relative to the coordinate system of the scatter plot 
	 *  	that have to be drawn on the image.
	 *  
	 * @param xCoord
	 * @return the x coordinate in the image plane
	 */
	private int convertX(double xCoord){

		LOG.info("xCoord: " + xCoord);
		return (int) (axisOffset + (getImageFrameSize().getWidth()-2*axisOffset)* (xCoord-getMinResourcePosition().getX())/(getMaxResourcePosition().getX()-getMinResourcePosition().getX()));	
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
	 *  		( axisOffset + (imageWidth - 2* axisOffset) * xCoord/getMaxResourcePosition().getX(),
	 *  		 imageHeight - axisOffset +(2*axisOffset - imageHeight )* yCoord/yMax )
	 *  		relative to the coordinate system of the image plane
	 *  
	 * In this conversion, axisOffset denotes the offset between the border of image and the axes.
	 * getMaxResourcePosition().getX() and yMax are the maximal values relative to the coordinate system of the scatter plot 
	 *  	that have to be drawn on the image.
	 *  
	 * @param yCoord
	 * @param yMax
	 * @return the y coordinate in the image plane
	 */
	private int convertY(double yCoord){
		return (int) (getImageFrameSize().getHeight()-axisOffset + (2*axisOffset-getImageFrameSize().getHeight()) *(yCoord-getMinResourcePosition().getY())/(getMaxResourcePosition().getY()-getMinResourcePosition().getY()));
	}

	/**
	 * Using interpolation, let the width of a resource visualisation be between the minimum and maximum width for a resource visualisation, 
	 * proportionally to the minimum and maximum value for the metric represented by the width. 
	 * @param width: The value of the metric represented by the width to be converted.
	 * @return the converted width (in pixels)
	 */
	private int convertWidth(double width){
		return (int) (minRVWidth+(maxRVWidth-minRVWidth)*(width-getMinResourceSize().getWidth())/(getMaxResourceSize().getWidth()-getMinResourceSize().getWidth()));
	}
	/**
	 * Using interpolation, let the height of a resource visualisation be between the minimum and maximum height for a resource visualisation, 
	 * proportionally to the minimum and maximum value for the metric represented by the height. 
	 * @param width: The value of the metric represented by the height to be converted.
	 * @return the converted height (in pixels)
	 */
	private int convertHeight(double height){
		return (int) (minRVHeight+(maxRVHeight-minRVHeight)*(height-getMinResourceSize().getHeight())/(getMaxResourceSize().getHeight()-getMinResourceSize().getHeight()));
	}
}