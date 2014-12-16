package be.kuleuven.cs.oss.control;

import java.text.ParseException;
import java.util.Arrays;
import java.util.List;

import javax.persistence.NoResultException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.sonar.api.charts.ChartParameters;

import be.kuleuven.cs.oss.charts.Chart;
import be.kuleuven.cs.oss.charts.ScatterPlot;
import be.kuleuven.cs.oss.charts.SystemComplexity;
import be.kuleuven.cs.oss.datautils.Size;
import be.kuleuven.cs.oss.sonarfacade.SonarFacade;

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
	public void handleRequest(Chart chart, ChartParameters params) {
		if (!(chart instanceof ScatterPlot)) {
			if(next != null){
				next.handleRequest(chart, params);
			}
			return;
		}

		Size size;
		try {
			String toParse = retrieveValue(key, params);
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

	/**
	 * Retrieve a parameter value for the given parameter key
	 * @param key the given parameter key
	 * @return the retrieved parameter value
	 * @throws NoResultException if the value cannot be retrieved
	 */
	private String retrieveValue(String key, ChartParameters params) throws NoResultException{
		String result = params.getValue(key);

		if(result.equals("")){
			LOG.info("retrieve value failed");
			throw new NoResultException("value not retrieved");
		}

		return result;
	}


}
