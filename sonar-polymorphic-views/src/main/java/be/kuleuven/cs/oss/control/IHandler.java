package be.kuleuven.cs.oss.control;

import be.kuleuven.cs.oss.datautils.ParamValueRetriever;

/**
 * An interface that is to be implemented by all handlers that want to modify an object of type M
 * @param <M> the type of the object to be modified along the chain
 * 
 * @author jeroenreinenbergh
 *
 */
public interface IHandler<M> {
	
	/**
	 * Sets the next handler in the chain of responsibility
	 * @param handler
	 */
	//reference to the next handler in the chain
	public abstract void setNext(IHandler<M> handler);
	
	/**
	 * Modifies the given mutable object based on the given parameter value retriever
	 * @param mutableObject the mutable object to modify
	 * @param params the given parameter value retriever
	 */
	//handle request
	public abstract void handleRequest(M mutableObject, ParamValueRetriever params);
	
}
