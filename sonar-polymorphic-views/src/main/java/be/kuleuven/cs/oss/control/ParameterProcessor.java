package be.kuleuven.cs.oss.control;

import org.sonar.api.charts.ChartParameters;

import be.kuleuven.cs.oss.charts.Chart;

public class ParameterProcessor {

	private ParameterHandler firstHandler;
	private ParameterHandler prevHandler;
	
	public void addHandler(ParameterHandler handler) {
		if(firstHandler == null) {
			firstHandler = handler;
		}
		
		if(prevHandler != null)
		{
			prevHandler.setNext(handler);
		}
		prevHandler = handler;
	}
	
	public void startProcess(Chart chart, ChartParameters params) {
		firstHandler.handleRequest(chart, params);
	}
	

}
