package be.kuleuven.cs.oss.datautils;

public class Color {
	private int r;
	private int g;
	private int b;
	
	public Color(int gray) throws IllegalArgumentException{
		this(gray, gray, gray);
	}
	
	public Color(int r, int g, int b) throws IllegalArgumentException{
		setR(r);
		setG(g);
		setB(b);
	}

	public int getR() {
		return r;
	}

	public void setR(int r) throws IllegalArgumentException{
		if(r < 0) throw new IllegalArgumentException("Can't set a color value less than zero");
		if(r > 255) throw new IllegalArgumentException("Can't set color value above 255");
		
		this.r = r;
	}

	public int getG() {
		return g;
	}

	public void setG(int g) throws IllegalArgumentException{
		if(g < 0) throw new IllegalArgumentException("Can't set a color value less than zero");
		if(g > 255) throw new IllegalArgumentException("Can't set color value above 255");
		this.g = g;
	}

	public int getB() {
		return b;
	}

	public void setB(int b) throws IllegalArgumentException {
		if(b < 0) throw new IllegalArgumentException("Can't set a color value less than zero");
		if(b > 255) throw new IllegalArgumentException("Can't set color value above 255");
		this.b = b;
	}

}
