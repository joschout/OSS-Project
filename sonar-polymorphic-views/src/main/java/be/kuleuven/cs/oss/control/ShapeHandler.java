package be.kuleuven.cs.oss.control;

import java.util.TreeMap;

import javax.persistence.NoResultException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.sonar.api.charts.ChartParameters;

import be.kuleuven.cs.oss.charts.Chart;
import be.kuleuven.cs.oss.resourcevisualizations.BoxFactory;
import be.kuleuven.cs.oss.resourcevisualizations.ConstantShapeDecider;
import be.kuleuven.cs.oss.resourcevisualizations.IntervalShapeDecider;
import be.kuleuven.cs.oss.resourcevisualizations.ResourceVisualizationFactory;
import be.kuleuven.cs.oss.resourcevisualizations.ShapeDecider;
import be.kuleuven.cs.oss.sonarfacade.SonarFacade;

public class ShapeHandler implements IHandler<Chart>{

	private final static Logger LOG = LoggerFactory.getLogger(RVFactoryHandler.class);

	private IHandler<Chart> next;
	
	private String key = "shape";
	
	private SonarFacade sf;
	
	public ShapeHandler(SonarFacade sf) {
		this.sf = sf;
	}

	@Override
	public void setNext(IHandler<Chart> handler) {
		this.next = handler;
	}

	
	@Override
	public void handleRequest(Chart chart, ChartParameters params) {
		ShapeDecider sd;
		
		if(retrieveValue(key, params).equals("metric")){
			sd = new IntervalShapeDecider();
			startProcessForMetricShape(sd,params);
		}
		else{
			sd = new ConstantShapeDecider();
			startProcessForConstantShape(sd,params);
		}
		
		chart.setShapeDecider(sd);
		
		if(next != null) {
			next.handleRequest(chart, params);
		}
		
	}

	private void startProcessForConstantShape(ShapeDecider sd, ChartParameters params) {
		Processor<ShapeDecider> processor = new Processor<ShapeDecider>();
		//TODO: idee: map van shapes (als strings) met bijhorende intervals maken, alsook getter, in ShapeDecider (nadeel: redundant)
		//TODO: ander idee: interface van IHandler generischer maken -> Chartparameters kan dan vervangen worden door combo
		
		switch(retrieveValue(key,params)){
		case "box": 
		}
		processor.addHandler(new RVFactoryHandler(sf));

		processor.startProcess(sd, params);
		
	}
	
	private void startProcessForMetricShape(ShapeDecider sd, ChartParameters params) {	
		TreeMap<Double,ResourceVisualizationFactory> map = new TreeMap<Double,ResourceVisualizationFactory>();
		
	}
	
	private String retrieveValue(String key, ChartParameters params) {
		String result = params.getValue(key);
		
		if(result.equals("")){
			LOG.info("retrieve value failed");
			throw new NoResultException("value not retrieved");
		}
		
		return result;
	}

}
