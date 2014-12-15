package be.kuleuven.cs.oss.resourcevisualizations;

import be.kuleuven.cs.oss.resourceproperties.ResourceProperty;
import be.kuleuven.cs.oss.sonarfacade.Resource;

public class ConstantShapeDecider implements ShapeDecider {

	private ResourceVisualizationFactory resourceVisualizationFactory;
	private ResourceProperty resourceProperty;
		
	public ResourceVisualizationFactory getResourceVisualizationFactory() {
		return resourceVisualizationFactory;
	}


	public void setResourceVisualizationFactory(
			ResourceVisualizationFactory resourceVisualizationFactory) {
		this.resourceVisualizationFactory = resourceVisualizationFactory;
	}


	public ResourceProperty getResourceProperty() {
		return resourceProperty;
	}

	public void setResourceProperty(ResourceProperty resourceProperty) {
		this.resourceProperty = resourceProperty;
	}
	
	
	@Override
	public ResourceVisualization create(Resource res) {
		return getResourceVisualizationFactory().create(res);
	}

}
