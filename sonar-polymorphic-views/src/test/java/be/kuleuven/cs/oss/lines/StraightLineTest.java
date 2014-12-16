package be.kuleuven.cs.oss.lines;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import be.kuleuven.cs.oss.datautils.Color;
import be.kuleuven.cs.oss.datautils.Position;

public class StraightLineTest {
	
	private StraightLine line1;
	
	@Before
	public void setUp() throws Exception {
		Position origin1 = new Position(1,2);
		Position destination1 = new Position(3,4);
		int width1 = 2;
		Color color1 = new Color(5,6,7);
		line1 = new StraightLine(origin1, destination1, width1, color1);
		
	}
	
	@Test
	public void constructorTest(){
		assertEquals(line1.getColor().getRed(), 5);
		assertEquals(line1.getColor().getGreen(), 6);
		assertEquals(line1.getColor().getBlue(), 7);
		assertEquals(line1.getWidth(), 2);
		assertEquals(line1.getOrigin().getX(), 1);
		assertEquals(line1.getOrigin().getY(), 2);
		assertEquals(line1.getDestination().getX(), 3);
		assertEquals(line1.getDestination().getY(), 4);
	}

	@Test
	public void testEqualPositions() {
		Position origin2 = new Position(1,2);
		Position destination2 = new Position(3,4);
		int width2 = 2;
		Color color2 = new Color(5,6,7);
		StraightLine line2 = new StraightLine(origin2, destination2, width2, color2);
		assertEquals(line1, line2);
	}
	
	@Test
	public void testUnEqualOrigins() {
		Position origin2 = new Position(0,0);
		Position destination2 = new Position(3,4);
		int width2 = 2;
		Color color2 = new Color(5,6,7);
		StraightLine line2 = new StraightLine(origin2, destination2, width2, color2);
		assertNotEquals(line1, line2);
	}
	
	@Test
	public void testUnequalDestinations() {
		Position origin2 = new Position(1,2);
		Position destination2 = new Position(0,0);
		int width2 = 2;
		Color color2 = new Color(5,6,7);
		StraightLine line2 = new StraightLine(origin2, destination2, width2, color2);
		assertNotEquals(line1, line2);
	}
	
	@Test
	public void testUnequalWidths() {
		Position origin2 = new Position(1,2);
		Position destination2 = new Position(3,4);
		int width2 = 1;
		Color color2 = new Color(5,6,7);
		StraightLine line2 = new StraightLine(origin2, destination2, width2, color2);
		assertNotEquals(line1, line2);
	}
	
	@Test
	public void testUnequalColors() {
		Position origin2 = new Position(1,2);
		Position destination2 = new Position(3,4);
		int width2 = 2;
		Color color2 = new Color(0,0,0);
		StraightLine line2 = new StraightLine(origin2, destination2, width2, color2);
		assertNotEquals(line1, line2);
	}
	
}
