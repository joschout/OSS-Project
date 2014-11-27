package be.kuleuven.cs.oss.datautils;

/**
 * Position class to represent positions in a 2 dimensional plane
 * @author Lennart De Graef
 *
 */

public class Position {
	
	private int x;
	private int y;
	
	public Position(int x, int y){
		this.x = x;
		this.y = y;
	}
	
	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}
	
    public void setX(int x) {
		this.x = x;
	}

	public void setY(int y) {
		this.y = y;
	}

	/**
     * @param   other
     * @return  result == ((other != null) && (getClass() == other.getClass()) && (getX() == (Position other).getX()) && (getY() == (Position other).getY())
     */
    @Override
    public boolean equals (Object other) {
        if ((other == null) || (this.getClass() != other.getClass())) {
            return false;
        }
        Position otherPosition = (Position) other;
        return ((this.getX() == otherPosition.getX()) && (this.getY() == otherPosition.getY()));
    }
}
