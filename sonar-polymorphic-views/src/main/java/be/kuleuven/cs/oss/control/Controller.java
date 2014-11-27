package be.kuleuven.cs.oss.control;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

import org.sonar.api.charts.ChartParameters;

import be.kuleuven.cs.oss.charts.Chart;
import be.kuleuven.cs.oss.charts.ScatterPlot;
import be.kuleuven.cs.oss.charts.SystemComplexity;
import be.kuleuven.cs.oss.resourceproperties.ConstantResourceProperty;
import be.kuleuven.cs.oss.resourceproperties.ResourcePropertiesManager;
import be.kuleuven.cs.oss.resourceproperties.ResourceProperty;
import be.kuleuven.cs.oss.resourceproperties.ScaledResourceProperty;
import be.kuleuven.cs.oss.resourceproperties.SonarResourceProperty;
import be.kuleuven.cs.oss.resourcevisualizations.BoxFactory;
import be.kuleuven.cs.oss.resourcevisualizations.ResourceVisualizationFactory;
import be.kuleuven.cs.oss.sonarfacade.Metric;
import be.kuleuven.cs.oss.sonarfacade.Resource;
import be.kuleuven.cs.oss.sonarfacade.SonarFacade;

public class Controller {

	private final ChartParameters rawParams;
	private final SonarFacade sf;
	
	private final Resource parent;
	private final String resourceType;
	private final String chartType;
	private final ResourcePropertiesManager rpm = new ResourcePropertiesManager();

	private static final String DEFAULT_RESOURCE_TYPE = "classes";
	private static final String DEFAULT_CHART_TYPE = "scatter";
		
	
	Controller(ChartParameters p, SonarFacade sf) throws Exception{
		this.rawParams = p;
		this.sf = sf;
		this.parent = retrieveParent();
		this.resourceType = retrieveValueWithDefault("resources", DEFAULT_RESOURCE_TYPE);
		this.chartType = retrieveValueWithDefault("type", DEFAULT_CHART_TYPE);
	}
	
	public Resource getParent() {
		return parent;
	}

	public String getResourceType() {
		return resourceType;
	}

	public String getChartType() {
		return chartType;
	}
	
	/**
	 * Create a new visualization factory (currently, only boxes are supported)
	 * @return a new visualization factory
	 */
	public ResourceVisualizationFactory createRVFactory(){
		return new BoxFactory();
	}
	
	/**
	 * Retrieve the resource of which all children will be visualized in the chart
	 * @return the parent resource
	 * @throws Exception if the parent resource cannot be found
	 */
	private Resource retrieveParent() throws Exception{
		String parent = retrieveValue("parent");
		try{
			return sf.findResource(parent);
		}
		catch(Exception e){
			throw new Exception("Parent not valid");
		}
	}
	
	/**
	 * Retrieve all resources that will be visualized in the chart
	 * @return all the child resources of the parent, belonging to the previously specified resource type
	 * @throws Exception if no resources can be found belonging to the requested type
	 */
	private List<Resource> retrieveResources() throws Exception{
		try{
			switch(getResourceType()){
			case "classes":
				return sf.findClasses(getParent());
			case "packages":
				return sf.findPackages(getParent());
			default:
				throw new Exception();
			}
		}
		catch(Exception e){
			throw new Exception("Resources could not be retrieved");
		}
	}
	
	/**
	 * Retrieve the x metric and the y metric
	 * @return a list containing both x and y metric
	 * @throws Exception if at least one of the metrics cannot be found
	 */
	private List<Metric> retrieveXYMetrics() throws Exception{
		try{
			List<Metric> result = new ArrayList<Metric>();
			String xm = retrieveValue("xmetric");
			result.add(sf.findMetric(xm));
			String ym = retrieveValue("ymetric");
			result.add(sf.findMetric(ym));
			return result;
		}
		catch(Exception e){
			throw new Exception("X and/or Y metrics not valid");
		}
	}
	
	/**
	 * Retrieve the requested size of the chart
	 * @return a list containing both dimensions of the size
	 * @throws Exception if the size cannot be found
	 */
	private List<Integer> retrieveSize() throws Exception{
		try{
			String size = retrieveValue("size");
			return parseSize(size);
		}
		catch(Exception e){
			throw new Exception("Size not valid");
		}
	}
	
