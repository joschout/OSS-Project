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

	
	public Resource getParentResource(SonarFacade sf, ChartParameters params) {
		String parent = params.getValue(key);
		
		if(parent.equals("")){
			LOG.info("retrieve value failed");
			throw new NoResultException("value not retrieved");
		}
		
		Resource resource = null;
		resource = sf.findResource(parent);
		if(resource == null){
			LOG.info("retrieve parent failed");
			throw new NoResultException("Parent not valid");
		}
		return resource;
		
	}

}
