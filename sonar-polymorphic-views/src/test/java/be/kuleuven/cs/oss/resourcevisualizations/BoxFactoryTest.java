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

public class BoxFactoryTest {
	
	
	ResourceProperty widthProperty = mock(ResourceProperty.class);
	ResourceProperty heightProperty = mock(ResourceProperty.class);
	ResourceProperty redProperty = mock(ResourceProperty.class);
	ResourceProperty greenProperty = mock(ResourceProperty.class);
	ResourceProperty blueProperty = mock(ResourceProperty.class);

	Resource resource = mock(Resource.class);
	
	BoxFactory boxFactory = new BoxFactory();
	
	@Before
	public void setUp() {
		boxFactory.setWidthProperty(widthProperty);
		boxFactory.setHeightProperty(heightProperty);
		boxFactory.setRedProperty(redProperty);
		boxFactory.setGreenProperty(greenProperty);
		boxFactory.setBlueProperty(blueProperty);
		
		when(widthProperty.getValue(any(Resource.class))).thenReturn((double) 5);
		when(heightProperty.getValue(any(Resource.class))).thenReturn((double) 6);
		when(redProperty.getValue(any(Resource.class))).thenReturn((double) 7);
		when(greenProperty.getValue(any(Resource.class))).thenReturn((double) 8);
		when(blueProperty.getValue(any(Resource.class))).thenReturn((double) 9);
		
		when(resource.getName()).thenReturn("SweetBabyJesus");
	}
	
	@Test
	public void test() {
		
		Position position = new Position(0,0);
		Size size = new Size (5,6);
		Color color = new Color(7,8,9);
		Box box1 = (Box)boxFactory.create(resource);
		Box box2 = new Box(position, size, color, "SweetBabyJesus");
		assertEquals(box1, box2);
	}
}
