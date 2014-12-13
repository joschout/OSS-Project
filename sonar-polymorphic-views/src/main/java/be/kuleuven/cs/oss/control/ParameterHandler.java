package be.kuleuven.cs.oss.control;

import javax.persistence.NoResultException;

import org.sonar.api.charts.ChartParameters;

import be.kuleuven.cs.oss.charts.Chart;

public interface ParameterHandler {

	//reference to the next handler in the chain
	public abstract void setNext(ParameterHandler handler);
	
	//handle request
	public abstract void handleRequest(Chart chart, ChartParameters params);
	
	
	
	
	
}