	/**
	 * Parse the given size
	 * @param s Textual representation of the given size
	 * @return a list containing both dimensions of the size
	 * @throws Exception if the size cannot be parsed
	 */
	private List<Integer> parseSize(String s) throws Exception{
		try{
			List<String> split = Arrays.asList(s.split("x"));
			return parseStringsToInts(split);
		}
		catch(Exception e){
			throw new Exception("Size cannot be parsed");
		}
	}
	
	/**
	 * Parse a given list of strings to a list of integers
	 * @param s List of strings
	 * @return a list containing the respective parsed integers
	 * @throws Exception if at least one of the strings cannot be parsed
	 */
	private static List<Integer> parseStringsToInts(List<String> s) throws Exception{
		List<Integer> result = new ArrayList<Integer>();
		Iterator<String> it = s.iterator();
		while (it.hasNext()){
			result.add(Integer.parseInt(it.next()));
		}
		return result;
	}
	
	/**
	 * Parse the given list of strings to a list of floats
	 * @param s List of strings
	 * @return a list containing the respective parsed floats
	 * @throws Exception if at least one of the strings cannot be parsed
	 */
	private List<Float> parseStringsToFloats(List<String> s) throws Exception{
		List<Float> result = new ArrayList<Float>();
		Iterator<String> it = s.iterator();
		while (it.hasNext()){
			result.add(Float.parseFloat(it.next()));
		}
		return result;
	}
	
	/**
	 * Create new resource properties for the boxcolor and add it to the resource property manager
	 * @throws Exception if the creation of the resource properties fails
	 */
	private void createColorRPs() throws Exception{
		try{
			String colorValue = retrieveValue("boxcolor");
			List<ResourceProperty> result = new ArrayList<ResourceProperty>();
			if(colorValue.matches("r[0-9]{1,3}g[0-9]{1,3}b[0-9]{1,3}")){
				List<Integer> rgb = parseRGB(colorValue);
				Iterator<Integer> it = rgb.iterator();
				while(it.hasNext()){
					result.add(new ConstantResourceProperty(it.next()));
				}
			}
			else if(colorValue.matches("min[0-9]+(\\.[0-9]+)*max[0-9]+(\\.[0-9]+)*key(.)+")){
				List<Float> gsFloats = parseGrayScaleFloats(colorValue);
				String gsKey = parseGrayScaleKey(colorValue);
				Metric gsMetric = sf.findMetric(gsKey);
				ResourceProperty rp = new ScaledResourceProperty(gsFloats.get(0), gsFloats.get(1), 255, 0, sf, gsMetric);
				for(int i=0;i<3;++i){
					result.add(rp);
				}
			}
			else throw new Exception("Color not recognized");
			rpm.addProperty("boxcolorR", result.get(0));
			rpm.addProperty("boxcolorG", result.get(1));
			rpm.addProperty("boxcolorB", result.get(2));
		}
		catch(Exception e){
			throw new Exception("Failed to create resource properties for color");
		}
	}
	
	/**
	 * Create a new resource property for the given box dimension and add it to the resource property manager
	 * @param dimension The given box dimension (currently, only boxwidth and boxheight are supported)
	 * @throws Exception if the creation of the resource property fails
	 */
	private void createBoxDimensionRP(String dimension) throws Exception{
		try{
			String dimensionValue = retrieveValue(dimension);
			ResourceProperty rp;
			if(dimensionValue.matches("[0-9]")){
				rp = new ConstantResourceProperty(Integer.parseInt(dimensionValue));
			}
			else{
				rp = new SonarResourceProperty(sf,sf.findMetric(dimensionValue));
			}
			rpm.addProperty(dimension, rp);
		}
		catch(Exception e){
			throw new Exception("Failed to create resource property for "+dimension);
		}
	}
	
	/**
	 * Parse the given RGB color
	 * @param rgb Textual representation of the given RGB color
	 * @return a list containing the respective integers representing each RGB component
	 * @throws Exception if the given RGB color cannot be parsed
	 */
	private List<Integer> parseRGB(String rgb) throws Exception{
		try{
		List<String> split = Arrays.asList(rgb.split("[rgb]"));
		return parseStringsToInts(split.subList(1, split.size()));
		}
		catch(Exception e){
			throw new Exception("RGB not valid");
		}
	}
	
