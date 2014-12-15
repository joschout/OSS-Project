package be.kuleuven.cs.oss.control;

import java.util.NoSuchElementException;

import javax.persistence.NoResultException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.sonar.api.charts.ChartParameters;

import be.kuleuven.cs.oss.resourceproperties.ConstantResourceProperty;
import be.kuleuven.cs.oss.resourceproperties.ResourceProperty;
import be.kuleuven.cs.oss.resourceproperties.SonarResourceProperty;
import be.kuleuven.cs.oss.resourcevisualizations.BoxFactory;
import be.kuleuven.cs.oss.resourcevisualizations.ResourceVisualizationFactory;
import be.kuleuven.cs.oss.sonarfacade.Metric;
import be.kuleuven.cs.oss.sonarfacade.SonarFacade;

public class BoxDimensionHandler implements IHandler<ResourceVisualizationFactory> {

	private final static Logger LOG = LoggerFactory.getLogger(BoxDimensionHandler.class);

	private IHandler<ResourceVisualizationFactory> next;

	private String keyWidth = "boxwidth";
	private String keyHeight = "boxheight";
	
	private static final ConstantResourceProperty DEFAULT_WIDTH = new ConstantResourceProperty(20);
	private static final ConstantResourceProperty DEFAULT_HEIGHT = new ConstantResourceProperty(20);



	SonarFacade sf;

	public BoxDimensionHandler(SonarFacade sf) {
		this.sf = sf;
	}

	@Override
	public void setNext(IHandler<ResourceVisualizationFactory> handler) {
		this.next = handler;
	}

	@Override
	public void handleRequest(ResourceVisualizationFactory rvf, ChartParameters params) {
		ResourceProperty widthProperty;
		try {
			widthProperty = createBoxDimensionRP(keyWidth, params);
		}
		catch(NoResultException e) {
			widthProperty = DEFAULT_WIDTH;
		}
		ResourceProperty heightProperty;
		try {
			heightProperty = createBoxDimensionRP(keyHeight, params);
		}
		catch(NoResultException e) {
			heightProperty = DEFAULT_HEIGHT;
		}
		
		((BoxFactory) rvf).setWidthProperty(widthProperty);
		((BoxFactory) rvf).setHeightProperty(heightProperty);
		
		if(next != null) {
			next.handleRequest(rvf, params);
		}

	}

	/**
	 * Create a new resource property for the given box dimension and add it to the resource property manager
	 * @param dimension The given box dimension (currently, only boxwidth and boxheight are supported)
	 * @throws Exception if the creation of the resource property fails
	 */
	private ResourceProperty createBoxDimensionRP(String dimension, ChartParameters params){
		String dimensionValue = retrieveValue(dimension, params);
		ResourceProperty rp;
		if(dimensionValue.matches("[0-9]+")){
			rp = new ConstantResourceProperty(Integer.parseInt(dimensionValue));
		}
		else{
			Metric metric = sf.findMetric(dimensionValue);
			if(metric == null){
				throw new NoSuchElementException("metric not found");
			}
			rp = new SonarResourceProperty(sf, metric);
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
			LOG.info("retrieve value failed, setting defaults");
			throw new NoResultException("value not retrieved");
		}
		
		return result;
	}


}
