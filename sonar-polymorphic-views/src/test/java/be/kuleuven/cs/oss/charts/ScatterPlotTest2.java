package be.kuleuven.cs.oss.charts;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;

import org.junit.Before;
import org.junit.Test;

import be.kuleuven.cs.oss.datautils.Color;
import be.kuleuven.cs.oss.datautils.Position;
import be.kuleuven.cs.oss.datautils.Size;
import be.kuleuven.cs.oss.resourceproperties.ResourceProperty;
import be.kuleuven.cs.oss.resourcevisualizations.Box;
import be.kuleuven.cs.oss.resourcevisualizations.ResourceVisualizationCreator;
import be.kuleuven.cs.oss.sonarfacade.Resource;

public class ScatterPlotTest2 {

	private ScatterPlot scatterplot;
	private ResourceVisualizationCreator rvc;

	@Before
	public void setUp() throws Exception {

		scatterplot = new ScatterPlot(new Size(1500,1000));
		scatterplot.setMaxResourcePosition(new Position(0,0));
		scatterplot.setMinResourcePosition(new Position(Integer.MAX_VALUE, Integer.MAX_VALUE));
		scatterplot.setMaxResourceSize(new Size(0,0));
		scatterplot.setMinResourceSize(new Size(Integer.MAX_VALUE, Integer.MAX_VALUE));

		//change the 5 to 6 to get different result (originbox)
		Resource res = mock(Resource.class);
		ArrayList<Resource> reslist = new ArrayList<Resource>();
		for(int i=1; i<5; i++){
			reslist.add(res);
		}

		scatterplot.setResources(reslist);

		rvc = mock(ResourceVisualizationCreator.class);
		Box box1 = new Box(new Position(0,0), new Size(10,10), new Color(10,10,10), "Sweet");
		Box box2 = new Box(new Position(0,0), new Size(20,20), new Color(50,50,50), "Baby");
		Box box3 = new Box(new Position(0,0), new Size(50,50), new Color(100,100,100), "Jesus");
		Box box4 = new Box(new Position(0,0), new Size(15,30), new Color(150,150,150), "Nipples");
		Box box5 = new Box(new Position(0,0), new Size(5,5), new Color(150,150,150), "oorsprong");

		scatterplot.setRvf(rvc);
		when(rvc.create(any(Resource.class))).thenReturn(box1).thenReturn(box2).thenReturn(box3).thenReturn(box4).thenReturn(box5);

		ResourceProperty xMetric = mock(ResourceProperty.class);
		when(xMetric.getValue(any(Resource.class))).thenReturn(10.).thenReturn(50.).thenReturn(50.).thenReturn(5.).thenReturn(0.);
		when(xMetric.getPropertyName()).thenReturn("Holyness");
		ResourceProperty yMetric = mock(ResourceProperty.class);
		when(yMetric.getValue(any(Resource.class))).thenReturn(30.).thenReturn(45.).thenReturn(40.).thenReturn(20.).thenReturn(0.);
		when(yMetric.getPropertyName()).thenReturn("Cuteness");
		scatterplot.setAxisMetrics(xMetric, yMetric);

	}





	@Test
	public void test() {
		BufferedImage bi = scatterplot.draw();
		try {

			//JONAS 
			//File outputfile = new File("D:\\eclipse\\workspace OSS\\OSS-Project\\sonar-polymorphic-views\\src\\test\\java\\be\\kuleuven\\cs\\oss\\charts\\scattertest.png");
			//ELINE
			File outputfile = new File("C:\\Users\\eline vanermen\\Documents\\Github\\OSS-Project\\sonar-polymorphic-views\\src\\test\\java\\be\\kuleuven\\cs\\oss\\charts\\scattertest.png");
			ImageIO.write(bi, "png", outputfile);
		} catch (IOException e) {

		}
	}
	
	@Test
	public void giantResourceTest() {
		
		Box box1 = new Box(new Position(0,0), new Size(5,5), new Color(10,10,10), "little box");
		Box box2 = new Box(new Position(0,0), new Size(20,10), new Color(50,50,50), "box2");
		Box box3 = new Box(new Position(0,0), new Size(5,20), new Color(100,100,100), "box3");
		Box box4 = new Box(new Position(0,0), new Size(9000,800), new Color(150,150,150), "Giantass");
		Box box5 = new Box(new Position(0,0), new Size(0,0), new Color(150,150,150), "origin");

		scatterplot.setRvf(rvc);
		when(rvc.create(any(Resource.class))).thenReturn(box1).thenReturn(box2).thenReturn(box3).thenReturn(box4).thenReturn(box5);

		ResourceProperty xMetric = mock(ResourceProperty.class);
		when(xMetric.getValue(any(Resource.class))).thenReturn(10.).thenReturn(20.).thenReturn(50.).thenReturn(30.).thenReturn(0.);
		when(xMetric.getPropertyName()).thenReturn("What");
		ResourceProperty yMetric = mock(ResourceProperty.class);
		when(yMetric.getValue(any(Resource.class))).thenReturn(30.).thenReturn(5.).thenReturn(40.).thenReturn(20.).thenReturn(0.);
		when(yMetric.getPropertyName()).thenReturn("Ever");
		scatterplot.setAxisMetrics(xMetric, yMetric);
		BufferedImage bi = scatterplot.draw();
		try {

			//JONAS 
			//File outputfile = new File("D:\\eclipse\\workspace OSS\\OSS-Project\\sonar-polymorphic-views\\src\\test\\java\\be\\kuleuven\\cs\\oss\\charts\\scattertest.png");
			//ELINE
			File outputfile = new File("C:\\Users\\eline vanermen\\Documents\\Github\\OSS-Project\\sonar-polymorphic-views\\src\\test\\java\\be\\kuleuven\\cs\\oss\\charts\\scGiant.png");
			ImageIO.write(bi, "png", outputfile);
		} catch (IOException e) {

		}
	}

}
