package be.kuleuven.cs.oss.charts;

import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

import be.kuleuven.cs.oss.drawingPackage.IDraw;
import be.kuleuven.cs.oss.resourceproperties.ResourcePropertiesManager;
import be.kuleuven.cs.oss.resourcevisualizations.ResourceVisualizationFactory;
import be.kuleuven.cs.oss.sonarfacade.Dependency;
import be.kuleuven.cs.oss.sonarfacade.DependencyType;
import be.kuleuven.cs.oss.sonarfacade.Resource;
import be.kuleuven.cs.oss.sonarfacade.SonarFacade;

public class SystemComplexity extends Chart {
	
	private List<Dependency> dependencies;
	
	public SystemComplexity(List<Resource> resources, ResourceVisualizationFactory RVF, SonarFacade sonarF, ResourcePropertiesManager propManager) {
		super(resources, RVF, sonarF, propManager);
		this.dependencies = findDependencies();
	}

	@Override
	public BufferedImage draw(IDraw drawInterface) {
		SystemComplexityDrawing draw = new SystemComplexityDrawing(drawInterface);
		return null;
	}
	
	public List<Dependency> getDependencies() {
		return dependencies;
	}
	
	private List<Dependency> findDependencies() {
		List<Dependency> dependencies = new ArrayList<Dependency>();
		
		for(Resource resource: resources) {
			List<Dependency> dependenciesTemp = sonarF.findOutgoingDependencies(resource);
			
			for(Dependency dependency: dependenciesTemp) {
				
				if(dependency.getType() == DependencyType.EXTENDS) {
					dependencies.add(dependency);
				}
			}
		}
		return dependencies;
	}
	
	
	
}
