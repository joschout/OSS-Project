package be.kuleuven.cs.oss.resourcevisualizations;

import be.kuleuven.cs.oss.datautils.Color;
import be.kuleuven.cs.oss.datautils.Position;
import be.kuleuven.cs.oss.datautils.Size;

/**
 * Factory class for the box resource visualization
 * @author Lennart De Graef
 *
 */
public class BoxResourceVisualizationFactory implements ResourceVisualizationFactory{
	
	private static final Size DEFAULT_SIZE = new Size(20 ,20);
	private static final Position DEFAULT_POSITION = new Position(0, 0);
	private static final Color DEFAULT_COLOR = new Color(255, 255, 255);
	private static final String DEFAULT_NAME = "";

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Box create() {
		Box out = new Box(DEFAULT_POSITION, DEFAULT_SIZE, DEFAULT_COLOR, DEFAULT_NAME);
		return out;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Box create(Position p) {
		Box out = new Box(p,DEFAULT_SIZE, DEFAULT_COLOR, DEFAULT_NAME);
		return out;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Box create(Position p, Size size) throws IllegalArgumentException{
		Box out = new Box(p, size, DEFAULT_COLOR, DEFAULT_NAME); 
		return out;
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public Box create(Position p, Size size, Color c) throws IllegalArgumentException{
		Box out = new Box(p, size, c, DEFAULT_NAME); 
		return out;
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public Box create(Position p, Size size, Color c, String name) throws IllegalArgumentException{
		Box out = new Box(p, size, c, name); 
		return out;
	}
	
}
