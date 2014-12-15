package be.kuleuven.cs.oss.resourceproperties;

import be.kuleuven.cs.oss.sonarfacade.Resource;

public class ResourcePropertyDecorator implements ResourceProperty {
	
	private ResourceProperty property;
	
	public ResourcePropertyDecorator(ResourceProperty rpToBeDecorated) {
		this.property = rpToBeDecorated;
	}
	
	@Override
	public Double getValue(Resource r) {
		return property.getValue(r);
	}

	@Override
	public String getPropertyName() {
		return property.getPropertyName();
	}
	
	public ResourceProperty getProperty(){
		return property;
	}

}
