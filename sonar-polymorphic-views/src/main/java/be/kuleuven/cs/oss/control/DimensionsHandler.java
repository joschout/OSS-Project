package be.kuleuven.cs.oss.control;

import javax.persistence.NoResultException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.sonar.api.charts.ChartParameters;

import be.kuleuven.cs.oss.resourceproperties.ConstantResourceProperty;
import be.kuleuven.cs.oss.resourceproperties.ResourceProperty;
import be.kuleuven.cs.oss.resourceproperties.SonarResourceProperty;
import be.kuleuven.cs.oss.resourcevisualizations.BoxFactory;
import be.kuleuven.cs.oss.resourcevisualizations.CircleFactory;
import be.kuleuven.cs.oss.resourcevisualizations.ResourceVisualizationFactory;
import be.kuleuven.cs.oss.resourcevisualizations.TrapezoidFactory;
import be.kuleuven.cs.oss.sonarfacade.Metric;
import be.kuleuven.cs.oss.sonarfacade.SonarFacade;

public class DimensionsHandler implements IHandler<ResourceVisualizationFactory> {

	private final static Logger LOG = LoggerFactory.getLogger(DimensionsHandler.class);

	private IHandler<ResourceVisualizationFactory> next;

	
	private static final ConstantResourceProperty DEFAULT_BOXWIDTH = new ConstantResourceProperty(20);
	private static final ConstantResourceProperty DEFAULT_BOXHEIGHT = new ConstantResourceProperty(20);
	private static final ConstantResourceProperty DEFAULT_CIRCLEDIAM = new ConstantResourceProperty(20);
	private static final ConstantResourceProperty DEFAULT_TRAPSIDE1 = new ConstantResourceProperty(20);
	private static final ConstantResourceProperty DEFAULT_TRAPSIDE2 = new ConstantResourceProperty(20);
	private static final ConstantResourceProperty DEFAULT_TRAPSIDE3 = new ConstantResourceProperty(20);
	
	SonarFacade sf;

	/**
	 * Creates a new dimensionhandler based on the given sonarfacade
	 * @param sf an instance of SonarFacade
	 */
	public DimensionsHandler(SonarFacade sf) {
		this.sf = sf;
	}

	@Override
	public void setNext(IHandler<ResourceVisualizationFactory> handler) {
		this.next = handler;
	}

	/**
	 * Creates the dimension properties for the given resourcevisualization factory, depending on the type of factory (currently only box, circle and trap are supported) and sets them in this factory
	 * @throws IllegalArgumentException if the factory cannot be cast to a specific type of factory
	 */
	@Override
	public void handleRequest(ResourceVisualizationFactory rvf, ChartParameters params) throws IllegalArgumentException{
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
	 * Creates a new dimension property that is defined by the given dimension key
	 * @param key the given key for the dimension property
	 * @param params the given chartparameters
	 * @param def the default property to be set in case the given key is not a valid one
	 * @return the resource property that is defined by the given dimension key if that key is valid, else return the given default resource property
	 */
	private ResourceProperty createDimensionRP(String key, ChartParameters params, ResourceProperty def){
		String dimensionValue;
		ResourceProperty rp;
		
		try{
			dimensionValue = retrieveValue(key, params);
		}
		catch(NoResultException e){
			LOG.info("setting default "+key);
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
	 * @param key the given parameter key
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
