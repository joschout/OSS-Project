package be.kuleuven.cs.oss.charts;

import java.util.ArrayList;
import java.util.List;

import be.kuleuven.cs.oss.datautils.Connection;
import be.kuleuven.cs.oss.sonarfacade.Dependency;
import be.kuleuven.cs.oss.sonarfacade.Resource;
import be.kuleuven.cs.oss.trees.TreeNode;

public class SystemComplexityDrawing {
	
	private List<Connection> connections;
	
	public SystemComplexityDrawing(TreeNode inheritanceTree) {
		
		
		
		
		
		
		
		
		connections = new ArrayList<Connection>();
	}
	
	
	
	
	private void createConnections() {
		for(Resource resource: resources) {
			List<Dependency> dependencies = sonarF.findOutgoingDependencies(resource);
			for(Dependency dependency: dependencies) {
				Connection connection = new Connection()
			}
		}
	}

}
