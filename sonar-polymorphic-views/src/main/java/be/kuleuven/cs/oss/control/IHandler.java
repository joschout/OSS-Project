package be.kuleuven.cs.oss.control;

import org.sonar.api.charts.ChartParameters;

public interface IHandler<M> {
	
	//reference to the next handler in the chain
	public abstract void setNext(IHandler<M> handler);
	
	//handle request
	public abstract void handleRequest(M mutableObject, ChartParameters params);
	
}
