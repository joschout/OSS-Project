package be.kuleuven.cs.oss.control;

import org.sonar.api.charts.ChartParameters;

/**
 * A class that is responsible for the correct functioning of the chain of responsibility
 * in which all the handlers perform their request in a predefined order
 * @param <M> the type of the object to be modified by the handlers along the chain
 * 
 * @author jeroenreinenbergh
 *
 */
public class Processor<M> {
	private IHandler<M> firstHandler;
	private IHandler<M> prevHandler;
	
	/**
	 * Adds a handler to the chain of responsibility
	 * @param handler the given handler
	 */
	public void addHandler(IHandler<M> handler) {
		if(firstHandler == null) {
			firstHandler = handler;
		}
		
		if(prevHandler != null)
		{
			prevHandler.setNext(handler);
		}
		prevHandler = handler;
	}
	
	/**
	 * Starts the the process of executing the chain of responsibility
	 * @param mutableObject the mutable object to be modified along the chain
	 * @param params the given chartparameters to be used in the process
	 */
	public void startProcess(M mutableObject, ChartParameters params) {
		firstHandler.handleRequest(mutableObject, params);
	}
}
