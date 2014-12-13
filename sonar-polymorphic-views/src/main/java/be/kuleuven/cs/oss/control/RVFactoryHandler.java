package be.kuleuven.cs.oss.control;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.sonar.api.charts.ChartParameters;

import be.kuleuven.cs.oss.charts.Chart;
import be.kuleuven.cs.oss.resourcevisualizations.BoxFactory;
import be.kuleuven.cs.oss.resourcevisualizations.ResourceVisualizationFactory;
import be.kuleuven.cs.oss.sonarfacade.SonarFacade;

public class RVFactoryHandler implements ParameterHandler {

	private final static Logger LOG = LoggerFactory.getLogger(RVFactoryHandler.class);

	private ParameterHandler next;
	
	private SonarFacade sf;
	
	public RVFactoryHandler(SonarFacade sf) {
		this.sf = sf;
	}

	@Override
	public void setNext(ParameterHandler handler) {
		this.next = handler;
	}

	
	/**
	 * Create a new line factory (currently, only straight lines are supported)
	 * @return a new line factory
	 */
	@Override
	public void handleRequest(Chart chart, ChartParameters params) {
		ResourceVisualizationFactory factory = new BoxFactory();
		
		startProcess(factory, params);
		
		chart.setResourceVisualizationFactory(factory);
		
		if(next != null) {
			next.handleRequest(chart, params);
		}
		
	}

	private void startProcess(ResourceVisualizationFactory rvf, ChartParameters params) {
		ResourceVisualizationFactoryProcessor processor = new ResourceVisualizationFactoryProcessor();
		
		processor.addHandler(new BoxDimensionHandler(sf));
		processor.addHandler(new ColorHandler(sf));
		
		processor.startProcess(rvf, params);
		
	}
	
}
