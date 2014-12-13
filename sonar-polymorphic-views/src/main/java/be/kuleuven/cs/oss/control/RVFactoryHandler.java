package be.kuleuven.cs.oss.control;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.sonar.api.charts.ChartParameters;

import be.kuleuven.cs.oss.charts.Chart;
import be.kuleuven.cs.oss.charts.ScatterPlot;
import be.kuleuven.cs.oss.lines.LineFactory;
import be.kuleuven.cs.oss.lines.StraightLineFactory;
import be.kuleuven.cs.oss.resourcevisualizations.BoxFactory;
import be.kuleuven.cs.oss.resourcevisualizations.ResourceVisualizationFactory;

public class RVFactoryHandler implements ParameterHandler {

	private final static Logger LOG = LoggerFactory.getLogger(RVFactoryHandler.class);

	private ParameterHandler next;

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
		
		ResourceVisualizationFactory factory = new BoxFactory()();
		
		chart.setRVFactory(factory);
		
		next.handleRequest(chart, params);
		
	}
	
}
