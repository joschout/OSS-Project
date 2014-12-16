package be.kuleuven.cs.oss.datautils;

import static org.junit.Assert.*;

import org.junit.Test;

public class PositionTest {

	@Test
	public void testEqualPosition() {
		Position position1 = new Position(1,2);
		Position position2 = new Position(1,2);
		assertEquals(position1, position2);
	}
	
	@Test
	public void testUnequalXCoord() {
		Position position1 = new Position(0,2);
		Position position2 = new Position(1,2);
		assertNotEquals(position1, position2);
	}
	
	@Test
	public void testUnequalYCoord() {
		Position position1 = new Position(1,2);
		Position position2 = new Position(1,0);
		assertNotEquals(position1, position2);
	}
	

}
