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
public class TrapezoidFactoryTest {

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
		
		TrapezoidFactory tf = mock(TrapezoidFactory.class);
		when(tf.create(any(Resource.class))).thenCallRealMethod();
		when(tf.getLeftLineProperty()).thenReturn(rp);
		when(tf.getBaseLineProperty()).thenReturn(rp);
		when(tf.getRightLineProperty()).thenReturn(rp);
		when(tf.getRedProperty()).thenReturn(rp);
		when(tf.getGreenProperty()).thenReturn(rp);
		when(tf.getBlueProperty()).thenReturn(rp);
		
		
		Resource res = mock(Resource.class);
		when(res.getName()).thenReturn("SweetBabyJesus");
		
		Position position = new Position(0,0);
		
		int leftLine = 5;
		int baseLine = 6;
		int rightLine = 7;
		Color color = new Color(8,9,10);

		
		Trapezoid trapezoid = new Trapezoid(position, leftLine, baseLine, rightLine, color,"SweetBabyJesus");
		Trapezoid trapezoid2 = (Trapezoid) tf.create(res);
		
		
		System.out.println("trapezoid1 x: " +trapezoid.getX() + ", trapezoid2 x: " + trapezoid2.getX() );
		System.out.println("trapezoid1 y: " +trapezoid.getY() + ", trapezoid2 y: " + trapezoid2.getY() );
		System.out.println("trapezoid1 width: " +trapezoid.getWidth() + ", trapezoid2 width: " + trapezoid2.getWidth() );
		System.out.println("trapezoid1 height: " +trapezoid.getHeight() + ", trapezoid2 height: " + trapezoid2.getHeight());
		System.out.println("trapezoid1 rgb red: " +trapezoid.getColor().getRed() + ", trapezoid2 rgb red: " + trapezoid2.getColor().getRed());
		System.out.println("trapezoid1 rgb blue: " +trapezoid.getColor().getBlue() + ", trapezoid2 rgb blue: " + trapezoid2.getColor().getBlue());
		System.out.println("trapezoid1 rgb green: " +trapezoid.getColor().getGreen() + ", trapezoid2 rgb green: " + trapezoid2.getColor().getGreen());
		System.out.println("trapezoid1 name: " +trapezoid.getName() + ", trapezoid2 name: " + trapezoid2.getName() );
		System.out.println("trapezoid1 leftLine: " +trapezoid.getLeftLine() + ", trapezoid2 leftLine: " + trapezoid2.getLeftLine() );
		System.out.println("trapezoid1 baseLine: " +trapezoid.getBaseLine() + ", trapezoid2 baseLine: " + trapezoid2.getBaseLine() );
		System.out.println("trapezoid1 rightLine: " +trapezoid.getRightLine() + ", trapezoid2 rightLine: " + trapezoid2.getRightLine() );
		
		
		
		assertEquals(trapezoid, trapezoid2);
		
	}

}
