package be.kuleuven.cs.oss.control;

import javax.persistence.NoResultException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.sonar.api.charts.ChartParameters;

import be.kuleuven.cs.oss.datautils.ParamValueRetriever;
import be.kuleuven.cs.oss.sonarfacade.Resource;
import be.kuleuven.cs.oss.sonarfacade.SonarFacade;

/**
 * A parent parameter wrapper that is able to return the parent resource based on the chart parameters
 * 
 * @author jeroenreinenbergh
 *
 */
public class ParentParameter {
	
	private String key = "parent";
	
	private final static Logger LOG = LoggerFactory.getLogger(ParentParameter.class);

	/**
	 * Retrieves the parent resource
	 * @param sf an instance of SonarFacade
	 * @param params the given parameter value retriever
	 * @return the requested parent resource
	 * @throws NoResultException if the value for the parent key cannot be found
	 * @throws IllegalArgumentException if the value for the parent key is not valid
	 */
	public Resource getParentResource(SonarFacade sf, ParamValueRetriever params) throws NoResultException,IllegalArgumentException{
		String parent = params.retrieveValue(key);
		
		Resource resource = null;
		resource = sf.findResource(parent);
		if(resource == null){
			LOG.info("retrieve parent failed");
			throw new IllegalArgumentException("Parent not valid");
		}
		return resource;
		
	}

}
