package be.kuleuven.cs.oss.control;

import javax.persistence.NoResultException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.sonar.api.charts.ChartParameters;

import be.kuleuven.cs.oss.charts.Chart;
import be.kuleuven.cs.oss.sonarfacade.Resource;
import be.kuleuven.cs.oss.sonarfacade.SonarFacade;

public class ParentHandler implements ParameterHandler {
	
	private String key = "parent";
	
	private final static Logger LOG = LoggerFactory.getLogger(ChartHandler.class);
	
	private ParameterHandler next;

	private SonarFacade sf;
	
	public ParentHandler(SonarFacade sf) {
		this.sf = sf;
	}

	@Override
	public void setNext(ParameterHandler handler) {
		this.next = handler;
	}

	@Override
	public void handleRequest(Chart chart, ChartParameters params) {
		String parent = params.getValue(key);
		
		if(parent.equals("")){
			LOG.info("retrieve value failed");
			throw new NoResultException("value not retrieved");
		}
		
		try {
			Resource resource = sf.findResource(parent);
		}
		catch(Exception e){
			LOG.info("retrieve parent failed");
			throw new NoResultException("Parent not valid");
		}
		
		
	}

}
