package be.kuleuven.cs.oss.resourceproperties;

import be.kuleuven.cs.oss.sonarfacade.Resource;

public class ConstantResourceProperty implements ResourceProperty {

	private int value;
	
	public ConstantResourceProperty(int value) {
		this.value = value;
	}
	
	@Override
	public Double getValue(Resource r) {
		return (double) this.value;
	
	}

	@Override
	public String getPropertyName() {
		return "Constant";
	}

}
