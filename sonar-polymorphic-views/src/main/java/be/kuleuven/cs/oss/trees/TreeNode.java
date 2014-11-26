package be.kuleuven.cs.oss.trees;

import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;

import be.kuleuven.cs.oss.sonarfacade.Dependency;
import be.kuleuven.cs.oss.sonarfacade.DependencyType;
import be.kuleuven.cs.oss.sonarfacade.Resource;
import be.kuleuven.cs.oss.sonarfacade.SonarFacade;

public class TreeNode {
	
	//TODO TESTKLASSE MAKEN
	
	boolean isRoot = false;
	
	Resource resource;
	SonarFacade sonarF;
	
	TreeMap<String, TreeNode> children;
	
	

	public TreeNode(Resource resource, SonarFacade sonarFacade) {
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
		List<Dependency> dependencies = sonarF.findOutgoingDependencies(resource);
		List<Resource> foundChildResources = new ArrayList<Resource>();
		
		for(Dependency dependency: dependencies) {
			
			if(dependency.getType() == DependencyType.EXTENDS) {
				String toResourceKey = dependency.getToResourceKey();
				Resource resource = sonarF.findResource(toResourceKey);
				foundChildResources.add(resource);
			}
		}
		
		for(Resource resource: foundChildResources) {
			String name = resource.getName();
			TreeNode treeNode = new TreeNode(resource, sonarF);
			
			children.put(name, treeNode);
		}
		
	}
	
	private void setRootNode() {
		isRoot = true;
	}
	
	public Resource getResource() {
		return resource;
	}
	
	
	public TreeMap<String, TreeNode> getChildren() {
		return new TreeMap<>(children);
	}
	
}
