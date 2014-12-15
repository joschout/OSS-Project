package be.kuleuven.cs.oss.resourcevisualizations;

import be.kuleuven.cs.oss.datautils.Color;
import be.kuleuven.cs.oss.datautils.Position;
import be.kuleuven.cs.oss.resourceproperties.ResourceProperty;
import be.kuleuven.cs.oss.sonarfacade.Resource;

public class TrapezoidFactory implements ResourceVisualizationCreator {

	private ResourceProperty leftLineProperty;
	private ResourceProperty baseLineProperty;
	private ResourceProperty rightLineProperty;
	private ResourceProperty redProperty;
	private ResourceProperty greenProperty;
	private ResourceProperty blueProperty;
	
	
	public TrapezoidFactory( ) {
		
	}

	@Override
	public ResourceVisualization create(Resource resource) {
		Position position = new Position(0,0);
		int leftLine = getLeftLineProperty().getValue(resource).intValue();
		int baseLine = getBaseLineProperty().getValue(resource).intValue();
		int rightLine = getRightLineProperty().getValue(resource).intValue();
		Color color = new Color(getRedProperty().getValue(resource).intValue(), getGreenProperty().getValue(resource).intValue(), getBlueProperty().getValue(resource).intValue());
		Trapezoid trapezoid = new Trapezoid(position, leftLine, baseLine, rightLine, color, resource.getName());
		return trapezoid;
	}
	
	public ResourceProperty getLeftLineProperty() {
		return leftLineProperty;
	}

	public void setLeftLineProperty(ResourceProperty leftLineProperty) {
		this.leftLineProperty = leftLineProperty;
	}

	public ResourceProperty getBaseLineProperty() {
		return baseLineProperty;
	}

	public void setBaseLineProperty(ResourceProperty baseLineProperty) {
		this.baseLineProperty = baseLineProperty;
	}

	public ResourceProperty getRightLineProperty() {
		return rightLineProperty;
	}

	public void setRightLineProperty(ResourceProperty rightLineProperty) {
		this.rightLineProperty = rightLineProperty;
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
