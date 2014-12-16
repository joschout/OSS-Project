package be.kuleuven.cs.oss.resourcevisualizations;

import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import org.junit.Before;
import org.junit.Test;

import be.kuleuven.cs.oss.datautils.Color;
import be.kuleuven.cs.oss.datautils.Position;
import be.kuleuven.cs.oss.datautils.Size;
import be.kuleuven.cs.oss.resourceproperties.ResourceProperty;
import be.kuleuven.cs.oss.sonarfacade.Resource;

public class TrapezoidFactoryTest2 {
	
	
	private ResourceProperty leftLineProperty = mock(ResourceProperty.class);
	private ResourceProperty baseLineProperty = mock(ResourceProperty.class);
	private ResourceProperty rightLineProperty = mock(ResourceProperty.class);
	ResourceProperty redProperty = mock(ResourceProperty.class);
	ResourceProperty greenProperty = mock(ResourceProperty.class);
	ResourceProperty blueProperty = mock(ResourceProperty.class);

	Resource resource = mock(Resource.class);
	
	TrapezoidFactory TrapezoidFactory = new TrapezoidFactory();
	
	@Before
	public void setUp() {
		TrapezoidFactory.setLeftLineProperty(leftLineProperty);
		TrapezoidFactory.setBaseLineProperty(baseLineProperty);
		TrapezoidFactory.setRightLineProperty(rightLineProperty);
		TrapezoidFactory.setRedProperty(redProperty);
		TrapezoidFactory.setGreenProperty(greenProperty);
		TrapezoidFactory.setBlueProperty(blueProperty);
		
		when(leftLineProperty.getValue(any(Resource.class))).thenReturn((double) 5);
		when(baseLineProperty.getValue(any(Resource.class))).thenReturn((double) 6);
		when(rightLineProperty.getValue(any(Resource.class))).thenReturn((double) 7);
		when(redProperty.getValue(any(Resource.class))).thenReturn((double) 8);
		when(greenProperty.getValue(any(Resource.class))).thenReturn((double) 9);
		when(blueProperty.getValue(any(Resource.class))).thenReturn((double) 10);
		
		when(resource.getName()).thenReturn("SweetBabyJesus");
	}
	
	@Test
	public void test() {
		
		Position position = new Position(0,0);
		int leftLine = 5;
		int baseLine = 6;
		int rightLine = 7;
		Color color = new Color(8,9,10);
		Trapezoid trapezoid1 = (Trapezoid)TrapezoidFactory.create(resource);
		Trapezoid trapezoid2 = new Trapezoid(position, leftLine, baseLine, rightLine, color, "SweetBabyJesus");
		assertEquals(trapezoid1, trapezoid2);
	}
}
