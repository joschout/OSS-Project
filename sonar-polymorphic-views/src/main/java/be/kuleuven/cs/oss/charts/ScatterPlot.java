package be.kuleuven.cs.oss.charts;

import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;





import be.kuleuven.cs.oss.drawingPackage.IDraw;
import be.kuleuven.cs.oss.resourceproperties.ResourcePropertiesManager;
import be.kuleuven.cs.oss.resourcevisualizations.ResourceVisualization;
import be.kuleuven.cs.oss.resourcevisualizations.ResourceVisualizationFactory;
import be.kuleuven.cs.oss.resourcevisualizations.BoxFactory; //eventually to remove
import be.kuleuven.cs.oss.sonarfacade.Resource;
import be.kuleuven.cs.oss.sonarfacade.SonarFacade;

public class ScatterPlot extends Chart{

	private double axisOffset; 
	private int width;
	private int height;
	
	private double xMedian;
	private double yMedian;
	private double widthMedian;
	private double heightMedian;
	
	

	private int xMax;
	private int yMax;
	private int widthMax;
	private int heightMax;
	private int xMin;
	private int yMin;
	private int widthMin;
	private int heightMin;
	private double minRVHeight;
	private double maxRVHeight;
	private double maxRVWidth;
	private double minRVWidth;
	
	private static final double minRVScalingFactor = 0.05;
	private static final double maxRVScalingFactor = 0.20;
	
	
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
		setWidthMax(0);
		setHeightMax(0);
		setXMin(Integer.MAX_VALUE);
		setYMin(Integer.MAX_VALUE);
		setWidthMin(Integer.MAX_VALUE);
		setHeightMin(Integer.MAX_VALUE);
	}


	public double getxMedian() {
		
		return xMedian;
	}


	public void setxMedian(double xMedian) {
		this.xMedian = xMedian;
	}


	public double getyMedian() {
		return yMedian;
	}


	public void setyMedian(double yMedian) {
		this.yMedian = yMedian;
	}


	public double getWidthMedian() {
		return widthMedian;
	}


	public void setWidthMedian(double widthMedian) {
		this.widthMedian = widthMedian;
	}


	public double getHeightMedian() {
		return heightMedian;
	}


	public void setHeightMedian(double heightMedian) {
		this.heightMedian = heightMedian;
	}


	public int getXMin() {
		//TODO overal fouten gaan opvangen. <0 => exception
		return xMin;
	}


	public void setXMin(int xMin) {
		this.xMin = xMin;
	}


	public int getYMin() {
		return yMin;
	}


	public void setYMin(int yMin) {
		this.yMin = yMin;
	}


	public int getWidthMax() {
		return widthMax;
	}


	public void setWidthMax(int widthMax) {
		this.widthMax = widthMax;
	}


	public int getHeightMax() {
		return heightMax;
	}


	public void setHeightMax(int heightMax) {
		this.heightMax = heightMax;
	}
	
	public int getWidthMin() {
		return widthMin;
	}


	public void setWidthMin(int widthMin) {
		this.widthMin = widthMin;
	}


	public int getHeightMin() {
		return heightMin;
	}


	public void setHeightMin(int heightMin) {
		this.heightMin = heightMin;
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
		
		// 3) create the ResourceVsiualizations
		createResourceVisualizations();
		
		// 4) calculate min max rvsize
		setExtremeRVSizes();
		
		// 5) rescale the ResourceVsiualizations
		rescaleResourceVisualizations();
		
		// 6) draw the ResourceVisualizations
		drawResourceVisualizations();
		
		return d.getBufferedImage();
	}

	private void setExtremeRVSizes() {
		this.minRVHeight= minRVScalingFactor*getHeight();
		((BoxFactory)this.rvf).;
		this.maxRVHeight = maxRVScalingFactor*getHeight();
		this.maxRVWidth = maxRVScalingFactor*getWidth();
		this.minRVWidth = minRVScalingFactor*getWidth();
	}



	
	private void rescaleResourceVisualizations(){
//		setMedians();
		setExtremeValues();	
		List<ResourceVisualization> visualizations = this.getResourceVisualizations();
		
		
		
	}
	
