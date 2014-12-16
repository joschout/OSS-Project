package be.kuleuven.cs.oss.lines;

import be.kuleuven.cs.oss.datautils.Color;
import be.kuleuven.cs.oss.datautils.Position;

public class StraightLine extends Line{

	/**
	 * Creates a new straight line
	 * @param orig the given origin as a position
	 * @param dest the given destination as a position
	 * @param w the given width
	 * @param c the given color
	 */
	public StraightLine(Position orig, Position dest, int w, Color c) {
		super(orig, dest, w, c);
	}

}
