package be.kuleuven.cs.oss.control;

import javax.persistence.NoResultException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.sonar.api.charts.ChartParameters;

import be.kuleuven.cs.oss.charts.Chart;
import be.kuleuven.cs.oss.charts.SystemComplexity;
import be.kuleuven.cs.oss.resourceproperties.ResourceProperty;
import be.kuleuven.cs.oss.resourceproperties.SonarResourceProperty;
import be.kuleuven.cs.oss.sonarfacade.SonarFacade;

public class AxisMetricHandler implements ParameterHandler {

	private final static Logger LOG = LoggerFactory.getLogger(AxisMetricHandler.class);

	private ParameterHandler next;

	private String keyX = "xmetric";
	private String keyY = "ymetric";

	SonarFacade sf;

	public AxisMetricHandler(SonarFacade sf) {
		this.sf = sf;
	}

	@Override
	public void setNext(ParameterHandler handler) {
		this.next = handler;
	}

	@Override
	public void handleRequest(Chart chart, ChartParameters params) {
		if (chart instanceof SystemComplexity) {
			return;
		}

		ResourceProperty xProperty = createAxisMetricProperty(keyX, params);
		ResourceProperty yProperty = createAxisMetricProperty(keyY, params);
		
		chart.setAxisProperties();
		
		next.handleRequest(chart, params);

	}


	private ResourceProperty createAxisMetricProperty(String axis, ChartParameters params){
		String aMetric = retrieveValue(axis, params);

		SonarResourceProperty prop = new SonarResourceProperty(sf,sf.findMetric(aMetric));
		
		return prop;
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
