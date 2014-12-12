package be.kuleuven.cs.oss.control;

public class ParameterProcessor {

	private ParameterHandler prevHandler;
	
	public void addHandler(ParameterHandler handler)
	{
		if(prevHandler != null)
		{
			prevHandler.setNext(handler);
		}
		prevHandler = handler;

	}

}
