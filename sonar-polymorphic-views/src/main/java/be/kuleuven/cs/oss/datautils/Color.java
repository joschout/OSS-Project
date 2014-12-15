package be.kuleuven.cs.oss.datautils;

/**
 * Class representing colors.
 * Can be constructed in grayscale or with RGB arguments.
 * Internally grayscale is represented the same way as RGB.
 * @author Lennart De Graef
 *
 */

public class Color {
	private int r;
	private int g;
	private int b;
	
	public Color(int gray) throws IllegalArgumentException{
		this(gray, gray, gray);
	}
	
	public Color(int r, int g, int b) throws IllegalArgumentException{
		setRed(r);
		setGreen(g);
		setBlue(b);
	}

	public int getRed() {
		return r;
	}

	public void setRed(int r) throws IllegalArgumentException{
		if(r < 0) throw new IllegalArgumentException("Can't set a color value less than zero");
		if(r > 255) throw new IllegalArgumentException("Can't set color value above 255");
		
		this.r = r;
	}

	public int getGreen() {
		return g;
	}

	public void setGreen(int g) throws IllegalArgumentException{
		if(g < 0) throw new IllegalArgumentException("Can't set a color value less than zero");
		if(g > 255) throw new IllegalArgumentException("Can't set color value above 255");
		this.g = g;
	}

	public int getBlue() {
		return b;
	}

	public void setBlue(int b) throws IllegalArgumentException {
		if(b < 0) throw new IllegalArgumentException("Can't set a color value less than zero");
		if(b > 255) throw new IllegalArgumentException("Can't set color value above 255");
		this.b = b;
	}

    @Override
    public boolean equals (Object other) {
        if ((other == null) || (this.getClass() != other.getClass())) {
            return false;
        }
        Color otherColor = (Color) other;
        return ((this.getRed() == otherColor.getRed()) 
        		&& (this.getBlue() == otherColor.getBlue())
        		&& (this.getGreen() == otherColor.getGreen()));
    }
}
