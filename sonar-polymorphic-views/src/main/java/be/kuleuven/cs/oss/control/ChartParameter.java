package be.kuleuven.cs.oss.control;

import javax.persistence.NoResultException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.sonar.api.charts.ChartParameters;

import be.kuleuven.cs.oss.charts.Chart;
import be.kuleuven.cs.oss.charts.ScatterPlot;
import be.kuleuven.cs.oss.charts.SystemComplexity;

/**
 * A chart parameter wrapper that is able to return the right chart based on the chart parameters
 * 
 * @author jeroenreinenbergh
 *
 */
public class ChartParameter {
	
	private static final String DEFAULT_CHART_TYPE = "scatter";
	
	private String key = "type";
	
	private String chartType1 = "scatter";
	private String chartType2 = "syscomp";
	
	private final static Logger LOG = LoggerFactory.getLogger(ChartParameter.class);
	
	private ChartParameters params;
	
	/**
	 * Creates a new Chartparameter with the given Chartparameters instance
	 * @param params the given chartparameters
	 */
	public ChartParameter(ChartParameters params) {
		this.params = params;
	}

	
	/**
	 * Creates a new Chart instance based on the value of the chart key in the map of Chartparameters
	 * @return a Scatterplot instance if the chart value is equal to scatter and a SystemComplexity if the chart value is equal to syscomp
	 * @throws NoResultException if the creation of a chart failed
	 */
	public Chart getChart() throws NoResultException{
		String result = params.getValue(key, DEFAULT_CHART_TYPE, false);

		if(result.equals("")){
			LOG.info("retrieve value with default failed");
			throw new NoResultException("value with default not retrieved");
		}
		
		if(result.equals(chartType1)) {
			return new ScatterPlot();
		}
		if(result.equals(chartType2)) {
			SystemComplexity syscomp = new SystemComplexity();
			System.out.println(syscomp);
			return syscomp;
		}
		
		throw new NoResultException("No chart created");
		
	}

}
