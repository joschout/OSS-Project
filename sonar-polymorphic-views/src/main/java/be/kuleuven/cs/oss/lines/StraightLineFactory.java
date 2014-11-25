package be.kuleuven.cs.oss.lines;

import be.kuleuven.cs.oss.datautils.Position;

public class StraightLineFactory implements LineFactory {
	
	private static final Position DEFAULT_POSITION = new Position(0,0);
	private static final int DEFAULT_WIDTH = 1;

	

	@Override
	public StraightLine create() {
		return new StraightLine(DEFAULT_POSITION, DEFAULT_POSITION, DEFAULT_WIDTH);
	}

	@Override
	public StraightLine create(Position orig, Position dest) {
		return new StraightLine(orig, dest, DEFAULT_WIDTH);
	}

	@Override
	public StraightLine create(Position orig, Position dest, int w) {
		return new StraightLine(orig, dest, w);
	}

}
