package be.kuleuven.cs.oss.control;

import javax.persistence.NoResultException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import be.kuleuven.cs.oss.datautils.ParamValueRetriever;
import be.kuleuven.cs.oss.sonarfacade.SonarFacade;

/**
 * A resource type parameter wrapper that is able to return all the resources based on the chart parameters
 * 
 * @author jeroenreinenbergh
 *
 */
public class ResourceTypeParameter {
	
	private static final String DEFAULT_RESOURCE_TYPE = "classes";

	private String key = "resources";
	
	private final static Logger LOG = LoggerFactory.getLogger(ChartParameter.class);

	/**
	 * Retrieves the resource type
	 * @param sf an intstance of SonarFacade
	 * @param params the given parameter value retriever
	 * @return the value for the resource type key
	 * @throws NoResultException if the value for the resource type key cannot be retrieved
	 */
	public String getResourceType(SonarFacade sf, ParamValueRetriever params) {
		return params.retrieveValue(key, DEFAULT_RESOURCE_TYPE);
	}
}