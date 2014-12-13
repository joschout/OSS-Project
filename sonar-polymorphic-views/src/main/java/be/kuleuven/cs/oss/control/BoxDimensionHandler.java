package be.kuleuven.cs.oss.control;

import javax.persistence.NoResultException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.sonar.api.charts.ChartParameters;

import be.kuleuven.cs.oss.charts.Chart;
import be.kuleuven.cs.oss.resourceproperties.ConstantResourceProperty;
import be.kuleuven.cs.oss.resourceproperties.ResourceProperty;
import be.kuleuven.cs.oss.resourceproperties.SonarResourceProperty;
import be.kuleuven.cs.oss.sonarfacade.SonarFacade;

public class BoxDimensionHandler implements ParameterHandler {

	private final static Logger LOG = LoggerFactory.getLogger(BoxDimensionHandler.class);

	private ParameterHandler next;

	private String keyWidth = "boxwidth";
	private String keyHeight = "boxheight";


	SonarFacade sf;

	public BoxDimensionHandler(SonarFacade sf) {
		this.sf = sf;
	}

	@Override
	public void setNext(ParameterHandler handler) {
		this.next = handler;
	}

	@Override
	public void handleRequest(Chart chart, ChartParameters params) {
		ResourceProperty widthProperty = createBoxDimensionRP(keyWidth, params);
		ResourceProperty heightProperty = createBoxDimensionRP(keyHeight, params);
		
		chart.setWidthProperty(widthProperty);
		chart.setHeightProperty(heightProperty);
		
		next.handleRequest(chart, params);

	}

	/**
	 * Create a new resource property for the given box dimension and add it to the resource property manager
	 * @param dimension The given box dimension (currently, only boxwidth and boxheight are supported)
	 * @throws Exception if the creation of the resource property fails
	 */
	private ResourceProperty createBoxDimensionRP(String dimension, ChartParameters params){
		String dimensionValue = retrieveValue(dimension, params);
		ResourceProperty rp;
		if(dimensionValue.matches("[0-9]")){
			rp = new ConstantResourceProperty(Integer.parseInt(dimensionValue));
		}
		else{
			rp = new SonarResourceProperty(sf, sf.findMetric(dimensionValue));
		}

		return rp;
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
