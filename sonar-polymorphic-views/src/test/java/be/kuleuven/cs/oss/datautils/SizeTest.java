package be.kuleuven.cs.oss.datautils;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class SizeTest {

	
	private Size size;
	@Before
	public void setUp(){
		size = new Size(1,2);
	}
	
	@Test
	public void constructorTest() {
		assertEquals(size.getWidth(), 1);
		assertEquals(size.getHeight(), 2);
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void setWidthToLow(){
		size.setWidth(-1);
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void setHeightToLow(){
		size.setHeight(-1);
	}
	
	@Test 
	public void testEqualSizes(){
		Size size2 = new Size(1,2);
		Size size3 = new Size(1,2);
		assertEquals(size2, size3);
	}
	
	@Test 
	public void testUnequalWidth(){
		Size size2 = new Size(0,2);
		Size size3 = new Size(1,2);
		assertNotEquals(size2, size3);;
	}
	@Test 
	public void testUnequalHeight(){
		Size size2 = new Size(1,2);
		Size size3 = new Size(1,0);
		assertNotEquals(size2, size3);
	}
	
	
	
}
