package be.kuleuven.cs.oss.resourcevisualizations;

import be.kuleuven.cs.oss.datautils.Color;
import be.kuleuven.cs.oss.datautils.Position;

/**
 * Interface representing a Resource Visualization Factory
 * @author Lennart De Graef
 */
public interface ResourceVisualizationFactory {
	
	/**
	 * Creates a default ResourceVisualization of this type.
	 * As expected, this will use the default values of the RV to be created
	 * @return Default ResourceVisualization of this type
	 */
	public ResourceVisualization create();
	
	/**
	 * Creates a ResourceVisualization with the given position, the rest are the default values
	 * for that RV
	 * @param p Position to be set
	 * @return RV with the position p and default for all else
	 */
	public ResourceVisualization create(Position p);
	
	/**
	 * Creates a ResourceVisualization with the given position, width and height.
	 * @param p Position to be set
	 * @param width Width to be set
	 * @param height Height to be set
	 * @return ResourceVisualization with the given parameters.
	 */
	public ResourceVisualization create(Position p, int width, int height);
	
	/**
	 * Creates a ResourceVisualization with the given position, width and height and color
	 * @param p Position to be set
	 * @param width Width to be set
	 * @param height Height to be set
	 * @param c Color to be set
	 * @return ResourceVisualization with given parameters
	 */
	public ResourceVisualization create(Position p, int width, int height, Color c);
}
