package be.kuleuven.cs.oss.charts;

import static org.junit.Assert.fail;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.ArrayList;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import be.kuleuven.cs.oss.datautils.Color;
import be.kuleuven.cs.oss.datautils.Position;
import be.kuleuven.cs.oss.datautils.Size;
import be.kuleuven.cs.oss.resourcevisualizations.Box;
import be.kuleuven.cs.oss.resourcevisualizations.ResourceVisualizationCreator;
import be.kuleuven.cs.oss.sonarfacade.Resource;

public class ScatterPlotTest2 {

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		
		ScatterPlot scatterplot = new ScatterPlot(new Size(100,150));
		scatterplot.setMaxResourcePosition(new Position(0,0));
		scatterplot.setMinResourcePosition(new Position(Integer.MAX_VALUE, Integer.MAX_VALUE));
		scatterplot.setMaxResourceSize(new Size(0,0));
		scatterplot.setMinResourceSize(new Size(Integer.MAX_VALUE, Integer.MAX_VALUE));
		
		Resource res = mock(Resource.class);
		ArrayList<Resource> reslist = new ArrayList<Resource>;
		for()
		
		ResourceVisualizationCreator rvc = mock(ResourceVisualizationCreator.class);
		Box box1 = new Box(new Position(10,10), new Size(10,10), new Color(10,10,10), "Sweet");
		Box box2 = new Box(new Position(100,100), new Size(100,100), new Color(50,50,50), "Baby");
		Box box3 = new Box(new Position(500,50), new Size(500,50), new Color(100,100,100), "Jesus");
		Box box4 = new Box(new Position(1000,100), new Size(100,10), new Color(150,150,150), "Nipples");
		when(rvc.create(any(Resource.class))).thenReturn(box1).thenReturn(box2).thenReturn(box3).thenReturn(box4);
		
		
	
	}

	@Before
	public void setUp() throws Exception {
	}

	
	
	@Test
	public void test() {
		fail("Not yet implemented");
	}

}
