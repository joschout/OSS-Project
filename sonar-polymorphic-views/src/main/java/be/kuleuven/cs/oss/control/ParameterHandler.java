package be.kuleuven.cs.oss.control;

import org.sonar.api.charts.ChartParameters;

import be.kuleuven.cs.oss.charts.Chart;
import be.kuleuven.cs.oss.sonarfacade.SonarFacade;

public interface ParameterHandler {

	//reference to the next handler in the chain
	public void setNext(ParameterHandler handler);
	
	//handle request
	public void handleRequest(Chart chart, ChartParameters params);
	
}
