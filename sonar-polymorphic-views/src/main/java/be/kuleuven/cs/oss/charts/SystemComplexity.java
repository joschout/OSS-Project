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
import be.kuleuven.cs.oss.trees.TreeNode;

public class SystemComplexity extends Chart {
	
	private TreeNode inheritanceTree;
	
	
	public SystemComplexity(List<Resource> resources, ResourceVisualizationFactory RVF, SonarFacade sonarF, ResourcePropertiesManager propManager) {
		super(resources, RVF, sonarF, propManager);
		
		this.inheritanceTree = makeTree();
	}

	

	private TreeNode makeTree() {
		List<Resource> parentResources = new ArrayList<Resource>();
		
		for(Resource resource: resources) {
			boolean parentResource = true;
			List<Dependency> dependencies = sonarF.findIncomingDependencies(resource);
			
			for(Dependency dependency: dependencies) {
				
				if(dependency.getType() == DependencyType.EXTENDS) {
					parentResource = false;
					break;
				}
			}
			
			if(parentResource) {
				parentResources.add(resource);
			}
		}
		
		TreeNode treeNode = new TreeNode(parentResources, sonarF);
		return treeNode;
	}


	@Override
	public BufferedImage draw(IDraw drawInterface) {
		SystemComplexityDrawing draw = new SystemComplexityDrawing(drawInterface);

		return null;
	}
	
	
	
	
	
	
	
}
