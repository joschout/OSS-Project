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
		.thenReturn((double) 9);
		
		BoxFactory bf = mock(BoxFactory.class);
		
		Resource res = mock(Resource.class);
		when(res.getName()).thenReturn("SweetBabyJesus");
		
		Position pos = new Position(0,0);
		Size size = new Size (5,6);
		Color color = new Color(7,8,9);
		Box box = new Box(pos, size, color,"SweetBabyJesus");
		System.out.println(box.getX() + " - box 1 - " + box.getWidth() );
		//geraakt hier niet in omdat die geen resource heeft?
		Box box2 = (Box) bf.create(res);
		System.out.println(box2.getX() + " - box 2 - " + box2.getWidth());
		assertEquals(box, box2);
	}

}
