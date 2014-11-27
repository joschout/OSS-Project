package be.kuleuven.cs.oss.control;

import java.util.ArrayList;
import java.util.List;

import org.sonar.api.charts.ChartParameters;

import be.kuleuven.cs.oss.charts.Chart;
import be.kuleuven.cs.oss.resourcevisualizations.BoxResourceVisualizationFactory;
import be.kuleuven.cs.oss.resourcevisualizations.ResourceVisualizationFactory;
import be.kuleuven.cs.oss.sonarfacade.Dependency;
import be.kuleuven.cs.oss.sonarfacade.Metric;
import be.kuleuven.cs.oss.sonarfacade.Resource;
import be.kuleuven.cs.oss.sonarfacade.SonarFacade;

public class Controller {

	private final ChartParameters rawParams;
	private final SonarFacade sf;
	
	private final Resource parent;
	private final String resourceType;
	private final String chartType;
	
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

	public ResourceVisualizationFactory createRVFactory(){
		return new BoxResourceVisualizationFactory();
	}
	
	private Resource retrieveParent() throws Exception{
		String parent = retrieveValue("parent");
		try{
			return sf.findResource(parent);
		}
		catch(Exception e){
			throw new Exception("Parent not valid");
		}
	}
	
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
	
	private Metric[] retrieveXYMetrics() throws Exception{
		try{
			Metric[] result = new Metric[2];
			String xm = retrieveValue("xmetric");
			result[0] = sf.findMetric(xm);
			String ym = retrieveValue("ymetric");
			result[1] = sf.findMetric(ym);
			return result;
		}
		catch(Exception e){
			throw new Exception("X and/or Y metrics not valid");
		}
	}
	
	private int[] retrieveSize() throws Exception{
		try{
			int[] result = new int[2];
			String size = retrieveValue("size");
			result = parseSize(size);
			return result;
		}
		catch(Exception e){
			throw new Exception("Size not valid");
		}
	}
	
	private int[] parseSize(String s) throws Exception{
		try{
			int[] parsed = new int[2];
			String[] split = s.split("x");
			return parseStringsToInts(split);
		}
		catch(IllegalArgumentException e){
			throw new Exception("Size cannot be parsed");
		}
	}
	
	private int[] parseStringsToInts(String[] s) throws IllegalArgumentException{
		int size = s.length;
		int[] result = new int[size];
		for(int i=0;i<size;++i){
			result[i] = Integer.parseInt(s[i]);
		}
		return result;
	}
		
	private String retrieveValue(String key){
		return this.rawParams.getValue(key);
	}
	
	private String retrieveValueWithDefault(String key, String def){
		return this.rawParams.getValue(key, def, false);
	}
	
	//TODO
	public Chart createChart() throws Exception{
		try{
			ResourceVisualizationFactory rvf = createRVFactory();
			List<Resource> resources = retrieveResources();
			switch(getChartType()){
			case "scatter": 
				int[] size = retrieveSize();
				Metric[] xym = retrieveXYMetrics();
				return null;
			case "syscomp": 
				return null;
			default:
				throw new Exception();
			}
		}
		catch(Exception e){
			throw new Exception("Chart creation failed");
		}
	}
}
