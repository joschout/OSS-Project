package be.kuleuven.cs.oss.control;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.mockito.Matchers.any;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.sonar.api.charts.ChartParameters;

import be.kuleuven.cs.oss.charts.Chart;
import be.kuleuven.cs.oss.charts.ScatterPlot;
import be.kuleuven.cs.oss.sonarfacade.Resource;
import be.kuleuven.cs.oss.sonarfacade.SonarFacade;

public class ScenarioControllerTest {

	private static String queryString = "ck=polymorphic&type=scatter&parent=sonar:chartplugin&resources=classes&shape=metric&shapecircle&xmetric=lines&ymetric=comment_lines&circlecolor=min20.3max1000.28keylines";
	
	private static final String DEFAULT_RESOURCE_TYPE = "classes";
	
	private String key1 = "parent";
	private String key2 = "resources";
	
	SonarFacade sf = mock(SonarFacade.class);
	
	Resource parentResource = mock(Resource.class);
	
	

	
	
	@Before
	public void setUp() {
		Resource res = mock(Resource.class);
		List<Resource> resources = new ArrayList<Resource>();
		for(int i=1; i<5; i++){
			resources.add(res);
		}
		
		when(sf.findResource(any(String.class))).thenReturn(parentResource);
		when(sf.findClasses(parentResource)).thenReturn(resources);

		
	}
	
	@Test
	public void test() {
		ChartParameters params = new ChartParameters(queryString);
		
		Controller controller = new Controller(params, sf);
		
		//CHARTPARAMETER
		Chart chart = controller.getChartType(params);
		assertTrue(chart instanceof ScatterPlot);
		
		chart.setSonarFacade(sf);
		
		//PARENTPARAMETER
		String parent = params.getValue(key1);
		assertTrue(parent.equals("sonar:chartplugin"));
		
		//RESOURCETYPEPARAMETER
		String resourceType = params.getValue(key2, DEFAULT_RESOURCE_TYPE, false);
		assertTrue(resourceType.equals(DEFAULT_RESOURCE_TYPE));
		
		//RESOURCESPARAMETER
		List<Resource> resources = sf.findClasses(parentResource);
		assertTrue(resources.size() == 4);
		
		chart.setResources(resources);
		
		
		
		
		
	}
	

	
	
}
