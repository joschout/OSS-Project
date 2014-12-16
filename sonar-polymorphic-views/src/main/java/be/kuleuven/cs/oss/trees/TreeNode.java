package be.kuleuven.cs.oss.trees;

import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;

import be.kuleuven.cs.oss.resourcevisualizations.ResourceVisualization;
import be.kuleuven.cs.oss.resourcevisualizations.ResourceVisualizationCreator;
import be.kuleuven.cs.oss.sonarfacade.Dependency;
import be.kuleuven.cs.oss.sonarfacade.DependencyType;
import be.kuleuven.cs.oss.sonarfacade.Resource;
import be.kuleuven.cs.oss.sonarfacade.SonarFacade;

public class TreeNode {
	
	//TODO TESTKLASSE MAKEN
	
	private boolean isRoot = false;
	
	private Resource resource;
	private SonarFacade sonarF;
	
	private TreeMap<String, TreeNode> children;
	
	

	public TreeNode(Resource resource,  SonarFacade sonarFacade) {
		this.sonarF = sonarFacade;
		this.resource = resource;
		this.children = new TreeMap<String, TreeNode>();
		
		createChildren();
	}
	
	public TreeNode(List<Resource> parentResources, SonarFacade sonarFacade) {
		this.sonarF = sonarFacade;
		this.children = new TreeMap<String, TreeNode>();
		
		setRootNode();
		createChildres(parentResources);
		
	}
	
	private void createChildres(List<Resource> resources) {
		for(Resource resource: resources) {
			String name = resource.getName();
			TreeNode treeNode = new TreeNode(resource, sonarF);
			
			children.put(name, treeNode);
		}
	}

	private void createChildren() {
		List<Dependency> dependencies = sonarF.findIncomingDependencies(resource);
		List<Resource> foundChildResources = new ArrayList<Resource>();

		List<Dependency> extendsDependencies = new ArrayList<Dependency>();
		
		for(Dependency dependency: dependencies) {				
			if(dependency.getType().equals(DependencyType.EXTENDS)) {
				extendsDependencies.add(dependency);
			}
		}
		
		for(Dependency extendsDependency: extendsDependencies) {
			String fromResourceKey = extendsDependency.getFromResourceKey();
			Resource resource = sonarF.findResource(fromResourceKey);
			foundChildResources.add(resource);
			
		}
		
		for(Resource resource: foundChildResources) {
			String name = resource.getName();
			TreeNode treeNode = new TreeNode(resource, sonarF);
			
			children.put(name, treeNode);
		}
		
	}
	
	public void setRootNode() {
		isRoot = true;
	}
	
	public boolean isRoot(){
		return isRoot;
	}
	
	public Resource getResource() {
		return resource;
	}

	
	public TreeMap<String, TreeNode> getChildren() {
		return new TreeMap<>(children);
	}
	
}
