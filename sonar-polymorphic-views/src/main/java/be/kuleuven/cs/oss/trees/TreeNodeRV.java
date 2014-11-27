package be.kuleuven.cs.oss.trees;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import be.kuleuven.cs.oss.datautils.Connection;
import be.kuleuven.cs.oss.datautils.Position;
import be.kuleuven.cs.oss.resourceproperties.ResourcePropertiesManager;
import be.kuleuven.cs.oss.resourcevisualizations.ResourceVisualization;
import be.kuleuven.cs.oss.resourcevisualizations.ResourceVisualizationFactory;
import be.kuleuven.cs.oss.sonarfacade.Resource;

public class TreeNodeRV {

	private static final int OFFSET = 5;
	
	private ResourceVisualizationFactory rvf;
	private TreeNode treeNode;
	private ResourcePropertiesManager manager;

	private ResourceVisualization rv = null;
	private List<Connection> connections;
	private TreeMap<String, TreeNodeRV> children;

	private int maxRight;
	private int level = 0;

	public TreeNodeRV(ResourceVisualizationFactory rvf, TreeNode treeNode, ResourcePropertiesManager manager) {
		this.rvf = rvf;
		this.treeNode = treeNode;
		this.manager = manager;
		
		makeRv();
		createChildren();
		createConnections();
		
	}

	private void createChildren() {
		TreeMap<String, TreeNode> children = treeNode.getChildren();
		
		for(Map.Entry<String, TreeNode> entry : children.entrySet()) {
			TreeNode treeNode = entry.getValue();

			TreeNodeRV child = new TreeNodeRV(rvf, treeNode, manager);
			child.setLevel(this.level++);
			//maxRight = maxRight + child.getMaxRight() + OFFSET; // Hier moet geen maxRight ook nog eens bij denk ik
			this.children.put(entry.getKey(), child); //Add the child to the internal children list
		}
	
		
		//The position will have to be determined after all the scaling of the resources has been done.
		
		
//		if(children.size() == 0) {
//			maxRight = maxRight + rv.getWidth(); //BOX moet in ints en max moet er denk weer niet bij
//		}
//		
//		calculateXPositionRv();
		

	}

	private void calculateXPositionRv() {
		int xFirstChild = children.firstEntry().getValue().getRv().getPosition().getX();
		int xLastChild = children.lastEntry().getValue().getRv().getPosition().getX();
		
		int x = (xFirstChild + xLastChild) / 2; 
		
		Position position = new Position(x, 0);
		rv.setPosition(position);
		
	}
	
	private void createConnections() {
		List<Connection> connections = new ArrayList<Connection>();
		
		for(Map.Entry<String, TreeNodeRV> entry : children.entrySet()) {
			TreeNodeRV treeNodeRV = entry.getValue();

			ResourceVisualization rvChild = treeNodeRV.getRv();
			
			Connection connection = new Connection(rv, rvChild);
			connections.add(connection);
		}	
	}


	

	private void makeRv() {
		if(!isRoot()){
			Resource res = treeNode.getResource();
			HashMap<String, Double> resPropertyValues = manager.getPropertyValues(res);
			rv =  rvf.create(resPropertyValues);
		}
	}
	
	public int getMaxRight() {
		return maxRight;
	}

	public ResourceVisualization getRv() {
		return rv;
	}

	public List<ResourceVisualization> getAllRvs() {
		ArrayList<ResourceVisualization> rvs = new ArrayList<ResourceVisualization>();
		//rv is null if this is the root node
		if(rv != null) rvs.add(rv);		
		for(Map.Entry<String, TreeNodeRV> entry : children.entrySet()){
			rvs.addAll(entry.getValue().getAllRvs());
		}
		return rvs;
	}
	
	public List<Connection> getAllConnections(){
		ArrayList<Connection> cons = new ArrayList<Connection>();
		cons.addAll(connections);
		for(Map.Entry<String, TreeNodeRV> entry : children.entrySet()){
			cons.addAll(entry.getValue().getAllConnections());
		}
		return cons;
	}
	
	public List<Connection> getConnections() {
		return connections;
	}
	
	public TreeNodeRV updateXPosition(int maxRight){
		this.maxRight = maxRight + rv.getWidth() + OFFSET;
		
		for(Map.Entry<String, TreeNodeRV> entry : children.entrySet()){			
			TreeNodeRV child = entry.getValue().updateXPosition(maxRight);
			int childMaxRight = child.getMaxRight();
			if (this.maxRight >= childMaxRight){
				this.maxRight = childMaxRight;
			}			
		}
		
		calculateXPositionRv();
		
		return this;
		
	}
	
	public boolean isRoot(){
		return treeNode.isRoot();
	}
	
	public void setLevel(int level){
		this.level = level;
	}
	
	public int getLevel(){
		return this.level;
	}
	
	public Map<Integer, Integer> getMaxHeightPerLevel(Map<Integer, Integer> currentMaxHeights){
		int level = getLevel();
		int height = rv.getHeight();
		
		if(currentMaxHeights.containsKey(level)){
			int currentMaxHeight = currentMaxHeights.get(level);
			if(height>currentMaxHeight){
				currentMaxHeights.put(level, height);
			}
	
		}else{
			currentMaxHeights.put(level, height);
		}
		
		
		for(Map.Entry<String, TreeNodeRV> entry : children.entrySet()){
			currentMaxHeights = entry.getValue().getMaxHeightPerLevel(currentMaxHeights);	
		}
		
		return currentMaxHeights;
	}
	
	public void updateYPositions(Map<Integer, Integer> maxHeights){
		int level = getLevel();
		if(level == 0){ //In case the node is the root, height doens't get set
			for(Map.Entry<String, TreeNodeRV> entry : children.entrySet()){
				ResourceVisualization childRV = entry.getValue().getRv();
				
				int y = 0 + childRV.getHeight()/2;
				childRV.getPosition().setY(y);
				
				entry.getValue().updateYPositions(maxHeights);
			}
		}
		else{
			for(Map.Entry<String, TreeNodeRV> entry : children.entrySet()){
				ResourceVisualization childRV = entry.getValue().getRv();
				calcYPositionChild(level, maxHeights, childRV);
				
				entry.getValue().updateYPositions(maxHeights);
			}
			
		}
		
	}
	
	private Position calcYPositionChild(int level, Map<Integer, Integer> maxHeights, ResourceVisualization childRV) {
		int maxHeight = maxHeights.get(level);
		Position position = childRV.getPosition();
		int y = rv.getPosition().getY() - rv.getHeight()/2 + maxHeight + OFFSET + childRV.getHeight()/2;
		
		position.setY(y);
		return position;
	}

}
