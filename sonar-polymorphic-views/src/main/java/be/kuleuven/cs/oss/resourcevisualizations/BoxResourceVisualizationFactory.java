package be.kuleuven.cs.oss.resourcevisualizations;

import be.kuleuven.cs.oss.datautils.Position;

/**
 * Factory class for the box resource visualization
 * @author Lennart De Graef
 *
 */
public class BoxResourceVisualizationFactory implements ResourceVisualizationFactory{
	
	private static final int DEFAULT_WIDTH = 20;
	private static final int DEFAULT_HEIGHT = 20;
	private static final Position DEFAULT_POSITION = new Position(0, 0);

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Box create() {
		Box out = new Box(DEFAULT_POSITION, DEFAULT_WIDTH, DEFAULT_HEIGHT, 0);
		return out;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Box create(Position p) {
		Box out = new Box(p, DEFAULT_WIDTH, DEFAULT_HEIGHT, 0);
		return out;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Box create(Position p, int width, int height) {
		Box out = new Box(p, width, height, 0); //TODO try to figure out a way to do colors right
		return out;
	}
	
}
