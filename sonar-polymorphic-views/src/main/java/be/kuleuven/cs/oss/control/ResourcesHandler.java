package be.kuleuven.cs.oss.control;

import java.util.List;

import org.sonar.api.charts.ChartParameters;

import be.kuleuven.cs.oss.charts.Chart;
import be.kuleuven.cs.oss.sonarfacade.Resource;
import be.kuleuven.cs.oss.sonarfacade.SonarFacade;

/**
 * A handler for all the resources in the chart
 * 
 * @author jeroenreinenbergh
 *
 */
public class ResourcesHandler implements IHandler<Chart> {
	
	private IHandler<Chart> next;
	
	private String resourceType1 = "classes";
	private String resourceType2 = "packages";

	SonarFacade sf;

	/**
	 * Creates a new resources handler based on the given SonarFacade instance
	 * @param sf the given SonarFacade instance
	 */
	public ResourcesHandler(SonarFacade sf) {
		this.sf = sf;
	}

	@Override
	public void setNext(IHandler<Chart> handler) {
		this.next = handler;
	}

	/**
	 * Finds all the requested resources, depending on the resource type key (currently only packages and classes are supported) and sets them in the given chart
	 * @throws IllegalArgumentException if the resource type value is not valid
	 */
	@Override
	public void handleRequest(Chart chart, ChartParameters params) throws IllegalArgumentException {

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
			throw new IllegalArgumentException("Invalid resource type");
		}
		
		chart.setResources(resources);
		
		if(next != null) {
			next.handleRequest(chart, params);
		}

	}

}
