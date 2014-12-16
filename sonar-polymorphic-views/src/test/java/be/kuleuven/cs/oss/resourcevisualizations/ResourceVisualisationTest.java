package be.kuleuven.cs.oss.resourcevisualizations;

import static org.junit.Assert.assertTrue;

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
	
	@Test
	public void trapezoidChangeSize1(){
		trapezoid.setSize(new Size(10, 18));
		assertTrue(trapezoid.getBaseLine() == 10);
		assertTrue(trapezoid.getRightLine() == 18);
		assertTrue(trapezoid.getLeftLine() == 9);
	}
	
	@Test
	public void trapezoidChangeSize2(){
		trapezoid2.setSize(new Size(0, 20));
		assertTrue(trapezoid2.getBaseLine() == 0);
		assertTrue(trapezoid2.getRightLine() == 16);
		assertTrue(trapezoid2.getLeftLine() == 20);
	}
	
	

}
