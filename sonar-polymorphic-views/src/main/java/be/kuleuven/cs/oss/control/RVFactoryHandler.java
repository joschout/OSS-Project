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

public abstract class RVFactoryHandler implements IHandler<ShapeDecider> {

	private final static Logger LOG = LoggerFactory.getLogger(RVFactoryHandler.class);

	private IHandler<ShapeDecider> next;
	
	private SonarFacade sf;
	
	public RVFactoryHandler(SonarFacade sf) {
		this.sf = sf;
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
	
	public abstract ResourceVisualizationFactory createRvf();

	private void startProcess(ResourceVisualizationFactory rvf, ChartParameters params) {
		Processor<ResourceVisualizationFactory> processor = new Processor<ResourceVisualizationFactory>();
		
		processor.addHandler(new BoxDimensionHandler(sf));
		processor.addHandler(new ColorHandler(sf));
		
		processor.startProcess(rvf, params);
		
	}
	
}
