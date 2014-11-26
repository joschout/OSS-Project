package be.kuleuven.cs.oss.datautils;

/**
 * Position class to represent positions in a 2 dimensional plane
 * @author Lennart De Graef
 *
 */

public class Position {
	
	private double x;
	private double y;
	
	public Position(double x, double y){
		this.x = x;
		this.y = y;
	}
	
	public double getX() {
		return x;
	}

	public double getY() {
		return y;
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
