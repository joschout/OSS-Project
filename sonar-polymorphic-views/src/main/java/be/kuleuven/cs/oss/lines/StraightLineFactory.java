package be.kuleuven.cs.oss.lines;

import be.kuleuven.cs.oss.datautils.Color;
import be.kuleuven.cs.oss.datautils.Position;

/**
 * 
 * @author jeroenreinenbergh
 *
 */
public class StraightLineFactory implements LineFactory {
	
	private Position DEFAULT_POSITION;
	private int DEFAULT_WIDTH;
	private Color DEFAULT_COLOR;

	public void setDEFAULT_POSITION(Position dEFAULT_POSITION) {
		DEFAULT_POSITION = dEFAULT_POSITION;
	}

	public void setDEFAULT_WIDTH(int dEFAULT_WIDTH) {
		DEFAULT_WIDTH = dEFAULT_WIDTH;
	}

	public void setDEFAULT_COLOR(Color dEFAULT_COLOR) {
		DEFAULT_COLOR = dEFAULT_COLOR;
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
