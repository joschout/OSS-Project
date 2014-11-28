package be.kuleuven.cs.oss.resourceproperties;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import be.kuleuven.cs.oss.polymorphicviews.plugin.PolymorphicViewsChart;
import be.kuleuven.cs.oss.sonarfacade.Resource;
import be.kuleuven.cs.oss.trees.TreeNodeRV;

/**
 * 
 * 
 * 
 * 
 * @author Milan
 *
 */
public class ResourcePropertiesManager {

	
	private Map<String, ResourceProperty> properties;
	private final static Logger LOG = LoggerFactory.getLogger(PolymorphicViewsChart.class);
	/**
	 * 
	 */
	public ResourcePropertiesManager() {
		properties = new HashMap<String, ResourceProperty>();
	}

	/**
	 * 
	 * @param name
	 * @param prop
	 */
	public void addProperty(String name, ResourceProperty prop) {
		properties.put(name, prop);
	}

	/**
	 * 
	 * @param resource
	 * @return
	 */
	public HashMap<String, Double> getPropertyValues(Resource resource) {
		
		HashMap<String, Double> toReturn = new HashMap<String, Double>();
		for(Map.Entry<String, ResourceProperty> entry : properties.entrySet()){
			
			String name = entry.getKey();
			ResourceProperty resourceProperty = entry.getValue();
			
			double value = resourceProperty.getValue(resource);
			toReturn.put(name, value);
		}

		return toReturn;
	}
	
	public Double getPropertyValue(String name, Resource resource){
		ResourceProperty property = properties.get(name);
		return property.getValue(resource);
	}
	
	public ResourceProperty getResourceProperty(String name){
		return properties.get(name);
	}

}
