package be.kuleuven.cs.oss.control;

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

public abstract class BoxFactoryHandler implements IHandler<ShapeDecider> {

	private final static Logger LOG = LoggerFactory.getLogger(RVFactoryHandler.class);

	private IHandler<ShapeDecider> next;
	
	private SonarFacade sf;
	
	public BoxFactoryHandler(SonarFacade sf) {
		this.sf = sf;
	}

	@Override
	public void setNext(IHandler<ShapeDecider> handler) {
		this.next = handler;
	}

	
	@Override
	public void handleRequest(ShapeDecider sd, ChartParameters params) {
		if(ConstantShapeDecider.class.isInstance(sd)){
			
		}
		else if(IntervalShapeDecider.class.isInstance(sd)){
			
		}
		ResourceVisualizationFactory factory = new BoxFactory();
		
		startProcess(factory, params);
		
		sd.setResourceVisualizationFactory(factory);
		
		if(next != null) {
			next.handleRequest(sd, params);
		}
		
	}

	private void startProcess(ResourceVisualizationFactory rvf, ChartParameters params) {
		Processor<ResourceVisualizationFactory> processor = new Processor<ResourceVisualizationFactory>();
		
		processor.addHandler(new BoxDimensionHandler(sf));
		processor.addHandler(new ColorHandler(sf));
		
		processor.startProcess(rvf, params);
		
	}
	
}
