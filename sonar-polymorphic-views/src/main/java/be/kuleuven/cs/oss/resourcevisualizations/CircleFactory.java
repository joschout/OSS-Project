package be.kuleuven.cs.oss.resourcevisualizations;

import be.kuleuven.cs.oss.datautils.Color;
import be.kuleuven.cs.oss.datautils.Position;
import be.kuleuven.cs.oss.datautils.Size;
import be.kuleuven.cs.oss.resourceproperties.ResourceProperty;
import be.kuleuven.cs.oss.sonarfacade.Resource;

public class CircleFactory implements ResourceVisualizationFactory {

	
	private ResourceProperty radiusProperty;
	private ResourceProperty redProperty;
	private ResourceProperty greenProperty;
	private ResourceProperty blueProperty;
	
	public CircleFactory( ResourceProperty radiusProperty, ResourceProperty redProperty,
			ResourceProperty greenProperty, ResourceProperty blueProperty) {
		this.radiusProperty = radiusProperty;
		this.redProperty = redProperty;
		this.greenProperty = greenProperty;
		this.blueProperty = blueProperty;
	}
	
	@Override
	public ResourceVisualization create(Resource resource) {

		Position position = new Position(0, 0);
		int radius = radiusProperty.getValue(resource).intValue();
		Color color = new Color(redProperty.getValue(resource).intValue(), greenProperty.getValue(resource).intValue(), blueProperty.getValue(resource).intValue());
		Circle circle = new Circle(position, radius, color, resource.getName());
		return circle;
	}

}
