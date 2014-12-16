package be.kuleuven.cs.oss.resourcevisualizations;

import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import org.junit.Test;

import be.kuleuven.cs.oss.datautils.Color;
import be.kuleuven.cs.oss.datautils.Position;
import be.kuleuven.cs.oss.datautils.Size;
import be.kuleuven.cs.oss.resourceproperties.ResourceProperty;
import be.kuleuven.cs.oss.sonarfacade.Resource;

// mockito, how does it work?  http://fruzenshtein.com/junit-and-mockito/
public class BoxFactoryTest {

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
		
		BoxFactory bf = mock(BoxFactory.class);
		when(bf.create(any(Resource.class))).thenCallRealMethod();
		when(bf.getWidthProperty()).thenReturn(rp);
		when(bf.getHeightProperty()).thenReturn(rp);
		when(bf.getRedProperty()).thenReturn(rp);
		when(bf.getGreenProperty()).thenReturn(rp);
		when(bf.getBlueProperty()).thenReturn(rp);
		
		
		Resource res = mock(Resource.class);
		when(res.getName()).thenReturn("SweetBabyJesus");
		
		Position position = new Position(0,0);
		Size size = new Size (5,6);
		Color color = new Color(7,8,9);

		
		Box box = new Box(position, size, color,"SweetBabyJesus");
		Box box2 = (Box) bf.create(res);
		
		
		System.out.println("box1 x: " +box.getX() + ", box2 x: " + box2.getX() );
		System.out.println("box1 y: " +box.getY() + ", box2 y: " + box2.getY() );
		System.out.println("box1 width: " +box.getWidth() + ", box2 width: " + box2.getWidth() );
		System.out.println("box1 height: " +box.getHeight() + ", box2 height: " + box2.getHeight());
		System.out.println("box1 rgb red: " +box.getColor().getRed() + ", box2 rgb red: " + box2.getColor().getRed());
		System.out.println("box1 rgb blue: " +box.getColor().getBlue() + ", box2 rgb blue: " + box2.getColor().getBlue());
		System.out.println("box1 rgb green: " +box.getColor().getGreen() + ", box2 rgb green: " + box2.getColor().getGreen());
		System.out.println("box1 name: " +box.getName() + ", box2 name: " + box2.getName() );
		
		assertEquals(box, box2);		
	}

}
