package be.kuleuven.cs.oss.lines;

import be.kuleuven.cs.oss.datautils.Color;
import be.kuleuven.cs.oss.datautils.Position;

/**
 * A factory that creates straight lines
 * 
 * @author jeroenreinenbergh
 *
 */
public class StraightLineFactory implements LineFactory {
	
	private Position DEFAULT_POSITION;
	private int DEFAULT_WIDTH;
	private Color DEFAULT_COLOR;

	public void setDefaultPosition(Position def) {
		DEFAULT_POSITION = def;
	}

	public void setDefaultWidth(int def) {
		DEFAULT_WIDTH = def;
	}

	public void setDefaultColor(Color def) {
		DEFAULT_COLOR = def;
	}

	@Override
	public StraightLine create() {
		return new StraightLine(DEFAULT_POSITION, DEFAULT_POSITION, DEFAULT_WIDTH, DEFAULT_COLOR);
	}

	@Override
	public StraightLine create(Position orig, Position dest) {
		return new StraightLine(orig, dest, DEFAULT_WIDTH, DEFAULT_COLOR);
	}

	@Override
	public StraightLine create(Position orig, Position dest, int w) {
		return new StraightLine(orig, dest, w, DEFAULT_COLOR);
	}	
	
	@Override
	public StraightLine create(Position orig, Position dest, int w, Color c) {
		return new StraightLine(orig, dest, w, c);
	}

}
