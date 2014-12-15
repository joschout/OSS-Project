package be.kuleuven.cs.oss.datautils;

public class Size {
	private int width;
	private int height;
	
	/**
	 * 
	 * @param width
	 * @param height
	 * @throws IllegalArgumentException
	 */
	public Size(int width, int height){
		this.width = width;
		this.height = height;
	}
	
	public int getWidth() {
		return width;
	}
	
	
	public void setWidth(int w) throws IllegalArgumentException{
		if(w < 0) 
			throw new IllegalArgumentException("Width of a size cannot be less than or equal to zero");
		this.width = w;
	}

	public int getHeight() {
		return height;
	}
	
	public void setHeight(int h) throws IllegalArgumentException{
		if(h < 0) 
			throw new IllegalArgumentException("Height of a size cannot be less than or equal to zero");
		this.height = h;
	}

    /**
     * @param   other
     * @return  result == ((other != null) && (getClass() == other.getClass()) && (getWidth() == (Size other).getWidth()) && (getHeight() == (Size other).getHeight())
     */
    @Override
    public boolean equals (Object other) {
        if ((other == null) || (this.getClass() != other.getClass())) {
            return false;
        }
        Size otherSize = (Size) other;
        return ((this.getWidth() == otherSize.getWidth()) && (this.getHeight() == otherSize.getHeight()));
    }
}
