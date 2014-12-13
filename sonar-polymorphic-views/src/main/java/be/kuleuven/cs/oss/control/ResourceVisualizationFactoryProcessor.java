package be.kuleuven.cs.oss.control;

import org.sonar.api.charts.ChartParameters;

import be.kuleuven.cs.oss.resourcevisualizations.ResourceVisualizationFactory;

public class ResourceVisualizationFactoryProcessor {
	
	private ResourceVisualizationFactoryHandler firstHandler;
	private ResourceVisualizationFactoryHandler prevHandler;
	
	public void addHandler(ResourceVisualizationFactoryHandler handler) {
		if(firstHandler == null) {
			firstHandler = handler;
		}
		
		if(prevHandler != null)
		{
			prevHandler.setNext(handler);
		}
		prevHandler = handler;
	}
	
	public void startProcess(ResourceVisualizationFactory rvf, ChartParameters params) {
		firstHandler.handleRequest(rvf, params);
	}
	
	
}