	/**
	 * Parse the floats contained in the given gray scale
	 * @param gs Textual representation of the given gray scale
	 * @return a list containing the respective floats representing each gray scale component
	 * @throws Exception if the given gray scale cannot be parsed
	 */
	private List<Float> parseGrayScaleFloats(String gs) throws Exception{
		try{
		List<String> split = Arrays.asList(gs.split("[(min)(max)(key)]"));
		return parseStringsToFloats(split.subList(1, split.size()-2));
		}
		catch(Exception e){
			throw new Exception("Grayscale not valid");
		}
	}
	
	/**
	 * Parse the key contained in the given gray scale
	 * @param gs Textual representation of the given gray scale
	 * @return the key contained in the gray scale
	 * @throws Exception if the given gray scale cannot be parsed
	 */
	private String parseGrayScaleKey(String gs) throws Exception{
		try{
		List<String> split = Arrays.asList(gs.split("[(min)(max)(key)]"));
		return split.get(split.size()-1);
		}
		catch(Exception e){
			throw new Exception("Grayscale not valid");
		}
	}
			
	/**
	 * Retrieve a parameter value for the given parameter key
	 * @param key The given parameter key
	 * @return the retrieved parameter value
	 */
	private String retrieveValue(String key){
		return this.rawParams.getValue(key);
	}
	
	/**
	 * Retrieve a parameter value for the given parameter key, given a default value
	 * @param key The given parameter key
	 * @return the retrieved parameter value
	 */
	private String retrieveValueWithDefault(String key, String def){
		return this.rawParams.getValue(key, def, false);
	}
	
	/**
	 * Create extra resource properties for the scatterplot and add them to the resource property manager
	 * @throws Exception if the creation of the resource properties fails
	 */
	private void createExtraResourcePropertiesForScatter() throws Exception{
		try{
		List<Metric> xym = retrieveXYMetrics();
		rpm.addProperty("xmetric", new SonarResourceProperty(sf, xym.get(0)));
		rpm.addProperty("ymetric", new SonarResourceProperty(sf, xym.get(1)));
		}
		catch(Exception e){
			throw new Exception("Failed to create resource properties for additional scatterplot metrics");
		}
	}
	
	/**
	 * Check if the chartparameters define a valid scatterplot
	 * @return True if the chartparameters define a valid scatterplot
	 */
	private boolean isValidScatter(){
		return true;
	}
	
	/**
	 * Check if the chartparameters define a valid system complexity view
	 * @return True if the chartparameters define a valid system complexity view
	 */
	private boolean isValidSyscomp(){
		return (getResourceType() == "classes");
	}
	
	/**
	 * Create all resource properties that are common for all chart types and add them to the resource properties manager
	 * @throws Exception if the creation of these resource properties failed
	 */
	private void createCommonResourceProperties() throws Exception{
		String boxdimension;
		boxdimension = "boxwidth";
		createBoxDimensionRP(boxdimension);
		boxdimension = "boxheight";
		createBoxDimensionRP(boxdimension);
		createColorRPs();
	}
	
	/**
	 * Create a new chart based on the available chart parameters
	 * @return a new chart (currently, only scatterplot and system complexity view are supported)
	 * @throws Exception if the creation of the chart failed
	 */
	public Chart createChart() throws Exception{
		try{
			List<Resource> resources = retrieveResources();
			ResourceVisualizationFactory rvf = createRVFactory();
			createCommonResourceProperties();
			switch(getChartType()){
			case "scatter": 
				if(!isValidScatter())
					throw new Exception("Invalid scatterplot parameters");
				createExtraResourcePropertiesForScatter();
				List<Integer> size = retrieveSize();
				return new ScatterPlot(resources, rvf, sf, rpm, size.get(0), size.get(1));
			case "syscomp": 
				if(!isValidSyscomp())
					throw new Exception("Invalid system complexity parameters");
				return new SystemComplexity(resources, rvf, sf, rpm);
			default:
				throw new Exception();
			}
		}
		catch(Exception e){
			throw new Exception("Chart creation failed");
		}
	}
}
