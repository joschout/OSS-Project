package be.kuleuven.cs.oss.control;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.persistence.NoResultException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.sonar.api.charts.ChartParameters;

import be.kuleuven.cs.oss.charts.Chart;
import be.kuleuven.cs.oss.resourceproperties.SonarResourceProperty;
import be.kuleuven.cs.oss.resourcevisualizations.BoxFactory;
import be.kuleuven.cs.oss.resourcevisualizations.CircleFactory;
import be.kuleuven.cs.oss.resourcevisualizations.IntervalShapeDecider;
import be.kuleuven.cs.oss.resourcevisualizations.ResourceVisualizationCreator;
import be.kuleuven.cs.oss.resourcevisualizations.TrapezoidFactory;
import be.kuleuven.cs.oss.sonarfacade.SonarFacade;

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
	private String orderKey = "shapeMetricOrder";
	private String boundKey = "shapeMetricSplit";
	
	private SonarFacade sf;
	
	public ResourceVisualizationCreatorHandler(SonarFacade sf) {
		this.sf = sf;
	}

	@Override
	public void setNext(IHandler<Chart> handler) {
		this.next = handler;
	}

	
	@Override
	public void handleRequest(Chart chart, ChartParameters params) {
		String shapeValue = retrieveValue(shapeKey, params);
		
		if(shapeValue.equals(shapeValueMetric)){
			IntervalShapeDecider rvc = new IntervalShapeDecider();
			rvc.setResourceProperty(new SonarResourceProperty(sf,sf.findMetric(retrieveValue(metricKey, params))));
			addFactories(rvc,params);
			chart.setRvf(rvc);
		}
		else{
			ResourceVisualizationCreator sd = createRVC(shapeValue,params);
			chart.setRvf(sd);
		}
				
		if(next != null) {
			next.handleRequest(chart, params);
		}
		
	}
	
	private ResourceVisualizationCreator createRVC(String shapeValue, ChartParameters params){
		ResourceVisualizationCreator factory;
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
		
		Processor<ResourceVisualizationCreator> processor = new Processor<ResourceVisualizationCreator>();
		processor.addHandler(new DimensionsHandler(sf));
		processor.addHandler(new ColorHandler(sf,colorValue));
		processor.startProcess(factory, params);
		
		return factory;
	}
	
	private void addFactories(IntervalShapeDecider sd, ChartParameters params) {	
		String order = retrieveValue(orderKey, params);
		String split = retrieveValue(boundKey, params);
		
		List<String> shapes = parseStringList(order,"-");
		
		List<Integer> boundaries = parseStringListToIntList(parseStringList(split,"x"));
		boundaries.add(Integer.MAX_VALUE);
		
		if(shapes.size() == boundaries.size()){
			for(String shape : shapes){
				sd.addBoundaryWithFactory(boundaries.get(shapes.indexOf(shape)),createRVC(shape,params));
			}
		}
		else throw new IllegalArgumentException("ShapeMetricOrder and ShapeMetricSplit combination not valid");
	}
	
	private String retrieveValue(String key, ChartParameters params) {
		String result = params.getValue(key);
		
		if(result.equals("")){
			LOG.info("retrieve value failed");
			throw new NoResultException("value not retrieved");
		}
		
		return result;
	}
	
	private List<Integer> parseStringListToIntList(List<String> toParse){
		List<Integer> result = new ArrayList<Integer>();
		for(String str : toParse){
			result.add(Integer.parseInt(str));
		}
		return result;
	}
	
	private List<String> parseStringList(String total,String splitter){
		String[] split = total.split(splitter);
		return Arrays.asList(split);
	}

}
