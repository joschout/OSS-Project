import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;
import static org.mockito.Mockito.*;

import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.util.Iterator;

import org.junit.Test;

//check  http://gojko.net/2009/10/23/mockito-in-six-easy-examples/

/**
 * Deze klasse is nutteloos voor het project!
 * @author eline vanermen
 *
 */
public class MockitoTestje {

	

	@Test
	public void iterator_will_return_hello_world(){
		//arrange
		Iterator i=mock(Iterator.class);
		when(i.next()).thenReturn("Hello").thenReturn("World");
		//act
		String result=i.next()+" "+i.next()+" "+i.next();
		//assert
		System.out.println(result);
		assertEquals("Hello World World", result);
	}

	@Test
	public void with_arguments(){
		Comparable c=mock(Comparable.class);
		when(c.compareTo("Test")).thenReturn(1);
		assertEquals(1,c.compareTo("Test"));
	}
	
	@Test
	public void with_unspecified_arguments(){
		Comparable c=mock(Comparable.class);
		when(c.compareTo(anyInt())).thenReturn(-1);
		assertEquals(-1,c.compareTo(5));
	}
	
	@Test(expected=IOException.class)
	public void OutputStreamWriter_rethrows_an_exception_from_OutputStream() 
		throws IOException{
		OutputStream mock=mock(OutputStream.class);
		OutputStreamWriter osw=new OutputStreamWriter(mock);
		doThrow(new IOException()).when(mock).close();
		osw.close();
	}

	@Test
	public void OutputStreamWriter_Closes_OutputStream_on_Close()
		 throws IOException{
		OutputStream mock=mock(OutputStream.class);
		OutputStreamWriter osw=new OutputStreamWriter(mock);
		osw.close();
		verify(mock).close();
	}
	
	
	
}
