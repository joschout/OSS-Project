package be.kuleuven.cs.oss.charts;

import java.awt.image.BufferedImage;
import java.util.List;

import be.kuleuven.cs.oss.drawingPackage.IDraw;
import be.kuleuven.cs.oss.resourcevisualizations.ResourceVisualization;

public class ScatterPlotDrawing {

	
	private double axisOffset; 
	private static final double OFFSET_SCALE = 0.1;
	private int width;
	private int height;
	
	public ScatterPlotDrawing(int width, int height) {
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

	public BufferedImage draw(IDraw d, List<ResourceVisualization> rvs){
		d.createEmptyImage(getWidth(), getHeight());
	
		return d.getBufferedImage();
		
		
	}
	
	public double getAxisOffset() {
		return axisOffset;
	}

	public void setAxisOffset(double axisOffset) {
		this.axisOffset = axisOffset;
	}

//	private void drawAxises(d){
//		d.drawArrowRight(axisOffset,height-DEFAULT_AXIS_OFFSET , width -2*DEFAULT_AXIS_OFFSET);
//		d.drawArrowUp(axisOffset, height-DEFAULT_AXIS_OFFSET, height  -2*DEFAULT_AXIS_OFFSET);
//	}
	
	/**
	 * Converts the given x-coordinate relative to the axises of the scatterplot
	 *  to an x-coordinate in the plane of the image.
	 *  The 
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

