package be.kuleuven.cs.oss.control;

import javax.persistence.NoResultException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.sonar.api.charts.ChartParameters;

import be.kuleuven.cs.oss.charts.Chart;
import be.kuleuven.cs.oss.charts.ScatterPlot;
import be.kuleuven.cs.oss.charts.SystemComplexity;

public class ChartHandler {
	
	private static final String DEFAULT_CHART_TYPE = "scatter";
	
	private String key = "type";
	
	private String chartType1 = "scatter";
	private String chartType2 = "syscomp";
	
	private final static Logger LOG = LoggerFactory.getLogger(ChartHandler.class);
	
	private ChartParameters params;

	public ChartHandler(ChartParameters params) {
		this.params = params;
	}

	
	
	public Chart getChart() {
		String result = params.getValue(key, DEFAULT_CHART_TYPE, false);
		if(result.equals("")){
			LOG.info("retrieve value with default failed");
			throw new NoResultException("value with default not retrieved");
		}
		
		if(result.equals(chartType1)) {
			return new ScatterPlot();
		}
		if(result.equals(chartType2)) {
			return new SystemComplexity();
		}
		
		
	}

}
