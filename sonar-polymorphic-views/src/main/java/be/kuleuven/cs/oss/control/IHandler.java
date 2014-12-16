package be.kuleuven.cs.oss.control;

import org.sonar.api.charts.ChartParameters;

public interface IHandler<M> {
	
	/**
	 * Sets the next handler in the chain of responsibility
	 * @param handler
	 */
	//reference to the next handler in the chain
	public abstract void setNext(IHandler<M> handler);
	
	/**
	 * Modifies the given mutableObject based on the given Chartparameters
	 * @param mutableObject the mutable object to modify
	 * @param params the given chartparameters
	 */
	//handle request
	public abstract void handleRequest(M mutableObject, ChartParameters params);
	
}
