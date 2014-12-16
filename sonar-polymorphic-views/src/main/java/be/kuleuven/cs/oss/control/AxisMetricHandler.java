package be.kuleuven.cs.oss.control;

import javax.persistence.NoResultException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.sonar.api.charts.ChartParameters;

import be.kuleuven.cs.oss.charts.Chart;
import be.kuleuven.cs.oss.charts.ScatterPlot;
import be.kuleuven.cs.oss.resourceproperties.ResourceProperty;
import be.kuleuven.cs.oss.resourceproperties.SonarResourceProperty;
import be.kuleuven.cs.oss.sonarfacade.Metric;
import be.kuleuven.cs.oss.sonarfacade.SonarFacade;

/**
 * A handler for the axis metrics in the chart
 * 
 * @author jeroenreinenbergh
 *
 */
public class AxisMetricHandler implements IHandler<Chart> {

	private final static Logger LOG = LoggerFactory.getLogger(AxisMetricHandler.class);

	private IHandler<Chart> next;

	private String keyX = "xmetric";
	private String keyY = "ymetric";

	SonarFacade sf;

	/**
	 * Creates a new handler for the axis metrics with the given sonar facade
	 * @param sf an instance of SonarFacade
	 */
	public AxisMetricHandler(SonarFacade sf) {
		this.sf = sf;
	}

	@Override
	public void setNext(IHandler<Chart> handler) {
		this.next = handler;
	}
	
	/**
	 * Creates the axis metric properties and sets them in the given chart if this chart is a scatterplot
	 */
	@Override
	public void handleRequest(Chart chart, ChartParameters params) {
		if (!(chart instanceof ScatterPlot)) {
			if(next != null){
				next.handleRequest(chart, params);
			}
			return;
		}
		ResourceProperty xProperty = createAxisMetricProperty(keyX, params);
		ResourceProperty yProperty = createAxisMetricProperty(keyY, params);
		
		
		((ScatterPlot) chart).setAxisMetrics(xProperty, yProperty);
		
		if(next != null) {
			next.handleRequest(chart, params);
		}

	}

	/**
	 * Creates a resource property for the given axis
	 * @param axis the axis for which a resource property has to be returned
	 * @param params the given chartparameters from which the axis metric has to be retrieved
	 * @return the resulting resource property for the given axis
	 * @throws NoResultException if the axis metric cannot be found
	 */
	private ResourceProperty createAxisMetricProperty(String axis, ChartParameters params) throws NoResultException{
		String metricValue = retrieveValue(axis, params);
		
		Metric metric = sf.findMetric(metricValue);
		if(metric == null){
			throw new NoResultException("Metric not found");
		}
		SonarResourceProperty prop = new SonarResourceProperty(sf,sf.findMetric(metricValue));
		
		return prop;
	}

	/**
	 * Retrieve a parameter value for the given parameter key
	 * @param key the given parameter key
	 * @param params the given chartparameters from which the parameter value has to be retrieved
	 * @return the retrieved parameter value
	 * @throws NoResultException if the value for the given key cannot be retrieved
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
