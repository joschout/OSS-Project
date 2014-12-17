package be.kuleuven.cs.oss.control;

import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.sonar.api.charts.ChartParameters;

import be.kuleuven.cs.oss.charts.Chart;
import be.kuleuven.cs.oss.charts.ScatterPlot;
import be.kuleuven.cs.oss.datautils.ParamValueRetriever;
import be.kuleuven.cs.oss.datautils.Size;
import be.kuleuven.cs.oss.resourceproperties.ResourceProperty;
import be.kuleuven.cs.oss.resourceproperties.SonarResourceProperty;
import be.kuleuven.cs.oss.sonarfacade.Metric;
import be.kuleuven.cs.oss.sonarfacade.Resource;
import be.kuleuven.cs.oss.sonarfacade.SonarFacade;

public class ScenarioControllerTest {

	private String queryString = "ck=polymorphic&type=scatter&parent=sonar:chartplugin&resources=classes&shape=metric&shape=circle&xmetric=lines&ymetric=comment_lines&circlecolor=min20.3max1000.28keylines";
	
	private static final String DEFAULT_RESOURCE_TYPE = "classes";
	private static final Size DEFAULT_SIZE = new Size(800,600);
	
	private String key1 = "parent";
	private String key2 = "resources";
	private String keyX = "xmetric";
	private String keyY = "ymetric";
	
	private SonarFacade sf = mock(SonarFacade.class);
	
	private Resource parentResource = mock(Resource.class);
	
	private ResourceProperty xMetric = mock(SonarResourceProperty.class);
	private ResourceProperty yMetric = mock(SonarResourceProperty.class);

	
	
	@Before
	public void setUp() {
		Resource res = mock(Resource.class);
		List<Resource> resources = new ArrayList<Resource>();
		for(int i=1; i<5; i++){
			resources.add(res);
		}
		
		when(xMetric.getValue(any(Resource.class))).thenReturn(10.).thenReturn(50.).thenReturn(50.).thenReturn(5.).thenReturn(0.);
		when(xMetric.getPropertyName()).thenReturn("Holyness");
		when(yMetric.getValue(any(Resource.class))).thenReturn(30.).thenReturn(45.).thenReturn(40.).thenReturn(20.).thenReturn(0.);
		when(yMetric.getPropertyName()).thenReturn("Cuteness");
		
		Metric metricObject = mock(Metric.class);
		
		when(sf.findResource(any(String.class))).thenReturn(parentResource);
		when(sf.findClasses(parentResource)).thenReturn(resources);
		when(sf.findMetric(any(String.class))).thenReturn(metricObject);

		
	}
	
	@Test
	public void test() {
		ChartParameters params = new ChartParameters(queryString);
		ParamValueRetriever retriever = new ParamValueRetriever(params);
		
		Controller controller = new Controller(params, sf);
		
		//CHARTPARAMETER
		Chart chart = controller.getChartType(retriever);
		assertTrue(chart instanceof ScatterPlot);
		
		chart.setSonarFacade(sf);
		
		//PARENTPARAMETER
		String parent = retriever.retrieveValue(key1);
		assertTrue(parent.equals("sonar:chartplugin"));
		
		//RESOURCETYPEPARAMETER
		String resourceType = retriever.retrieveValue(key2, DEFAULT_RESOURCE_TYPE);
		assertTrue(resourceType.equals(DEFAULT_RESOURCE_TYPE));
		
		//RESOURCESPARAMETER
		ResourcesHandler rh = new ResourcesHandler(sf);
		rh.setNext(null);
		rh.handleRequest(chart, retriever);
		
		assertTrue(chart.getResources().size() == 4);
		
		//RESOURCEVISUALIZATIONCREATOR
		ResourceVisualizationCreatorHandler rvc = new ResourceVisualizationCreatorHandler(sf);
		rvc.setNext(null);
		rvc.handleRequest(chart, retriever);
		
		assertTrue(chart.getRvf() != null); 
		
		//AXISMETRIX
		//Hier loopt iets mis met Mocking maar geen idee waarom
		AxisMetricHandler am = new AxisMetricHandler(sf);
		when(am.createAxisMetricProperty(keyX, retriever)).thenReturn(xMetric);
		when(am.createAxisMetricProperty(keyY, retriever)).thenReturn(yMetric);
		am.setNext(null);
		
		am.handleRequest(chart, retriever);
		
		assertTrue(((ScatterPlot)chart).getxMetric() == xMetric);
		assertTrue(((ScatterPlot)chart).getyMetric() == yMetric);
		
		//SIZE
		SizeHandler sh = new SizeHandler(sf);
		sh.setNext(null);
		
		sh.handleRequest(chart, retriever);
		
		assertTrue(((ScatterPlot) chart).getImageFrameSize() == DEFAULT_SIZE);
		
		//LINEFACTORY
		LineFactoryHandler lf = new LineFactoryHandler();
		lf.setNext(null);
	}
	

	
	
}
