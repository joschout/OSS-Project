package be.kuleuven.cs.oss.control;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.sonar.api.charts.ChartParameters;

import be.kuleuven.cs.oss.charts.Chart;
import be.kuleuven.cs.oss.charts.SystemComplexity;
import be.kuleuven.cs.oss.datautils.Color;
import be.kuleuven.cs.oss.datautils.Position;
import be.kuleuven.cs.oss.lines.LineFactory;
import be.kuleuven.cs.oss.lines.StraightLineFactory;

/**
 * A handler for the line factory in the chart
 * 
 * @author jeroenreinenbergh
 *
 */
public class LineFactoryHandler implements IHandler<Chart> {

	private final static Logger LOG = LoggerFactory.getLogger(LineFactoryHandler.class);
	
	private static final Position DEFAULT_POSITION = new Position(0,0);
	private static final int DEFAULT_WIDTH = 1;
	private static final Color DEFAULT_COLOR = new Color(0);

	private IHandler<Chart> next;

	@Override
	public void setNext(IHandler<Chart> handler) {
		this.next = handler;
	}

	/**
	 * Creates the line factory and sets it in the given chart if this chart is a system complexity view
	 */
	@Override
	public void handleRequest(Chart chart, ChartParameters params) {
		if (!(chart instanceof SystemComplexity)) {
			if(next != null){
				next.handleRequest(chart, params);
			}
			return;
		}
		
		LineFactory factory = new StraightLineFactory();
		
		factory.setDefaultPosition(DEFAULT_POSITION);
		factory.setDefaultWidth(DEFAULT_WIDTH);
		factory.setDefaultColor(DEFAULT_COLOR);
		
		((SystemComplexity) chart).setLineFactory(factory);
		
		if(next != null) {
			next.handleRequest(chart, params);
		}
		
	}

}
