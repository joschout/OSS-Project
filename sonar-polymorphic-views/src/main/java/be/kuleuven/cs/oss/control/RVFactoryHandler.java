package be.kuleuven.cs.oss.control;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.sonar.api.charts.ChartParameters;

import be.kuleuven.cs.oss.charts.Chart;
import be.kuleuven.cs.oss.resourcevisualizations.BoxFactory;
import be.kuleuven.cs.oss.resourcevisualizations.ResourceVisualizationFactory;
import be.kuleuven.cs.oss.sonarfacade.SonarFacade;

public class RVFactoryHandler implements IHandler<Chart> {

	private final static Logger LOG = LoggerFactory.getLogger(RVFactoryHandler.class);

	private IHandler<Chart> next;
	
	private SonarFacade sf;
	
	public RVFactoryHandler(SonarFacade sf) {
		this.sf = sf;
	}

	@Override
	public void setNext(IHandler<Chart> handler) {
		this.next = handler;
	}

	
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
		Processor<ResourceVisualizationFactory> processor = new Processor<ResourceVisualizationFactory>();
		
		processor.addHandler(new BoxDimensionHandler(sf));
		processor.addHandler(new ColorHandler(sf));
		
		processor.startProcess(rvf, params);
		
	}
	
}
