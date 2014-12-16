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
import be.kuleuven.cs.oss.resourcevisualizations.CircleFactory;
import be.kuleuven.cs.oss.resourcevisualizations.ResourceVisualizationCreator;
import be.kuleuven.cs.oss.resourcevisualizations.TrapezoidFactory;
import be.kuleuven.cs.oss.sonarfacade.Metric;
import be.kuleuven.cs.oss.sonarfacade.SonarFacade;

public class DimensionsHandler implements IHandler<ResourceVisualizationCreator> {

	private final static Logger LOG = LoggerFactory.getLogger(DimensionsHandler.class);

	private IHandler<ResourceVisualizationCreator> next;

	
	private static final ConstantResourceProperty DEFAULT_BOXWIDTH = new ConstantResourceProperty(20);
	private static final ConstantResourceProperty DEFAULT_BOXHEIGHT = new ConstantResourceProperty(20);
	private static final ConstantResourceProperty DEFAULT_CIRCLEDIAM = new ConstantResourceProperty(20);
	private static final ConstantResourceProperty DEFAULT_TRAPSIDE1 = new ConstantResourceProperty(20);
	private static final ConstantResourceProperty DEFAULT_TRAPSIDE2 = new ConstantResourceProperty(20);
	private static final ConstantResourceProperty DEFAULT_TRAPSIDE3 = new ConstantResourceProperty(20);
	
	SonarFacade sf;

	public DimensionsHandler(SonarFacade sf) {
		this.sf = sf;
	}

	@Override
	public void setNext(IHandler<ResourceVisualizationCreator> handler) {
		this.next = handler;
	}

	@Override
	public void handleRequest(ResourceVisualizationCreator rvf, ChartParameters params) {
		if(BoxFactory.class.isInstance(rvf)){
			((BoxFactory) rvf).setWidthProperty(createDimensionRP("boxwidth",params,DEFAULT_BOXWIDTH));
			((BoxFactory) rvf).setHeightProperty(createDimensionRP("boxheight",params,DEFAULT_BOXHEIGHT));
		}
		else if(CircleFactory.class.isInstance(rvf)){
			((CircleFactory) rvf).setDiameterProperty(createDimensionRP("circlediam",params,DEFAULT_CIRCLEDIAM));
		}
		else if(TrapezoidFactory.class.isInstance(rvf)){
			((TrapezoidFactory) rvf).setLeftLineProperty(createDimensionRP("trapside1",params,DEFAULT_TRAPSIDE1));
			((TrapezoidFactory) rvf).setBaseLineProperty(createDimensionRP("trapside2",params,DEFAULT_TRAPSIDE2));
			((TrapezoidFactory) rvf).setRightLineProperty(createDimensionRP("trapside3",params,DEFAULT_TRAPSIDE3));
		}
		else throw new IllegalArgumentException("RVF could not be cast to specific factory");

		if(next != null) {
			next.handleRequest(rvf, params);
		}
	}

	/**
	 * Create a new resource property for the given box dimension and add it to the resource property manager
	 * @param dimension The given box dimension (currently, only boxwidth and boxheight are supported)
	 * @throws Exception if the creation of the resource property fails
	 */
	private ResourceProperty createDimensionRP(String dimension, ChartParameters params, ResourceProperty def){
		String dimensionValue;
		ResourceProperty rp;
		
		try{
			dimensionValue = retrieveValue(dimension, params);
		}
		catch(NoResultException e){
			LOG.info("setting default "+dimension);
			return def;
		}
		
		if(dimensionValue.matches("[0-9]+")){
			rp = new ConstantResourceProperty(Integer.parseInt(dimensionValue));
		}
		else{
			Metric metric = sf.findMetric(dimensionValue);
			if(metric == null){
				throw new NoResultException("metric not found");
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
			LOG.info("failed to retrieve value for "+key);
			throw new NoResultException("value not retrieved");
		}
		
		return result;
	}

}
