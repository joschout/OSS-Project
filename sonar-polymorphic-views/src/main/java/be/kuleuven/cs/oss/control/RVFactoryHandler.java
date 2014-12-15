package be.kuleuven.cs.oss.control;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.sonar.api.charts.ChartParameters;

import be.kuleuven.cs.oss.charts.Chart;
import be.kuleuven.cs.oss.resourcevisualizations.BoxFactory;
import be.kuleuven.cs.oss.resourcevisualizations.ConstantShapeDecider;
import be.kuleuven.cs.oss.resourcevisualizations.IntervalShapeDecider;
import be.kuleuven.cs.oss.resourcevisualizations.ResourceVisualization;
import be.kuleuven.cs.oss.resourcevisualizations.ResourceVisualizationFactory;
import be.kuleuven.cs.oss.resourcevisualizations.ShapeDecider;
import be.kuleuven.cs.oss.sonarfacade.Resource;
import be.kuleuven.cs.oss.sonarfacade.SonarFacade;

public class RVFactoryHandler implements IHandler<ShapeDecider> {

	private final static Logger LOG = LoggerFactory.getLogger(RVFactoryHandler.class);

	private IHandler<ShapeDecider> next;
	
	private SonarFacade sf;
	
	private String shape;
	private int bound;
	
	public RVFactoryHandler(SonarFacade sf, String shape, int bound) {
		this.sf = sf;
		this.shape = shape;
		this.bound = bound;
	}

	@Override
	public void setNext(IHandler<ShapeDecider> handler) {
		this.next = handler;
	}

	
	@Override
	public void handleRequest(ShapeDecider sd, ChartParameters params) {
		ResourceVisualizationFactory rvf = createRvf();
		
		startProcess(factory, params);
		
		chart.setRvf(factory);
		
		if(ConstantShapeDecider.class.isInstance(sd)){
			sd.setResourceVisualizationFactory(rvf);
		}
		else if(IntervalShapeDecider.class.isInstance(sd)){
			//TODO: 
		}
		else throw new IllegalArgumentException("Invalid shape decider");
				
		if(next != null) {
			next.handleRequest(sd, params);
		}
		
	}
	
	public ResourceVisualizationFactory createRvf();

	private void startProcess(ResourceVisualizationFactory rvf, ChartParameters params) {
		Processor<ResourceVisualizationFactory> processor = new Processor<ResourceVisualizationFactory>();
		
		processor.addHandler(new DimensionsHandler(sf));
		processor.addHandler(new ColorHandler(sf));
		
		processor.startProcess(rvf, params);
		
	}
	
}
