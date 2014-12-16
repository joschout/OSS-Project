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

public class CircleFactoryTest2 {
	
	
	ResourceProperty diameterProperty = mock(ResourceProperty.class);
	ResourceProperty redProperty = mock(ResourceProperty.class);
	ResourceProperty greenProperty = mock(ResourceProperty.class);
	ResourceProperty blueProperty = mock(ResourceProperty.class);

	Resource resource = mock(Resource.class);
	
	CircleFactory CircleFactory = new CircleFactory();
	
	@Before
	public void setUp() {
		CircleFactory.setDiameterProperty(diameterProperty);
		CircleFactory.setRedProperty(redProperty);
		CircleFactory.setGreenProperty(greenProperty);
		CircleFactory.setBlueProperty(blueProperty);
		
		when(diameterProperty.getValue(any(Resource.class))).thenReturn((double) 6);
		when(redProperty.getValue(any(Resource.class))).thenReturn((double) 7);
		when(greenProperty.getValue(any(Resource.class))).thenReturn((double) 8);
		when(blueProperty.getValue(any(Resource.class))).thenReturn((double) 9);
		
		when(resource.getName()).thenReturn("SweetBabyJesus");
	}
	
	@Test
	public void test() {
		
		Position position = new Position(0,0);
		int diameter = 6;
		Color color = new Color(7,8,9);
		Circle circle1 = (Circle)CircleFactory.create(resource);
		Circle circle2 = new Circle(position, diameter, color, "SweetBabyJesus");
		assertEquals(circle1, circle2);
	}
}
