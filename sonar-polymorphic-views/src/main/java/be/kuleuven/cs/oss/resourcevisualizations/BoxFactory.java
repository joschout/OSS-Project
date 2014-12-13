package be.kuleuven.cs.oss.resourcevisualizations;

import be.kuleuven.cs.oss.datautils.Color;
import be.kuleuven.cs.oss.datautils.Position;
import be.kuleuven.cs.oss.datautils.Size;
import be.kuleuven.cs.oss.resourceproperties.ResourceProperty;
import be.kuleuven.cs.oss.sonarfacade.Resource;

/**
 *  The class BoxFactory is a specific implementation of the ResourceVisualizationFactory class.
 *  It creates a ResourceVisualization in the form of a Box object for one specific resource,
 *   based on the given map of ResourcePropert names and their values,
 *    which it gets as an argument in its create method.
 *    
 * @author Jonas
 *
 */
public class BoxFactory implements ResourceVisualizationFactory {
	
	private ResourceProperty widthProperty;
	private ResourceProperty heightProperty;
	private ResourceProperty redProperty;
	private ResourceProperty greenProperty;
	private ResourceProperty blueProperty;
	
	

	public BoxFactory(ResourceProperty widthProperty,
			ResourceProperty heightProperty, ResourceProperty redProperty,
			ResourceProperty greenProperty, ResourceProperty blueProperty) {
		this.widthProperty = widthProperty;
		this.heightProperty = heightProperty;
		this.redProperty = redProperty;
		this.greenProperty = greenProperty;
		this.blueProperty = blueProperty;
	}



	/**
	 * {@inheritDoc}
	 * 
	 */
	@Override
	public ResourceVisualization create(Resource res) {
		
		Position position = new Position(0, 0);
		Size size = new Size( widthProperty.getValue(res).intValue(), heightProperty.getValue(res).intValue());
		Color color = new Color(redProperty.getValue(res).intValue(), greenProperty.getValue(res).intValue(), blueProperty.getValue(res).intValue());
		Box box = new Box(position, size, color, res.getName());
		return box;
	}

}
