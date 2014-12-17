package be.kuleuven.cs.oss.control;

import javax.persistence.NoResultException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import be.kuleuven.cs.oss.datautils.ParamValueRetriever;
import be.kuleuven.cs.oss.resourceproperties.ConstantResourceProperty;
import be.kuleuven.cs.oss.resourceproperties.ResourceProperty;
import be.kuleuven.cs.oss.resourceproperties.SonarResourceProperty;
import be.kuleuven.cs.oss.resourcevisualizations.BoxFactory;
import be.kuleuven.cs.oss.resourcevisualizations.CircleFactory;
import be.kuleuven.cs.oss.resourcevisualizations.ResourceVisualizationFactory;
import be.kuleuven.cs.oss.resourcevisualizations.TrapezoidFactory;
import be.kuleuven.cs.oss.sonarfacade.Metric;
import be.kuleuven.cs.oss.sonarfacade.SonarFacade;

/**
 * A handler for the shape dimensions in the resource visualization factory
 * 
 * @author jeroenreinenbergh
 *
 */
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
	 * Creates a new dimensionshandler based on the given sonarfacade
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
	 * Creates the dimension properties for the given resourcevisualization factory,
	 * depending on the type of factory (currently only box, circle and trap are supported)
	 * and sets them in this factory
	 * @throws ClassCastException if the factory cannot be cast to a specific type of factory
	 */
	@Override
	public void handleRequest(ResourceVisualizationFactory rvf, ParamValueRetriever params) throws ClassCastException{
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
		else throw new ClassCastException("RVF could not be cast to specific factory");

		if(next != null) {
			next.handleRequest(rvf, params);
		}
	}

	/**
	 * Creates a new dimension property that is defined by the given dimension key
	 * @param key the given key for the dimension property
	 * @param params the given parameter value retriever
	 * @param def the default property to be set in case the given key is not a valid one
	 * @return the resource property that is defined by the given dimension key if that key is valid, else return the given default resource property
	 * @throws IllegalArgumentException if the metric for the given key cannot be found
	 */
	private ResourceProperty createDimensionRP(String key, ParamValueRetriever params, ResourceProperty def) throws IllegalArgumentException{
		String dimensionValue;
		ResourceProperty rp;
		
		try{
			dimensionValue = params.retrieveValue(key);
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
				throw new IllegalArgumentException(key+" metric not found");
			}
			rp = new SonarResourceProperty(sf, metric);
		}

		return rp;
	}

}
