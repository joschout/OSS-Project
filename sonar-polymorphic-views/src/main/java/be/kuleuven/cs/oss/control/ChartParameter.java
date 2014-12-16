package be.kuleuven.cs.oss.control;

import javax.persistence.NoResultException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import be.kuleuven.cs.oss.charts.Chart;
import be.kuleuven.cs.oss.charts.ScatterPlot;
import be.kuleuven.cs.oss.charts.SystemComplexity;
import be.kuleuven.cs.oss.datautils.ParamValueRetriever;

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
	
	private ParamValueRetriever params;
	
	/**
	 * Creates a new Chartparameter with the given ParamValueRetriever instance
	 * @param params the given parameter value retriever
	 */
	public ChartParameter(ParamValueRetriever params) {
		this.params = params;
	}

	
	/**
	 * Creates a new Chart instance based on the value of the chart key
	 * @return a Scatterplot instance if the chart value is equal to scatter and a SystemComplexity if the chart value is equal to syscomp
	 * @throws NoResultException if the creation of a chart failed
	 */
	public Chart getChart() throws NoResultException{
		String result = params.retrieveValue(key, DEFAULT_CHART_TYPE);
		
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
