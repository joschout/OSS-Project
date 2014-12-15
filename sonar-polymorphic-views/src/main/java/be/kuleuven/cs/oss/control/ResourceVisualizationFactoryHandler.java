package be.kuleuven.cs.oss.control;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

import javax.persistence.NoResultException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.sonar.api.charts.ChartParameters;

import be.kuleuven.cs.oss.charts.Chart;
import be.kuleuven.cs.oss.resourceproperties.SonarResourceProperty;
import be.kuleuven.cs.oss.resourcevisualizations.BoxFactory;
import be.kuleuven.cs.oss.resourcevisualizations.ConstantShapeDecider;
import be.kuleuven.cs.oss.resourcevisualizations.IntervalShapeDecider;
import be.kuleuven.cs.oss.resourcevisualizations.ResourceVisualization;
import be.kuleuven.cs.oss.resourcevisualizations.ResourceVisualizationFactory;
import be.kuleuven.cs.oss.resourcevisualizations.ShapeDecider;
import be.kuleuven.cs.oss.sonarfacade.Resource;
import be.kuleuven.cs.oss.sonarfacade.SonarFacade;

public class ResourceVisualizationFactoryHandler implements IHandler<Chart>{

	private final static Logger LOG = LoggerFactory.getLogger(ResourceVisualizationFactoryHandler.class);

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
	
	public ResourceVisualizationFactoryHandler(SonarFacade sf) {
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
			IntervalShapeDecider sd = new IntervalShapeDecider();
			sd.setResourceProperty(new SonarResourceProperty(sf,sf.findMetric(retrieveValue(metricKey, params))));
			addFactories(sd,params);
			chart.setShapeDecider(sd);
		}
		else{
			ConstantShapeDecider sd = new ConstantShapeDecider();
			sd.setResourceVisualizationFactory(createRVF(shapeValue,params));
			chart.setShapeDecider(sd);
		}
				
		if(next != null) {
			next.handleRequest(chart, params);
		}
		
	}
	
	private ResourceVisualizationFactory createRVF(String shapeValue, ChartParameters params){
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
	
	private void addFactories(IntervalShapeDecider sd, ChartParameters params) {	
		String order = retrieveValue(orderKey, params);
		String split = retrieveValue(boundKey, params);
		
		List<String> shapes = parseStringList(order,"-");
		
		List<Integer> boundaries = parseStringListToIntList(parseStringList(split,"x"));
		boundaries.add(Integer.MAX_VALUE);
		
		if(shapes.size() == boundaries.size()){
			for(String shape : shapes){
				sd.addBoundaryWithFactory(boundaries.get(shapes.indexOf(shape)),createRVF(shape,params));
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
