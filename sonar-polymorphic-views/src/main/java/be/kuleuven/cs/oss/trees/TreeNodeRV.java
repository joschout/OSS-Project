package be.kuleuven.cs.oss.trees;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import be.kuleuven.cs.oss.datautils.Connection;
import be.kuleuven.cs.oss.datautils.Position;
import be.kuleuven.cs.oss.polymorphicviews.plugin.PolymorphicViewsChart;
import be.kuleuven.cs.oss.resourcevisualizations.ResourceVisualization;
import be.kuleuven.cs.oss.resourcevisualizations.ResourceVisualizationFactory;
import be.kuleuven.cs.oss.sonarfacade.Resource;

public class TreeNodeRV {

	private static final int OFFSET = 5;
	
	private ResourceVisualizationFactory rvf;
	private TreeNode treeNode;

	private ResourceVisualization rv = null;
	private List<Connection> connections = new ArrayList<Connection>();
	private TreeMap<String, TreeNodeRV> children = new TreeMap<String, TreeNodeRV>();

	private int maxRight;
	private int level = 0;
	
	private final static Logger LOG = LoggerFactory.getLogger(TreeNodeRV.class);

	public TreeNodeRV(ResourceVisualizationFactory rvf, TreeNode treeNode) {
		this.rvf = rvf;
		this.treeNode = treeNode;
		
		makeRv();
		LOG.info("MADE RV OF NODE");
		createChildren();
		//LOG.info("CHILDREN: " + this.children.toString());
		LOG.info("MADE CHILDREN");
		createConnections();
		LOG.info("MADE CONNECTIONS");
		
	}

	private void createChildren() {
		TreeMap<String, TreeNode> nodeChildren = treeNode.getChildren();
		LOG.info("Children: " + nodeChildren.toString());
		
		for(Map.Entry<String, TreeNode> entry : nodeChildren.entrySet()) {
			TreeNode treeNode = entry.getValue();
			LOG.info(entry.getKey());

			TreeNodeRV child = new TreeNodeRV(rvf, treeNode);
			child.setLevel(this.level + 1);
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
		int x = 0;
		
		if(children.firstEntry() != null) {
			int xFirstChild = children.firstEntry().getValue().getRv().getPosition().getX();
			int xLastChild = children.lastEntry().getValue().getRv().getPosition().getX();
			
			x = (xFirstChild + xLastChild) / 2;
		}
		else {
			x = this.maxRight;
		}
		
		System.out.println("X IS: " + x);
		
		Position position = new Position(x, 0);
		rv.setPosition(position);
		
	}
	
	private void createConnections() {
		
		for(Map.Entry<String, TreeNodeRV> entry : children.entrySet()) {
			TreeNodeRV treeNodeRV = entry.getValue();

			ResourceVisualization rvChild = treeNodeRV.getRv();
			
			Connection connection = new Connection(rv, rvChild);
			connections.add(connection);
		}	
		System.out.println("Connections for " + this.toString() + " " + connections.toString());
	}


	

	private void makeRv() {
		if(!isRoot()){
			Resource res = treeNode.getResource();
			rv =  rvf.create(res);
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
		
		if(!isRoot()) {
			cons.addAll(connections);
		}
		
		for(Map.Entry<String, TreeNodeRV> entry : children.entrySet()){
			cons.addAll(entry.getValue().getAllConnections());
		}
		return cons;
	}
	
	public List<Connection> getConnections() {
		return connections;
	}
	
	public TreeNodeRV updateXPosition(int maxRight){

		if (isRoot()) {
			this.maxRight = maxRight;
			
		}
		else {
			System.out.println("MAXRIGHT: " + maxRight + " " + rv.getWidth());
			this.maxRight = maxRight + rv.getWidth() + OFFSET;
		}
		
		for(Map.Entry<String, TreeNodeRV> entry : children.entrySet()){			
			TreeNodeRV child = entry.getValue().updateXPosition(this.maxRight);
			int childMaxRight = child.getMaxRight();
			if (this.maxRight <= childMaxRight){
				this.maxRight = childMaxRight;
			}			
		}
		
			if(!isRoot()){
				calculateXPositionRv();
			}
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
		
		int height;
		if(isRoot()) {
			height = 0;
		}
		else {
			height = rv.getHeight();
		}
		
		if(currentMaxHeights.containsKey(level)){
			int currentMaxHeight = currentMaxHeights.get(level);
			if(height > currentMaxHeight){
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
				System.out.println("Y IS: " + y);
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
		if (isRoot()) {
			return new Position(0, 0);
		}
		
		int maxHeight = maxHeights.get(level);
		Position position = childRV.getPosition();
		int y = rv.getPosition().getY() - rv.getHeight()/2 + maxHeight + OFFSET + childRV.getHeight()/2;
		
		position.setY(y);
		return position;
	}

}
