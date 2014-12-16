package be.kuleuven.cs.oss.charts;

import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import be.kuleuven.cs.oss.lines.LineFactory;
import be.kuleuven.cs.oss.polymorphicviews.plugin.PolymorphicViewsChart;
import be.kuleuven.cs.oss.sonarfacade.Dependency;
import be.kuleuven.cs.oss.sonarfacade.DependencyType;
import be.kuleuven.cs.oss.sonarfacade.Resource;
import be.kuleuven.cs.oss.trees.TreeNode;
import be.kuleuven.cs.oss.trees.TreeNodeRV;

public class SystemComplexity extends Chart {
	
	private final static Logger LOG = LoggerFactory.getLogger(SystemComplexity.class);
	
	private TreeNode inheritanceTree;
	private LineFactory lf;
	
	
	public SystemComplexity() {
		super();
	}
	

	

	public void makeTree() {
		LOG.info("Started making a Tree in SysCom");
		List<Resource> parentResources = new ArrayList<Resource>();
		
		List<Resource> resources = getResources();
		
		for(Resource resource: resources) {
			List<Dependency> extendsDependencies = new ArrayList<Dependency>();
			List<Dependency> dependencies = getSonarFacade().findOutgoingDependencies(resource);
			for(Dependency dependency: dependencies) {				
				if(dependency.getType().equals(DependencyType.EXTENDS)) {
					extendsDependencies.add(dependency);
				}
			}
			
			if(extendsDependencies.size()==0){
				parentResources.add(resource);
			}
		}
			
		LOG.info("PARENTRESOURCES: " + parentResources.toString());
		TreeNode treeNode = new TreeNode(parentResources, getSonarFacade());
		treeNode.setRootNode();
		
		LOG.info("Ended making a Tree in SysCom");
		
		this.inheritanceTree =  treeNode;
	}


	@Override
	public BufferedImage draw() {
		makeTree();
		SystemComplexityDrawing sysComDraw = new SystemComplexityDrawing(getIDrawInstantiation(), lf);
		TreeNodeRV treeNodeRV = new TreeNodeRV(getRvf(), inheritanceTree);
		BufferedImage out = sysComDraw.drawTreeRV(treeNodeRV);
		return out;
	}



	public void setLineFactory(LineFactory factory) {
		this.lf = factory;
		
	}
	
	
	
	
	
	
	
}
