package be.kuleuven.cs.oss.control;

import java.util.Arrays;
import java.util.List;

import javax.persistence.NoResultException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.sonar.api.charts.ChartParameters;

import be.kuleuven.cs.oss.charts.Chart;
import be.kuleuven.cs.oss.charts.ScatterPlot;
import be.kuleuven.cs.oss.datautils.ParamValueRetriever;
import be.kuleuven.cs.oss.datautils.Size;
import be.kuleuven.cs.oss.sonarfacade.SonarFacade;

/**
 * A handler for the size in the chart
 * 
 * @author jeroenreinenbergh
 *
 */
public class SizeHandler implements IHandler<Chart> {

	private final static Logger LOG = LoggerFactory.getLogger(SizeHandler.class);

	private static final Size DEFAULT_SIZE = new Size(800,600);

	private IHandler<Chart> next;

	private String key = "size";

	SonarFacade sf;

	/**
	 * Creates a new sizehandler with the given SonarFacade instance
	 * @param sf an instance of SonarFacade
	 */
	public SizeHandler(SonarFacade sf) {
		this.sf = sf;
	}

	@Override
	public void setNext(IHandler<Chart> handler) {
		this.next = handler;
	}

	/**
	 * Creates the size and sets it in the given chart if that chart is a scatterplot
	 */
	@Override
	public void handleRequest(Chart chart, ParamValueRetriever params) {
		if (!(chart instanceof ScatterPlot)) {
			if(next != null){
				next.handleRequest(chart, params);
			}
			return;
		}

		Size size;
		try {
			String toParse = params.retrieveValue(key);
			size = parseSize(toParse);
		}
		catch(NoResultException e) {
			size = DEFAULT_SIZE;
		}

		((ScatterPlot) chart).setImageFrameSize(size);
		
		if(next != null) {
			next.handleRequest(chart, params);
		}

	}

	/**
	 * Parses the given size
	 * @param s textual representation of the given size
	 * @return a list containing both dimensions of the size
	 * @throws NumberFormatException if the size cannot be parsed
	 */
	private Size parseSize(String s) throws NumberFormatException{
		List<String> split = Arrays.asList(s.split("x"));
		int height = Integer.parseInt(split.get(0));
		int width = Integer.parseInt(split.get(1));

		Size size = new Size(height, width);
		return size;

	}

}
