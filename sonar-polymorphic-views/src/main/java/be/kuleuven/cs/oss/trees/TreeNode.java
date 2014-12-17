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
	
	/**
	 * Class that represent the abstract structure of the inheritance tree. 
	 */
	
	private boolean isRoot = false;
	
	private Resource resource;
	private SonarFacade sonarF;
	
	private TreeMap<String, TreeNode> children;
	
	
	
	/**
	 * This constructor builds a tree down from 1 class. Not very useful when trying to build a tree of
	 * an entire project.
	 * 
	 * It sets it's attributes and the uses the CreateChildren() method to build it's children.
	 * 
	 * @param resource the resource that represents this node.
	 * @param sonarFacade sonarfacade is used to find the dependencies between classes
	 */
	public TreeNode(Resource resource,  SonarFacade sonarFacade) {
		this.sonarF = sonarFacade;
		this.resource = resource;
		this.children = new TreeMap<String, TreeNode>();
		
		createChildren();
	}
	
	/**
	 * This constructor is used to build a tree based on multiple resources that are at the top of the hierarchy.
	 * it creates the rootnode used to pass information between the parent resources. Then it calls the 
	 * createChildres(parentResources) method to build all children for each parent.
	 * @param parentResources list of parent resources
	 * @param sonarFacade sonarfacade used to find dependencies.
	 */
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
	
	private void setRootNode() {
		isRoot = true;
	}
	
	/**
	 * Check to see if this node is the Root node. 
	 * @return boolean True if this node is the root.
	 */
	public boolean isRoot(){
		return isRoot;
	}
	
	/**
	 * Getter for the resource of this node.
	 * @return Resource this node's resource.
	 */
	public Resource getResource() {
		return resource;
	}

	/**
	 * getter for this nodes children. This is returned as a TreeMap of strings and treenode, with the string
	 * being the name of the child treenode.
	 * @return TreeMap<String, TreeNode> TreeMap representing the children.
	 */
	public TreeMap<String, TreeNode> getChildren() {
		return new TreeMap<>(children);
	}
	
}
