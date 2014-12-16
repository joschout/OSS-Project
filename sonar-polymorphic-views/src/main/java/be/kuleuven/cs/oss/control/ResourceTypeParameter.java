package be.kuleuven.cs.oss.control;

import javax.persistence.NoResultException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.sonar.api.charts.ChartParameters;

import be.kuleuven.cs.oss.sonarfacade.SonarFacade;

public class ResourceTypeParameter {
	
	private static final String DEFAULT_RESOURCE_TYPE = "classes";

	private String key = "resources";
	
	private final static Logger LOG = LoggerFactory.getLogger(ChartParameter.class);

	/**
	 * Retrieves the resource type
	 * @param sf an intstance of SonarFacade
	 * @param params the given chartparameters
	 * @return the value for the resource type key in the given chartparameters
	 * @throws NoResultException if the value for the resource type key cannot be retrieved
	 */
	public String getResourceType(SonarFacade sf, ChartParameters params) throws NoResultException{
		String result = params.getValue(key, DEFAULT_RESOURCE_TYPE, false);
		if(result.equals("")){
			LOG.info("retrieve value with default failed");
			throw new NoResultException("value with default not retrieved");
		}
		return result;
	}
}