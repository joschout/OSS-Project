package be.kuleuven.cs.oss.resourcevisualizations;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import be.kuleuven.cs.oss.datautils.Color;
import be.kuleuven.cs.oss.datautils.Position;
import be.kuleuven.cs.oss.datautils.Size;

public class ResourceVisualisationTest {

	Trapezoid trapezoid = new Trapezoid(new Position(10,10), 3,7,6,new Color(255,255,255),"Sweet");
	Trapezoid trapezoid2 = new Trapezoid(new Position(10,10), 5,3,4,new Color(255,255,255),"Baby");
	
	Circle circle = new Circle(new Position(20,15), 15, new Color(20,46,23), "Jesus");
	
	@Before
	public void setUp() throws Exception {
		
	}

	
	@Test
	public void circleChangeSize() throws Exception {
		circle.setSize(new Size(10,10));
		assertTrue(circle.getDiameter()==10);
		
	}

}
