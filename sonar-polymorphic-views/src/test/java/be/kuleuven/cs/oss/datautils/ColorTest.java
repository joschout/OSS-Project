package be.kuleuven.cs.oss.datautils;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class ColorTest {
	private Color color;
	
	@Before
	public void setUp(){
		 color = new Color (1,2,3);
	}
	
	@Test
	public void constructorTest() {
		assertEquals(color.getRed(), 1);
		assertEquals(color.getGreen(), 2);
		assertEquals(color.getBlue(), 3);
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void setRedToLow(){
		color.setRed(-1);
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void setGreenToLow(){
		color.setGreen(-1);
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void setBlueToLow(){
		color.setBlue(-1);
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void setRedToHigh(){
		color.setRed(256);
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void setGreenToHigh(){
		color.setGreen(256);
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void setBlueToHigh(){
		color.setBlue(256);
	}
	
	@Test 
	public void testEqualColors(){
		Color color2 = new Color(1,2,3);
		Color color3 = new Color(1,2,3);
		assertEquals(color2, color3);
	}
	
	@Test 
	public void testUnEqualColorsRed(){
		Color color2 = new Color(0,2,3);
		Color color3 = new Color(1,2,3);
		assertNotEquals(color2, color3);
	}
	@Test 
	public void testUnEqualColorsGreen(){
		Color color2 = new Color(1,0,3);
		Color color3 = new Color(1,2,3);
		assertNotEquals(color2, color3);
	}
	@Test 
	public void testUnEqualColorsBlue(){
		Color color2 = new Color(1,2,0);
		Color color3 = new Color(1,2,3);
		assertNotEquals(color2, color3);
	}
	
	
	
	
	
	
	

}
