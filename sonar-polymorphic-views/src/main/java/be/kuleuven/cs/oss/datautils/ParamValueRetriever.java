package be.kuleuven.cs.oss.datautils;

import javax.persistence.NoResultException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.sonar.api.charts.ChartParameters;

public class ParamValueRetriever {
	
	private final static Logger LOG = LoggerFactory.getLogger(ParamValueRetriever.class);
	
	private ChartParameters chartParams;
	
	public ParamValueRetriever(ChartParameters params){
		this.chartParams = params;
	}
	
	/**
	 * Retrieve a parameter value for the given parameter key
	 * @param key the given parameter key
	 * @return the retrieved parameter value
	 * @throws NoResultException if the value for the given key cannot be retrieved
	 */
	public String retrieveValue(String key) throws NoResultException{
		String result = this.chartParams.getValue(key);

		if(result.equals("")){
			LOG.info("retrieving "+key+" value failed");
			throw new NoResultException("value not retrieved");
		}

		return result;
	}
	
	/**
	 * Retrieve a parameter value for the given parameter key with a given default value
	 * @param key the given parameter key
	 * @param def the given default value
	 * @return the retrieved parameter value if it is found, else the given default value
	 */
	public String retrieveValue(String key, String def) throws NoResultException{
		String result = this.chartParams.getValue(key);

		if(result.equals("")){
			result = def;
		}

		return result;
	}
}
