package be.kuleuven.cs.oss.resourceproperties;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import be.kuleuven.cs.oss.sonarfacade.Resource;

/**
 * @author Milan
 *
 * 
 *
 */
public class ResourcePropertiesManager {

	
	private Map<String, ResourceProperty> properties;

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

		Iterator<Entry<String, ResourceProperty>> it = properties.entrySet().iterator();
		while (it.hasNext()) {
			Entry<String, ResourceProperty> pairs = it.next();
			
			String name = pairs.getKey();
			ResourceProperty resourceProperty = pairs.getValue();
			
			double value = resourceProperty.getValue(resource);
			
			toReturn.put(name, value);
			
			it.remove(); // avoids a ConcurrentModificationException
		}

		return toReturn;
	}
	
	public Double getPropertyValue(String name, Resource resource){
		ResourceProperty property = properties.get(name);
		return property.getValue(resource);
	}

}
