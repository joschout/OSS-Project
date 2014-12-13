package be.kuleuven.cs.oss.control;

import org.sonar.api.charts.ChartParameters;

import be.kuleuven.cs.oss.resourcevisualizations.ResourceVisualizationFactory;

public interface ResourceVisualizationFactoryHandler {

	//reference to the next handler in the chain
		public abstract void setNext(ResourceVisualizationFactoryHandler handler);
		
		//handle request
		public abstract void handleRequest(ResourceVisualizationFactory rvf, ChartParameters params);
	
}
