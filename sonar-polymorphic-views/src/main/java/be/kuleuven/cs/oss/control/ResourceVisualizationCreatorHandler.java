package be.kuleuven.cs.oss.control;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.persistence.NoResultException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.sonar.api.charts.ChartParameters;

import be.kuleuven.cs.oss.charts.Chart;
import be.kuleuven.cs.oss.datautils.ParamValueRetriever;
import be.kuleuven.cs.oss.resourceproperties.SonarResourceProperty;
import be.kuleuven.cs.oss.resourcevisualizations.BoxFactory;
import be.kuleuven.cs.oss.resourcevisualizations.CircleFactory;
import be.kuleuven.cs.oss.resourcevisualizations.IntervalShapeDecider;
import be.kuleuven.cs.oss.resourcevisualizations.ResourceVisualizationCreator;
import be.kuleuven.cs.oss.resourcevisualizations.ResourceVisualizationFactory;
import be.kuleuven.cs.oss.resourcevisualizations.TrapezoidFactory;
import be.kuleuven.cs.oss.sonarfacade.SonarFacade;

/**
 * A handler for the resource visualization creator, which can either be a factory or an interval decider, in the chart
 * 
 * @author jeroenreinenbergh
 *
 */
public class ResourceVisualizationCreatorHandler implements IHandler<Chart>{

	private final static Logger LOG = LoggerFactory.getLogger(ResourceVisualizationCreatorHandler.class);

	private IHandler<Chart> next;
	
	private String shapeKey = "shape";
	private String shapeValueMetric = "metric";
	private String shapeValueBox = "box";
	private String shapeValueCircle = "circle";
	private String shapeValueTrap = "trap";
	
	private String colorValueBox = "boxcolor";
	private String colorValueCircle = "circlecolor";
	private String colorValueTrap = "trapcolor";
	
	private String metricKey = "shapemetric";
	private String orderKey = "shapemetricorder";
	private String boundKey = "shapemetricsplit";
	
	private SonarFacade sf;
	
	/**
	 * Creates a new ResourceVisualizationCreatorHandler with the given SonarFacade instance
	 * @param sf an instance of SonarFacade
	 */
	public ResourceVisualizationCreatorHandler(SonarFacade sf) {
		this.sf = sf;
	}

	@Override
	public void setNext(IHandler<Chart> handler) {
		this.next = handler;
	}

	/**
	 * Creates the resource visualization creator and sets it in the given chart
	 */
	@Override
	public void handleRequest(Chart chart, ParamValueRetriever params) {
		String shapeValue = params.retrieveValue(shapeKey);
		
		if(shapeValue.equals(shapeValueMetric)){
			IntervalShapeDecider rvc = new IntervalShapeDecider();
			rvc.setResourceProperty(new SonarResourceProperty(sf,sf.findMetric(params.retrieveValue(metricKey))));
			addFactories(rvc,params);
			chart.setRvf(rvc);
		}
		else{
			ResourceVisualizationFactory rvf = createRVF(shapeValue,params);
			chart.setRvf(rvf);
		}
				
		if(next != null) {
			next.handleRequest(chart, params);
		}
		
	}
	
	/**
	 * Creates a fully modified resource visualization factory that is defined by the given shape value and parameter value retriever
	 * @param shapeValue the given value of the shape key
	 * @param params the given parameter value retriever
	 * @return the fully modified resource visualization factory that is defined by the given shape value and parameter value retriever
	 * @throws IllegalArgumentException if the given shape value is invalid
	 */
	private ResourceVisualizationFactory createRVF(String shapeValue, ParamValueRetriever params) throws IllegalArgumentException{
		ResourceVisualizationFactory factory;
		String colorValue;
		
		if(shapeValue.equals(shapeValueBox)){
			factory = new BoxFactory();
			colorValue = colorValueBox;
		}
		else if(shapeValue.equals(shapeValueCircle)){
			factory = new CircleFactory();
			colorValue = colorValueCircle;
		}
		else if(shapeValue.equals(shapeValueTrap)){
			factory = new TrapezoidFactory();
			colorValue = colorValueTrap;
		}
		else throw new IllegalArgumentException("Invalid shape");
		
		Processor<ResourceVisualizationFactory> processor = new Processor<ResourceVisualizationFactory>();
		processor.addHandler(new DimensionsHandler(sf));
		processor.addHandler(new ColorHandler(sf,colorValue));
		processor.startProcess(factory, params);
		
		return factory;
	}
	
	/**
	 * Adds all resource visualization factories to the given interval shape decider
	 * @param sd the given interval shape decider
	 * @param params the given parameter value retriever
	 */
	private void addFactories(IntervalShapeDecider sd, ParamValueRetriever params) {	
		String order = params.retrieveValue(orderKey);
		String split = params.retrieveValue(boundKey);
		
		List<String> shapes = parseStringList(order,"-");
		
		List<Integer> boundaries = parseStringListToIntList(parseStringList(split,"x"));
		boundaries.add(Integer.MAX_VALUE);
		try{
		if(shapes.size() == boundaries.size()){
			for(int i=0;i<shapes.size();++i){
				sd.addBoundaryWithFactory(boundaries.get(i),createRVF(shapes.get(i),params));
			}
		}
		
		else throw new IllegalArgumentException("ShapeMetricOrder and ShapeMetricSplit combination not valid");
		}
		catch(Exception e){
			e.printStackTrace();
		}
	}
	
	/**
	 * Parses the given list of strings to a list of integers
	 * @param toParse the given list of strings
	 * @return the parsed list of integers
	 */
	private List<Integer> parseStringListToIntList(List<String> toParse){
		List<Integer> result = new ArrayList<Integer>();
		for(String str : toParse){
			result.add(Integer.parseInt(str));
		}
		return result;
	}
	
	/**
	 * Splits the given string into a list of strings based on the given split sequence
	 * @param total the string to split
	 * @param splitter the sequence to split on
	 * @return a list containing the splitted strings
	 */
	private List<String> parseStringList(String total,String splitter){
		String[] split = total.split(splitter);
		return Arrays.asList(split);
	}

}
