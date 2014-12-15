package be.kuleuven.cs.oss.charts;

import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

import be.kuleuven.cs.oss.drawingPackage.IDraw;
import be.kuleuven.cs.oss.drawingPackage.Java2DFacade;
import be.kuleuven.cs.oss.resourcevisualizations.ResourceVisualization;
import be.kuleuven.cs.oss.resourcevisualizations.ResourceVisualizationFactory;
import be.kuleuven.cs.oss.resourcevisualizations.ShapeDecider;
import be.kuleuven.cs.oss.sonarfacade.Resource;
import be.kuleuven.cs.oss.sonarfacade.SonarFacade;


/**
 * 
 * The Chart class has instantiations which  are visual representations of resources as  particular charts.
 * The class is an abstract superclass so it cannot be instantiated itself.
 * It can have subclasses such as SysCom and ScatterPlot,
 *  which represent System Complexity Views and Scatter Plots respectively.
 * 
 * @author Milan
 *
 */

public abstract class Chart {

	private List<ResourceVisualization> rvs;
	private List<Resource> resources;
	private SonarFacade sonarF;
	private IDraw iDrawFacade;
	private ShapeDecider shapeDecider;

	//TODO LineFactory has to be one of the arguments
	/**
	 *  Constructs an instantiation of a chart.
	 * 	More specifically, it helps construct an instance of a non-abstract subclass of this class.
	 *  As an argument on instantiation, chart gets a list of resources which will have to be drawn on the plot.
	 *  It also gets a reference to an instance of a ResourceVisualizationFactory, 
	 *  which is used to make resource visualizations of a particular kind, for example in the form of boxes or circles.
	 *  Furthermore, a reference to a SonarFacade-object is given, which is a facade for the Sonar database, by connecting to
	 *  the web service provided by the Sonar server.
	 *  Another argument it gets is a ResourcePropertyManager
	 *  
	 * 
	 * @param List<Resource>resources: The classes or packages to appear on the plot
	 * @param rvf: The ResourceVisualisationFactory which makes a ResourceVisualisation for each resource (such as a Box)
	 * @param SonarFacade sonarF: a facade to the sonar database
	 * @param ResourcePropertiesManager propManager: manages the properties of each resource
	 */
	public Chart() {
		setIDrawInstantiation(new Java2DFacade());
		this.rvs = new ArrayList<ResourceVisualization>();
	}

	public SonarFacade getSonarFacade() {
		return sonarF;
	}

	public void setSonarFacade(SonarFacade sonarF) {
		this.sonarF = sonarF;
	}


	/**
	 * Returns a list of visualizations for the resources of the graph
	 * 
	 * @return List<ResourceVisualization> : the list of resource visualizations
	 */
	public List<ResourceVisualization> getResourceVisualizations() {
		return rvs;
	}

	/**
	 * Returns a list of visualizations for the resources of the graph
	 * 
	 * @return List<ResourceVisualization> : the list of resource visualizations
	 */
	public void setResourceVisualizations(List<ResourceVisualization> rvs) {
		this.rvs = rvs;
	}


	/**
	 * Returns a list of the resources this chart visualizes
	 */
	public List<Resource> getResources() {
		return resources;
	}

	/** 
	 * Sets the list of the resources this chart visualizes
	 * 
	 * @param resources: list of the resources this chart visualizes
	 */
	public void setResources(List<Resource> resources) {
		this.resources = resources;
	}


	/**
	 *  Returns an instantiation of the IDraw interface. This interface is used to actually draw the chart to an image.
	 */
	public IDraw getIDrawInstantiation() {
		return iDrawFacade;
	}

	/**
	 * Sets the instantiation of the IDraw interface the chart uses to draw its image to the given object.
	 * @param d: the instantiation of the IDraw interface that will be used to draw the image of the chart object.
	 */
	public void setIDrawInstantiation(IDraw d) {
		this.iDrawFacade = d;
	}


	public ShapeDecider getShapeDecider() {
		return shapeDecider;
	}

	public void setShapeDecider(ShapeDecider shapeDecider) {
		this.shapeDecider = shapeDecider;
	}

	/**
	 * Creates an actual image of the chart represented by an object of this class. 
	 * Returns this image as a BufferedImage.
	 * 
	 * @return BufferedImage: returns a drawing of the actual image represented by this chart object
	 */
	public abstract BufferedImage draw();


}


