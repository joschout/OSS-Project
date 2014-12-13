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

public class SizeHandler implements ParameterHandler {

	private final static Logger LOG = LoggerFactory.getLogger(SizeHandler.class);

	private static final Size DEFAULT_SIZE = new Size(800,600);

	private ParameterHandler next;

	private String key = "size";

	SonarFacade sf;

	public SizeHandler(SonarFacade sf) {
		this.sf = sf;
	}

	@Override
	public void setNext(ParameterHandler handler) {
		this.next = handler;
	}


	/**
	 * Retrieve the requested size of the chart
	 * @return a list containing both dimensions of the size
	 * @throws Exception if the size cannot be found
	 */
	@Override
	public void handleRequest(Chart chart, ChartParameters params) {
		if (!(chart instanceof ScatterPlot)) {
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

		((ScatterPlot) chart).setSize(size);
		
		if(next != null) {
			next.handleRequest(chart, params);
		}

	}

	/**
	 * Parse the given size
	 * @param s Textual representation of the given size
	 * @return a list containing both dimensions of the size
	 * @throws Exception if the size cannot be parsed
	 */
	private Size parseSize(String s) {
		List<String> split = Arrays.asList(s.split("x"));
		int height = Integer.parseInt(split.get(0));
		int width = Integer.parseInt(split.get(1));

		Size size = new Size(height, width);
		return size;

	}

	/**
	 * Retrieve a parameter value for the given parameter key
	 * @param key The given parameter key
	 * @return the retrieved parameter value
	 */
	private String retrieveValue(String key, ChartParameters params) {
		String result = params.getValue(key);

		if(result.equals("")){
			LOG.info("retrieve value failed");
			throw new NoResultException("value not retrieved");
		}

		return result;
	}


}
