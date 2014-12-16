package be.kuleuven.cs.oss.resourcevisualizations;

import be.kuleuven.cs.oss.datautils.Color;
import be.kuleuven.cs.oss.datautils.Position;
import be.kuleuven.cs.oss.resourceproperties.ResourceProperty;
import be.kuleuven.cs.oss.sonarfacade.Resource;

public class CircleFactory implements ResourceVisualizationCreator {

	
	private ResourceProperty diameterProperty;
	private ResourceProperty redProperty;
	private ResourceProperty greenProperty;
	private ResourceProperty blueProperty;
	
	public CircleFactory( ) {
	}
	
	
	@Override
	public ResourceVisualization create(Resource resource) {

		Position position = new Position(0, 0);
		int diameter = getDiameterProperty().getValue(resource).intValue();
		Color color = new Color(getRedProperty().getValue(resource).intValue(), getGreenProperty().getValue(resource).intValue(), getBlueProperty().getValue(resource).intValue());
		Circle circle = new Circle(position, diameter, color, resource.getName());
		return circle;
	}

	public ResourceProperty getDiameterProperty() {
		return diameterProperty;
	}

	public void setDiameterProperty(ResourceProperty diameterProperty) {
		this.diameterProperty = diameterProperty;
	}

	public ResourceProperty getRedProperty() {
		return redProperty;
	}

	public void setRedProperty(ResourceProperty redProperty) {
		this.redProperty = redProperty;
	}

	public ResourceProperty getGreenProperty() {
		return greenProperty;
	}

	public void setGreenProperty(ResourceProperty greenProperty) {
		this.greenProperty = greenProperty;
	}

	public ResourceProperty getBlueProperty() {
		return blueProperty;
	}

	public void setBlueProperty(ResourceProperty blueProperty) {
		this.blueProperty = blueProperty;
	}

}
