package be.kuleuven.cs.oss.charts;

import java.util.ArrayList;
import java.util.List;

import be.kuleuven.cs.oss.datautils.Connection;
import be.kuleuven.cs.oss.sonarfacade.Dependency;
import be.kuleuven.cs.oss.sonarfacade.Resource;

public class SystemComplexityDrawing {
	
	private List<Connection> connections;
	
	public SystemComplexityDrawing() {
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
