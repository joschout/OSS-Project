package be.kuleuven.cs.oss.resourcevisualizations;

import be.kuleuven.cs.oss.datautils.Color;
import be.kuleuven.cs.oss.datautils.Position;
import be.kuleuven.cs.oss.resourceproperties.ResourceProperty;
import be.kuleuven.cs.oss.sonarfacade.Resource;

public class TrapezoidFactory implements ResourceVisualizationFactory {

	private ResourceProperty leftLineProperty;
	private ResourceProperty baseLineProperty;
	private ResourceProperty rightLineProperty;
	ResourceProperty redProperty;
	private ResourceProperty greenProperty;
	private ResourceProperty blueProperty;
	
	public TrapezoidFactory( ResourceProperty leftProperty, ResourceProperty baseProperty, ResourceProperty rightProperty,ResourceProperty redProperty,
			ResourceProperty greenProperty, ResourceProperty blueProperty) {
		this.leftLineProperty = leftProperty;
		this.baseLineProperty = baseProperty;
		this.baseLineProperty = baseProperty;
		this.redProperty = redProperty;
		this.greenProperty = greenProperty;
		this.blueProperty = blueProperty;
	}

	@Override
	public ResourceVisualization create(Resource resource) {
		Position position = new Position(0,0);
		int leftLine = leftLineProperty.getValue(resource).intValue();
		int baseLine = baseLineProperty.getValue(resource).intValue();
		int rightLine = rightLineProperty.getValue(resource).intValue();
		Color color = new Color(redProperty.getValue(resource).intValue(), greenProperty.getValue(resource).intValue(), blueProperty.getValue(resource).intValue());
		Trapezoid trapezoid = new Trapezoid(position, leftLine, baseLine, rightLine, color, resource.getName());
		return trapezoid;
	}
}
