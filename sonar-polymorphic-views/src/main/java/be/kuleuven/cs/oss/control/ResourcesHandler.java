package be.kuleuven.cs.oss.control;

import java.util.List;

import org.sonar.api.charts.ChartParameters;

import be.kuleuven.cs.oss.charts.Chart;
import be.kuleuven.cs.oss.sonarfacade.Resource;
import be.kuleuven.cs.oss.sonarfacade.SonarFacade;

public class ResourcesHandler implements IHandler<Chart> {
	
	private IHandler<Chart> next;
	
	private String resourceType1 = "classes";
	private String resourceType2 = "packages";

	SonarFacade sf;

	public ResourcesHandler(SonarFacade sf) {
		this.sf = sf;
	}

	@Override
	public void setNext(IHandler<Chart> handler) {
		this.next = handler;
	}

	@Override
	public void handleRequest(Chart chart, ChartParameters params) {

		ParentParameter parentParam = new ParentParameter();
		Resource parent = parentParam.getParentResource(sf, params);

		ResourceTypeParameter resourceTypeParam = new ResourceTypeParameter();
		String resourceType = resourceTypeParam.getResourceType(sf, params);

		List<Resource> resources = null;


		if(resourceType.equals(resourceType1)) {
			resources = sf.findClasses(parent);
		}
		if(resourceType.equals(resourceType2)) {
			resources = sf.findPackages(parent);
		}
		if(resources == null) {
			throw new IllegalArgumentException();
		}
		
		chart.setResources(resources);
		
		if(next != null) {
			next.handleRequest(chart, params);
		}

	}

}
