package be.kuleuven.cs.oss.charts;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;

import org.junit.Test;

import be.kuleuven.cs.oss.datautils.Color;
import be.kuleuven.cs.oss.datautils.Position;
import be.kuleuven.cs.oss.datautils.Size;
import be.kuleuven.cs.oss.resourceproperties.ResourceProperty;
import be.kuleuven.cs.oss.resourcevisualizations.Box;
import be.kuleuven.cs.oss.resourcevisualizations.ResourceVisualization;

public class ScatterPlotTest {

	@Test
	public void rescaleTest() {
		ScatterPlot sp = mock(ScatterPlot.class);
		
		List<ResourceVisualization> RVList = new ArrayList<ResourceVisualization>();

		Box box1 = new Box(new Position(10,10), new Size(10,10), new Color(10,10,10), "Sweet");
		RVList.add(box1);
		Box box2 = new Box(new Position(100,100), new Size(100,100), new Color(50,50,50), "Baby");
		RVList.add(box2);
		Box box3 = new Box(new Position(500,50), new Size(500,50), new Color(100,100,100), "Jesus");
		RVList.add(box3);
		Box box4 = new Box(new Position(1000,100), new Size(100,10), new Color(150,150,150), "Nipples");
		RVList.add(box4);

		List<be.kuleuven.cs.oss.sonarfacade.Resource> reslist = new ArrayList<be.kuleuven.cs.oss.sonarfacade.Resource>();
		System.out.println("reslist is empty: "+ reslist.isEmpty());
		
		when(sp.getImageFrameSize()).thenReturn(new Size(100,100));
		when(sp.getResources()).thenReturn(reslist);  //for each over empty list does nothing
		when(sp.getResourceVisualizations()).thenReturn(RVList);

		ResourceProperty rp = mock(ResourceProperty.class);
		when(rp.getPropertyName()).thenReturn("x axis").thenReturn("y axis");
		System.out.println("hier is ie");
		//die geraakt hier niet in :o
		BufferedImage bi = sp.draw();

		try {

			File outputfile = new File("C:\\users\\eline vanermen\\Documents\\GitHub\\OSS-Project\\sonar-polymorphic-views\\src\\main\\java\\be\\kuleuven\\cs\\oss\\charts\\test\\scatterTest.png");
			ImageIO.write(bi, "png", outputfile);
		} catch (IOException e) {

		}

	}

}
