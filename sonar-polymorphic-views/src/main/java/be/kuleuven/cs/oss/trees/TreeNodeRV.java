package be.kuleuven.cs.oss.trees;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import be.kuleuven.cs.oss.datautils.Connection;
import be.kuleuven.cs.oss.datautils.Position;
import be.kuleuven.cs.oss.resourcevisualizations.ResourceVisualization;
import be.kuleuven.cs.oss.resourcevisualizations.ResourceVisualizationFactory;

public class TreeNodeRV {

	private static final int OFFSET = 5;
	
	private ResourceVisualizationFactory rvf;
	private TreeNode treeNode;

	private ResourceVisualization rv;
	private List<Connection> connections;
	private TreeMap<String, TreeNodeRV> children;

	private int maxRight;

	public TreeNodeRV(ResourceVisualizationFactory rvf, TreeNode treeNode, int maxRight) {
		this.rvf = rvf;
		this.treeNode = treeNode;
		this.maxRight = maxRight;
		
		this.rv = makeRv();
		createChildren();
		this.connections = createConnections();
		
	}

	
	

	private void createChildren() {
		TreeMap<String, TreeNode> children = treeNode.getChildren();

		for(Map.Entry<String, TreeNode> entry : children.entrySet()) {
			TreeNode treeNode = entry.getValue();

			TreeNodeRV child = new TreeNodeRV(rvf, treeNode, maxRight);
			maxRight = maxRight + child.getMaxRight() + OFFSET; // Hier moet geen maxRight ook nog eens bij denk ik

		}
		
		if(children.size() == 0) {
			maxRight = maxRight + rv.getWidth(); //BOX moet in ints en max moet er denk weer niet bij
		}
		
		calculateXPositionRv();
		

	}

	private void calculateXPositionRv() {
		int xFirstChild = children.firstEntry().getValue().getRv().getPosition().getX();
		int xLastChild = children.lastEntry().getValue().getRv().getPosition().getX();
		
		int x = (xFirstChild + xLastChild) / 2; 
		
		Position position = new Position(x, 0);
		rv.setPosition(position);
		
	}
	
	private List<Connection> createConnections() {
		List<Connection> connections = new ArrayList<Connection>();
		
		for(Map.Entry<String, TreeNodeRV> entry : children.entrySet()) {
			TreeNodeRV treeNodeRV = entry.getValue();

			ResourceVisualization rvChild = treeNodeRV.getRv();
			
			Connection connection = new Connection(rv, rvChild);
			connections.add(connection);
		}
		
		return connections;
	}


	

	private ResourceVisualization makeRv() {
		// TODO Auto-generated method stub
		return null;
	}
	
	private int getMaxRight() {
		return maxRight;
	}

	private ResourceVisualization getRv() {
		return rv;
	}

	public List<ResourceVisualization> getAllRvs() {
		// TODO Auto-generated method stub
		return null;
	}

}
