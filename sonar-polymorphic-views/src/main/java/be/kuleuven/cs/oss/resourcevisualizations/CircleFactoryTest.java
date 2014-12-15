package be.kuleuven.cs.oss.resourcevisualizations;

import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import org.junit.Test;

import be.kuleuven.cs.oss.datautils.Color;
import be.kuleuven.cs.oss.datautils.Position;
import be.kuleuven.cs.oss.resourceproperties.ResourceProperty;
import be.kuleuven.cs.oss.sonarfacade.Resource;

// mockito, how does it work?  http://fruzenshtein.com/junit-and-mockito/
public class CircleFactoryTest {

	@Test
	public void test() {
		
		ResourceProperty rp = mock(ResourceProperty.class);
		
		when(rp.getValue(any(Resource.class)))
		.thenReturn((double) 5)
		.thenReturn((double) 6)
		.thenReturn((double) 7)
		.thenReturn((double) 8)
		.thenReturn((double) 9)
		.thenReturn((double) 10)
		.thenReturn((double) 11)
		.thenReturn((double) 12)
		.thenReturn((double) 13)
		.thenReturn((double) 14);
		
		CircleFactory cf = mock(CircleFactory.class);
		when(cf.create(any(Resource.class))).thenCallRealMethod();
		when(cf.getDiameterProperty()).thenReturn(rp);
		when(cf.getRedProperty()).thenReturn(rp);
		when(cf.getGreenProperty()).thenReturn(rp);
		when(cf.getBlueProperty()).thenReturn(rp);
		
		Resource res = mock(Resource.class);
		when(res.getName()).thenReturn("SweetBabyJesus");
		
		Position position = new Position(0,0);
		 int diameter = 5;
		Color color = new Color(6,7,8);
		

		Circle circle = new Circle(position, diameter, color, "SweetBabyJesus");
		Circle circle2 = (Circle) cf.create(res);
		
		
		System.out.println("circle1 x: " + circle.getX() + ", circle2 x: " + circle2.getX() );
		System.out.println("circle1 y: " + circle.getY() + ", circle2 y: " + circle2.getY() );
		System.out.println("circle1 width: " +circle.getWidth() + ", circle2 width: " + circle2.getWidth() );
		System.out.println("circle1 height: " +circle.getHeight() + ", circle2 height: " + circle2.getHeight());
		System.out.println("circle1 rgb red: " +circle.getColor().getRed() + ", circle2 rgb red: " + circle2.getColor().getRed());
		System.out.println("circle1 rgb blue: " +circle.getColor().getBlue() + ", circle2 rgb blue: " + circle2.getColor().getBlue());
		System.out.println("circle1 rgb green: " +circle.getColor().getGreen() + ", circle2 rgb green: " + circle2.getColor().getGreen());
		System.out.println("circle1 name: " +circle.getName() + ", circle2 name: " + circle2.getName() );
		
		assertEquals(circle.getPosition(), circle2.getPosition());
		assertEquals(circle.getSize(), circle2.getSize());
		assertEquals(circle.getColor(), circle2.getColor());
		assertEquals(circle.getName(), circle2.getName());
		
	}

}
