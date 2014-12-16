package be.kuleuven.cs.oss.control;

import javax.persistence.NoResultException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.sonar.api.charts.ChartParameters;

import be.kuleuven.cs.oss.sonarfacade.Resource;
import be.kuleuven.cs.oss.sonarfacade.SonarFacade;

public class ParentParameter {
	
	private String key = "parent";
	
	private final static Logger LOG = LoggerFactory.getLogger(ParentParameter.class);

	/**
	 * Retrieves the parent resource
	 * @param sf an instance of SonarFacade
	 * @param params the given chartparameters
	 * @return the requested parent resource
	 * @throws NoResultException if the value for the parent key cannot be found
	 * @throws IllegalArgumentException if the value for the parent key is not valid
	 */
	public Resource getParentResource(SonarFacade sf, ChartParameters params) throws NoResultException,IllegalArgumentException{
		String parent = params.getValue(key);
		
		if(parent.equals("")){
			LOG.info("retrieve value failed");
			throw new NoResultException("value not retrieved");
		}
		
		Resource resource = null;
		resource = sf.findResource(parent);
		if(resource == null){
			LOG.info("retrieve parent failed");
			throw new IllegalArgumentException("Parent not valid");
		}
		return resource;
		
	}

}