//	private void setMedians(){
//		List<ResourceVisualization> visualizations = this.getResourceVisualizations();
//		
//		ArrayList<Integer> xList = new ArrayList<Integer>();
//		ArrayList<Integer> yList = new ArrayList<Integer>() ;
//		ArrayList<Integer> widthList = new ArrayList<Integer>() ;
//		ArrayList<Integer> heightList = new ArrayList<Integer>() ;
//		
//		for(ResourceVisualization visualization : visualizations){		
//			xList.add(visualization.getX());
//			yList.add(visualization.getY());
//			widthList.add(visualization.getWidth());
//			heightList.add(visualization.getHeight());
//		}
		
//		int xMedian = getMedian(xList);
//		int yMedian = getMedian(yList);
//		int widthMedian = getMedian(widthList);
//		int heightMedian = getMedian(heightList);
		
		
//		Collections.sort(xList);
//		Collections.sort(yList);
//		Collections.sort(widthList);
//		Collections.sort(heightList);
//		
//		int listSize = xList.size()/2;
		
		
		
			
			
//			
//			if (xCoord != null){
//				xList.add(xCoord);
//			}
//			if (yCoord != null){
//				yList.add(yCoord);
//			}
//			if (width != null){
//				widthList.add(width);
//			}
//			if (height != null){
//				widthList.add(height);
//			}		
//			
//			if(xList.size() != 0){
//				xList.sort(null);
//			}
	
		
//	}
	
	
//	private int getMedian(ArrayList<Integer> list) {
//		Collections.sort(list);
//		
//		int middle = list.size() /2;
//		if((list.size() % 2) == 1){
//			return list.get(middle);
//		}else{
//			return list.
//		}
//		
//		
//	}
//
//
//	public static double median(double[] m) {
//	   // int middle = m.length/2;
//	    if (m.length%2 == 1) {
//	        return m[middle];
//	    } else {
//	        return (m[middle-1] + m[middle]) / 2.0;
//	    }
//	}
	
	private void setExtremeValues(){	
		for(ResourceVisualization rv : rvs){		
			double xCoord = rv.getX();
			double yCoord = rv.getY();
			double width = rv.getWidth();
			double height = rv.getHeight();
			
			if(xCoord > getXMax() ){
				setXMax((int)xCoord);
			}
			if(yCoord > getYMax()){
				setYMax((int)yCoord);;
			}			
			if(width > getWidthMax()){
				setWidth((int)width);
			}	
			if(height > getHeightMax()){
				setHeight((int)height);
			}
			
			
			if(xCoord < getXMin() ){
				setXMax((int)xCoord);
			}
			if(yCoord < getYMin()){
				setYMin((int)yCoord);;
			}			
			if(width < getWidthMin()){
				setWidthMin((int)width);
			}	
			if(height < getHeightMin()){
				setHeightMin((int)height);
			}	
		}
	}
	
	private void createResourceVisualizations(){
		for(Resource resource: resources){
			Map<String, Double> properties = super.getResourcePropertyValues(resource);
			//DERP
//			int xCoord = convertX(properties.get("xmetric"));
//			int yCoord = convertY(properties.get("ymetric"));
//			Position position = new Position(xCoord, yCoord);
//			
//			Color color = new Color(properties.get("colorR").intValue(),properties.get("colorB").intValue(),properties.get("colorG").intValue());
//			Size size = new Size(properties.get("width").intValue(), properties.get("height").intValue());
//			
//			Map<String,Double> map = new HashMap<String,Double>();
			
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
	private int convertX(double xCoord){
		return (int) (getAxisOffset() + (width-2*getAxisOffset())* xCoord/getXMax());	
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
		return (int) (getHeight()-getAxisOffset()+(2*getAxisOffset()-getHeight())*yCoord/getYMax());
	}

}
