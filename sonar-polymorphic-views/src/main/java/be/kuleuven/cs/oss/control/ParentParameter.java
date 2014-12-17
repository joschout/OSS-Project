package be.kuleuven.cs.oss.control;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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
	 * @throws IllegalArgumentException if the metric for the parent key cannot be found
	 */
	public Resource getParentResource(SonarFacade sf, ParamValueRetriever params) throws IllegalArgumentException{
		String parent = params.retrieveValue(key);
		
		Resource resource = null;
		resource = sf.findResource(parent);
		if(resource == null){
			throw new IllegalArgumentException(key+" metric not found");
		}
		return resource;
		
	}

}
