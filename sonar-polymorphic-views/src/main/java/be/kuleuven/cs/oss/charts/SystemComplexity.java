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
	
	private final static Logger LOG = LoggerFactory.getLogger(PolymorphicViewsChart.class);
	
	private TreeNode inheritanceTree;
	private LineFactory lf;
	
	
	public SystemComplexity() {
		super();
		
		this.inheritanceTree = makeTree();
	}

	

	private TreeNode makeTree() {
		LOG.info("Started making a Tree in SysCom");
		List<Resource> parentResources = new ArrayList<Resource>();
		
		for(Resource resource: getResources()) {
			boolean parentResource = true;
			List<Dependency> dependencies = getSonarFacade().findIncomingDependencies(resource);
			
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
		LOG.info("PARENTRESOURCES: " + parentResources.toString());
		TreeNode treeNode = new TreeNode(parentResources, getSonarFacade());
		treeNode.setRootNode();
		
		LOG.info("Ended making a Tree in SysCom");
		return treeNode;
	}


	@Override
	public BufferedImage draw() {
		SystemComplexityDrawing sysComDraw = new SystemComplexityDrawing(getIDrawInstantiation(), lf);
		TreeNodeRV treeNodeRV = new TreeNodeRV(getResourceVisualizationFactory(), inheritanceTree);
		BufferedImage out = sysComDraw.drawTreeRV(treeNodeRV);
		return out;
	}



	public void setLineFactory(LineFactory factory) {
		this.lf = factory;
		
	}
	
	
	
	
	
	
	
}
